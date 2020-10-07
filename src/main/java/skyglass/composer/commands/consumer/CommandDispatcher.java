package skyglass.composer.commands.consumer;

import static java.util.Collections.EMPTY_MAP;
import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import skyglass.composer.commands.common.CommandMessageHeaders;
import skyglass.composer.commands.common.Failure;
import skyglass.composer.commands.common.ReplyMessageHeaders;
import skyglass.composer.commands.common.paths.ResourcePath;
import skyglass.composer.commands.common.paths.ResourcePathPattern;
import skyglass.composer.common.json.mapper.JSonMapper;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.messaging.consumer.MessageConsumer;
import skyglass.composer.messaging.producer.MessageBuilder;
import skyglass.composer.messaging.producer.MessageProducer;

public class CommandDispatcher {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private String commandDispatcherId;

	private CommandHandlers commandHandlers;

	private MessageConsumer messageConsumer;

	private MessageProducer messageProducer;

	public CommandDispatcher(String commandDispatcherId,
			CommandHandlers commandHandlers,
			MessageConsumer messageConsumer,
			MessageProducer messageProducer) {
		this.commandDispatcherId = commandDispatcherId;
		this.commandHandlers = commandHandlers;
		this.messageConsumer = messageConsumer;
		this.messageProducer = messageProducer;
	}

	@PostConstruct
	public void initialize() {
		messageConsumer.subscribe(commandDispatcherId,
				commandHandlers.getChannels(),
				this::messageHandler);
	}

	public void messageHandler(Message message) {
		logger.trace("Received message {} {}", commandDispatcherId, message);

		Optional<CommandHandler> possibleMethod = commandHandlers.findTargetMethod(message);
		if (!possibleMethod.isPresent()) {
			throw new RuntimeException("No method for " + message);
		}

		CommandHandler m = possibleMethod.get();

		Object param = convertPayload(m, message.getPayload());

		Map<String, String> correlationHeaders = correlationHeaders(message.getHeaders());

		Map<String, String> pathVars = getPathVars(message, m);

		Optional<String> defaultReplyChannel = message.getHeader(CommandMessageHeaders.REPLY_TO);

		List<Message> replies;
		try {
			CommandMessage cm = new CommandMessage(message.getId(), param, correlationHeaders, message);
			replies = invoke(m, cm, pathVars);
			logger.trace("Generated replies {} {} {}", commandDispatcherId, message, replies);
		} catch (Exception e) {
			logger.error("Generated error {} {} {}", commandDispatcherId, message, e.getClass().getName());
			logger.error("Generated error", e);
			handleException(message, param, m, e, pathVars, defaultReplyChannel);
			return;
		}

		if (replies != null) {
			sendReplies(correlationHeaders, replies, defaultReplyChannel);
		} else {
			logger.trace("Null replies - not publishling");
		}
	}

	protected List<Message> invoke(CommandHandler commandHandler, CommandMessage cm, Map<String, String> pathVars) {
		return commandHandler.invokeMethod(cm, pathVars);
	}

	protected Object convertPayload(CommandHandler m, String payload) {
		Class<?> paramType = findCommandParameterType(m);
		return JSonMapper.fromJson(payload, paramType);
	}

	private Map<String, String> getPathVars(Message message, CommandHandler handler) {
		return handler.getResource().flatMap(res -> {
			ResourcePathPattern r = ResourcePathPattern.parse(res);
			return message.getHeader(CommandMessageHeaders.RESOURCE).map(h -> {
				ResourcePath mr = ResourcePath.parse(h);
				return r.getPathVariableValues(mr);
			});
		}).orElse(EMPTY_MAP);
	}

	private void sendReplies(Map<String, String> correlationHeaders, List<Message> replies, Optional<String> defaultReplyChannel) {
		for (Message reply : replies)
			messageProducer.send(destination(defaultReplyChannel),
					MessageBuilder
							.withMessage(reply)
							.withExtraHeaders("", correlationHeaders)
							.build());
	}

	private String destination(Optional<String> defaultReplyChannel) {
		return defaultReplyChannel.orElseGet(() -> {
			throw new RuntimeException();
		});
	}

	private Map<String, String> correlationHeaders(Map<String, String> headers) {
		Map<String, String> m = headers.entrySet()
				.stream()
				.filter(e -> e.getKey().startsWith(CommandMessageHeaders.COMMAND_HEADER_PREFIX))
				.collect(Collectors.toMap(e -> CommandMessageHeaders.inReply(e.getKey()),
						Map.Entry::getValue));
		m.put(ReplyMessageHeaders.IN_REPLY_TO, headers.get(Message.ID));
		return m;
	}

	private void handleException(Message message, Object param,
			CommandHandler commandHandler,
			Throwable cause,
			Map<String, String> pathVars,
			Optional<String> defaultReplyChannel) {
		Optional<CommandExceptionHandler> m = commandHandlers.findExceptionHandler(commandHandler, cause);

		logger.info("Handler for {} is {}", cause.getClass(), m);

		if (m.isPresent()) {
			List<Message> replies = m.get().invoke(cause);
			sendReplies(correlationHeaders(message.getHeaders()), replies, defaultReplyChannel);
		} else {
			List<Message> replies = singletonList(MessageBuilder.withPayload(JSonMapper.toJson(new Failure())).build());
			sendReplies(correlationHeaders(message.getHeaders()), replies, defaultReplyChannel);
		}
	}

	private Class findCommandParameterType(CommandHandler m) {
		return m.getCommandClass();
	}

}

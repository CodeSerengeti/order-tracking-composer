package skyglass.composer.events.subscriber;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import skyglass.composer.common.json.mapper.JSonMapper;
import skyglass.composer.events.common.DomainEvent;
import skyglass.composer.events.common.DomainEventNameMapping;
import skyglass.composer.events.common.EventMessageHeaders;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.messaging.consumer.MessageConsumer;

public class DomainEventDispatcher {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final String eventDispatcherId;

	private DomainEventHandlers domainEventHandlers;

	private MessageConsumer messageConsumer;

	private DomainEventNameMapping domainEventNameMapping;

	public DomainEventDispatcher(String eventDispatcherId, DomainEventHandlers domainEventHandlers, MessageConsumer messageConsumer, DomainEventNameMapping domainEventNameMapping) {
		this.eventDispatcherId = eventDispatcherId;
		this.domainEventHandlers = domainEventHandlers;
		this.messageConsumer = messageConsumer;
		this.domainEventNameMapping = domainEventNameMapping;
	}

	@PostConstruct
	public void initialize() {
		logger.info("Initializing domain event dispatcher");
		messageConsumer.subscribe(eventDispatcherId, domainEventHandlers.getAggregateTypesAndEvents(), this::messageHandler);
		logger.info("Initialized domain event dispatcher");
	}

	public void messageHandler(Message message) {
		String aggregateType = message.getRequiredHeader(EventMessageHeaders.AGGREGATE_TYPE);

		message.setHeader(EventMessageHeaders.EVENT_TYPE,
				domainEventNameMapping.externalEventTypeToEventClassName(aggregateType, message.getRequiredHeader(EventMessageHeaders.EVENT_TYPE)));

		Optional<DomainEventHandler> handler = domainEventHandlers.findTargetMethod(message);

		if (!handler.isPresent()) {
			return;
		}

		DomainEvent param = JSonMapper.fromJson(message.getPayload(), handler.get().getEventClass());

		handler.get().invoke(new DomainEventEnvelopeImpl<>(message,
				aggregateType,
				message.getRequiredHeader(EventMessageHeaders.AGGREGATE_ID),
				message.getRequiredHeader(Message.ID),
				param));

	}

}

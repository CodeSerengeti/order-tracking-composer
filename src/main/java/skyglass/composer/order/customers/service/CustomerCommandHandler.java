package skyglass.composer.order.customers.service;

import skyglass.composer.commands.consumer.CommandHandlerReplyBuilder;
import skyglass.composer.commands.consumer.CommandHandlers;
import skyglass.composer.commands.consumer.CommandMessage;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.order.customers.api.commands.ReserveCreditCommand;
import skyglass.composer.order.customers.api.replies.CustomerCreditLimitExceeded;
import skyglass.composer.order.customers.api.replies.CustomerCreditReserved;
import skyglass.composer.order.customers.api.replies.CustomerNotFound;
import skyglass.composer.order.customers.domain.CustomerCreditLimitExceededException;
import skyglass.composer.order.customers.domain.CustomerNotFoundException;
import skyglass.composer.sagas.participant.SagaCommandHandlersBuilder;

public class CustomerCommandHandler {

	private CustomerService customerService;

	public CustomerCommandHandler(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CommandHandlers commandHandlerDefinitions() {
		return SagaCommandHandlersBuilder
				.fromChannel("customerService")
				.onMessage(ReserveCreditCommand.class, this::reserveCredit)
				.build();
	}

	public Message reserveCredit(CommandMessage<ReserveCreditCommand> cm) {
		ReserveCreditCommand cmd = cm.getCommand();
		try {
			customerService.reserveCredit(cmd.getCustomerId(), cmd.getOrderId(), cmd.getOrderTotal());
			return CommandHandlerReplyBuilder.withSuccess(new CustomerCreditReserved());
		} catch (CustomerNotFoundException e) {
			return CommandHandlerReplyBuilder.withFailure(new CustomerNotFound());
		} catch (CustomerCreditLimitExceededException e) {
			return CommandHandlerReplyBuilder.withFailure(new CustomerCreditLimitExceeded());
		}
	}

}

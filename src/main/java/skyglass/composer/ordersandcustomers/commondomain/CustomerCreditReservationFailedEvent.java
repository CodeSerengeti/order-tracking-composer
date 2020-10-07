package skyglass.composer.ordersandcustomers.commondomain;

public class CustomerCreditReservationFailedEvent extends AbstractCustomerOrderEvent {

  public CustomerCreditReservationFailedEvent() {
  }

  public CustomerCreditReservationFailedEvent(Long orderId) {
    super(orderId);
  }


}

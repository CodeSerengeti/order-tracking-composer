package skyglass.composer.commondomain;

public class CustomerValidationFailedEvent extends AbstractCustomerOrderEvent {

  public CustomerValidationFailedEvent(Long orderId) {
    super(orderId);
  }

  public CustomerValidationFailedEvent() {
  }
}

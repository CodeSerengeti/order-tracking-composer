package skyglass.composer.sagas.simpledsl;

public interface SimpleSagaDsl<Data> {

  default StepBuilder<Data> step() {
    SimpleSagaDefinitionBuilder<Data> builder = new SimpleSagaDefinitionBuilder<>();
    return new StepBuilder<>(builder);
  }

}

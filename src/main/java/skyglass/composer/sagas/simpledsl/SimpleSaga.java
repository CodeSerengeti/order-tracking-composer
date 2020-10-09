package skyglass.composer.sagas.simpledsl;

import skyglass.composer.sagas.orchestration.Saga;

public interface SimpleSaga<Data> extends Saga<Data>, SimpleSagaDsl<Data> {
}

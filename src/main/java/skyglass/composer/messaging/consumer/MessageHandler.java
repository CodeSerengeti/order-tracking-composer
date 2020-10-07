package skyglass.composer.messaging.consumer;

import java.util.function.Consumer;

import skyglass.composer.messaging.common.Message;

public interface MessageHandler extends Consumer<Message> {
}

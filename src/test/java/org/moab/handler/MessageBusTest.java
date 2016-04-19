package org.moab.handler;

import org.junit.Before;
import org.junit.Test;
import org.moab.bus.MessageBus;
import org.moab.command.MOABCommand;

import static org.assertj.core.api.Assertions.*;

public class MessageBusTest {
    private MessageBus bus;

    @Before
    public void setUp() {
        bus = new MessageBus();
    }

    class FakeCommand implements MOABCommand {

    }

    class FakeEventHandler implements MOABHandler {
        public boolean called = false;

        public void handle(Object fakeCommand) {
            called = true;
        }
    }

    @Test
    public void eventBusCallsHandlerOnEvent() {
        FakeEventHandler fakeEventHandler = new FakeEventHandler();
        bus.register("fakeCommand", fakeEventHandler);
        bus.send("fakeCommand", new FakeCommand());

        assertThat(fakeEventHandler.called).isTrue();
    }

}

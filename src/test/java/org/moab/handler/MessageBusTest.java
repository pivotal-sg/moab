package org.moab.handler;

import org.junit.Before;
import org.junit.Test;
import org.moab.bus.MessageBus;
import org.moab.command.MOABCommand;
import org.moab.exception.UnsupportedCommandException;

import static org.assertj.core.api.Assertions.*;

public class MessageBusTest {
    private MessageBus bus;

    @Before
    public void setUp() {
        bus = new MessageBus();
    }

    class FakeCommand implements MOABCommand {
    }

    class WrongCommand implements MOABCommand {
    }

    class FakeEventHandler implements MOABHandler {
        public boolean called = false;

        public void handle(Object fakeCommand) throws UnsupportedCommandException {
            if (!(fakeCommand instanceof FakeCommand)) { throw new UnsupportedCommandException();}
            called = true;
        }
    }

    class WrongEventHandler implements MOABHandler {
        public boolean called = false;

        public void handle(Object wrongCommand) throws UnsupportedCommandException {
            if (!(wrongCommand instanceof WrongCommand)) { throw new UnsupportedCommandException();}
            called = true;
        }
    }

    @Test
    public void eventBusCallsHandlerOnEvent() throws UnsupportedCommandException {
        FakeEventHandler fakeEventHandler = new FakeEventHandler();
        bus.register("fakeCommand", fakeEventHandler);
        bus.send("fakeCommand", new FakeCommand());

        assertThat(fakeEventHandler.called).isTrue();
    }

    @Test
    public void eventBusThrowsUnsupportedCommandExceptionOnWrongType() {
        FakeEventHandler fakeEventHandler = new FakeEventHandler();
        bus.register("fakeCommand", fakeEventHandler);
        Throwable thrown = catchThrowable(() -> { bus.send("fakeCommand", new WrongCommand());} );

        assertThat(thrown).isInstanceOf(UnsupportedCommandException.class);
        assertThat(fakeEventHandler.called).isFalse();
    }

    @Test
    public void eventBusDispatchesCorrectHandler() throws UnsupportedCommandException {
        FakeEventHandler fakeEventHandler = new FakeEventHandler();
        WrongEventHandler wrongEventHandler = new WrongEventHandler();

        bus.register("fakeCommand", fakeEventHandler);
        bus.register("wrongEvent", wrongEventHandler);
        bus.send("fakeCommand", new FakeCommand());

        assertThat(fakeEventHandler.called).isTrue();
        assertThat(wrongEventHandler.called).isFalse();
    }

}

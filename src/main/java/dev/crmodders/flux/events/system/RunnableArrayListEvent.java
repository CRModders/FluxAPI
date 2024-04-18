package dev.crmodders.flux.events.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RunnableArrayListEvent extends Event<Runnable> {

    private final Object lock = new Object();
    private final List<Runnable> runners;

    public RunnableArrayListEvent() {
        runners = new ArrayList<>();
        invoker = () -> runners.forEach(Runnable::run);
    }

    @Override
    public void register(Runnable listener) {
        Objects.requireNonNull(listener, "Tried to register a null listener!");
        synchronized (lock) {
            runners.add(listener);
        }
    }

    public List<Runnable> getRunnables() {
        return Collections.unmodifiableList(runners);
    }
}

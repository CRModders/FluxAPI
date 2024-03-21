package io.github.crmodders.flux.api.events;

public interface Event<T> {
    void onEventTriggered(Event<T> event);

}

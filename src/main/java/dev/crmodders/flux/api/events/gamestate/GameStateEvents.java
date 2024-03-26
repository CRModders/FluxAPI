package dev.crmodders.flux.api.events.gamestate;

import dev.crmodders.flux.api.events.Event;
import dev.crmodders.flux.api.events.EventFactory;
import finalforeach.cosmicreach.gamestates.GameState;

public class GameStateEvents {
    public static final Event<ChangeState> AFTER_CHANGE_STATE = EventFactory.createArrayBacked(ChangeState.class, callbacks -> gameState -> {
        for (var callback : callbacks) {
            //null check so game does not crash
            if (callback != null)
                callback.changeState(gameState);
        }
    });

    public static final Event<ChangeState> BEFORE_CHANGE_STATE = EventFactory.createArrayBacked(ChangeState.class, callbacks -> gameState -> {
        for (var callback : callbacks) {
            //null check so game does not crash
            if (callback != null)
                callback.changeState(gameState);
        }
    });


    @FunctionalInterface
    public interface ChangeState {
        void changeState(GameState gameState);
    }
}
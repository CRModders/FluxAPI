package dev.crmodders.flux.api.gui.interfaces;

import finalforeach.cosmicreach.gamestates.GameState;

import java.util.List;

public interface GameStateCache {

    public static GameStateCache getCache() {
        return (GameStateCache) GameState.currentGameState;
    }

    List<GameState> getAllLoadedGameStates();

    void invalidateCachedAssets();

}

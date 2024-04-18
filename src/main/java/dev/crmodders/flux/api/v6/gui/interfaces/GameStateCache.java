package dev.crmodders.flux.api.v6.gui.interfaces;

import finalforeach.cosmicreach.gamestates.GameState;

import java.util.List;

import finalforeach.cosmicreach.blocks.Block;

/**
 * The basic cache for gameStates that holds the current block state and some other methods you can use in your impls.
 */
public interface GameStateCache {

    static GameStateCache getCache() {
        return (GameStateCache) GameState.currentGameState;
    }

    List<GameState> getAllLoadedGameStates();

    void invalidateCachedAssets();

}

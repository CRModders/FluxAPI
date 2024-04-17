package dev.crmodders.flux.api.v5.gui;

import finalforeach.cosmicreach.gamestates.GameState;

import java.util.function.Supplier;

public class SwitchGameStateButtonElement extends ButtonElement {
    public SwitchGameStateButtonElement(Supplier<GameState> previous) {
        super(() -> GameState.switchToGameState(previous.get()));
    }
}

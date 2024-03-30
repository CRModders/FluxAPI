package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;

import java.util.function.Supplier;

public class SwitchGameStateButtonElement extends ButtonElement {

    public SwitchGameStateButtonElement(Supplier<GameState> previous) {
        super(b -> GameState.switchToGameState(previous.get()), FluxConstants.TextBack);
    }

    public SwitchGameStateButtonElement(Supplier<GameState> previous, TranslationKey textKey) {
        super(b -> GameState.switchToGameState(previous.get()), textKey);
    }

    public SwitchGameStateButtonElement(float x, float y, float w, float h, Supplier<GameState> previous) {
        super(x, y, w, h, b -> GameState.switchToGameState(previous.get()), FluxConstants.TextBack);
    }

    public SwitchGameStateButtonElement(float x, float y, float w, float h, Supplier<GameState> previous, TranslationKey textKey) {
        super(x, y, w, h, b -> GameState.switchToGameState(previous.get()), textKey);
    }
}

package dev.crmodders.flux.api.input.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import dev.crmodders.flux.impl.input.client.FluxInput;
import org.jetbrains.annotations.ApiStatus;

import java.util.LinkedHashSet;

@ApiStatus.Experimental
public final class InputProcessorHelper {
    /**
     * Adds an input processor.
     * <p>
     * All added input processors are ran in insertion order through an internal
     * {@link LinkedHashSet}. Addition will not succeed with a {@code null}
     * processor, {@code this}, or the current main processor (through
     * {@link Input#setInputProcessor}). The internal set works best with input
     * processors that did not override the methods {@link Object#equals} and
     * {@link Object#hashCode()}, wrapper or new-type might be needed otherwise.
     *
     * @param processor  The processor to add.
     * @return {@code boolean} value representing addition success.
     */
    public static boolean register(final InputProcessor processor) {
        return Gdx.input instanceof final FluxInput self && self.addInputProcessor(processor);
    }

    /**
     * Checks input processor presence.
     * <p>
     * Checks if a given processor is present and added through
     * {@code register}. This method does not check against the main
     * processor.
     *
     * @param processor  The processor to check.
     * @return {@code true} if present; {@code false} otherwise.
     * @see #register
     */
    public static boolean isRegistered(final InputProcessor processor) {
        return Gdx.input instanceof final FluxInput self && self.containsInputProcessor(processor);
    }

    /**
     * Removes an input processor.
     * <p>
     * Removes a given processor added through {@code register}. This method
     * cannot remove the main processor, use
     * {@code Input.setInputProcessor(null)}.
     *
     * @param processor  The processor to remove.
     * @return {@code boolean} value representing removal success.
     * @see #register
     * @see Input#setInputProcessor
     */
    public static boolean unregister(final InputProcessor processor) {
        return Gdx.input instanceof final FluxInput self && self.removeInputProcessor(processor);
    }

    private InputProcessorHelper() {}
}

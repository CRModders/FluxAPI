package dev.crmodders.flux.impl.input.client;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

import java.util.LinkedHashSet;

final class FluxInputProcessor implements InputProcessor {
    private static final InputProcessor NOOP = new InputAdapter();

    private final LinkedHashSet<InputProcessor> delegates;

    private InputProcessor main;

    public FluxInputProcessor() {
        this.delegates = new LinkedHashSet<>();
        this.main = NOOP;
    }

    /**
     * Adds an input processor.
     * <p>
     * All added input processors are ran in insertion order through an internal
     * {@link LinkedHashSet}. Addition will not succeed with a {@code null}
     * processor, {@code this}, or the current main processor. The internal set
     * works best if the input processors did not override {@link Object#equals}
     * and {@link Object#hashCode()} and in case that it did, make a wrapper.
     *
     * @param processor  The processor to add.
     * @return {@code boolean} value representing addition success.
     * @see #getMainProcessor
     */
    public boolean addProcessor(final InputProcessor processor) {
        return processor != null
            && processor != this
            && processor != this.main
            && this.delegates.add(processor);
    }

    /**
     * Checks input processor presence.
     * <p>
     * Checks if a given processor is present and added through
     * {@code addProcessor}. This method does not check against the main
     * processor.
     *
     * @param processor  The processor to check.
     * @return {@code true} if present; {@code false} otherwise.
     * @see #addProcessor
     */
    public boolean containsProcessor(final InputProcessor processor) {
        return processor != null && this.delegates.contains(processor);
    }

    /**
     * Removes an input processor.
     * <p>
     * Removes a given processor added through {@code addProcessor}. This method
     * cannot remove the main processor, use {@code setMainProcessor(null)}.
     *
     * @param processor  The processor to remove.
     * @return {@code boolean} value representing removal success.
     * @see #addProcessor
     * @see #setMainProcessor
     */
    public boolean removeProcessor(final InputProcessor processor) {
        return processor != null && this.delegates.remove(processor);
    }

    /**
     * The main front-facing input processor.
     *
     * @return The main processor.
     * @see #setMainProcessor
     */
    public InputProcessor getMainProcessor() {
        final var main = this.main;
        return main == NOOP ? null : main;
    }

    /**
     * Sets the main processor.
     * <p>
     * The main processor is the front facing processor visible through
     * {@link FluxInput#getInputProcessor} where this method is the delegate
     * of {@link FluxInput#setInputProcessor}. Delegated
     * {@link com.badlogic.gdx.Input}s will return a {@code FluxInputProcessor}.
     * <p>
     * If the given processor is already present through {@link #addProcessor},
     * it's removed, as if through {@link #removeProcessor}, will it then be set
     * as the new main processor.
     * <p>
     * The previous main processor is replaced and is not re-added to the
     * processor list if it was promoted from {@code addProcessor}. {@code null}
     * is valid and will behave as a no-op as if a blank extension of
     * {@link InputAdapter}.
     *
     * @param processor  The new main processor.
     */
    public void setMainProcessor(final InputProcessor processor) {
        if (processor == null) {
            this.main = NOOP;
        } else {
            this.delegates.remove(processor);
            this.main = processor;
        }
    }

    @Override
    public boolean keyDown(final int keycode) {
        final var result = this.main.keyDown(keycode);
        for (final var delegate : this.delegates) {
            delegate.keyDown(keycode);
        }
        return result;
    }

    @Override
    public boolean keyUp(final int keycode) {
        final var result = this.main.keyUp(keycode);
        for (final var delegate : this.delegates) {
            delegate.keyUp(keycode);
        }
        return result;
    }

    @Override
    public boolean keyTyped(final char character) {
        final var result = this.main.keyUp(character);
        for (final var delegate : this.delegates) {
            delegate.keyUp(character);
        }
        return result;
    }

    @Override
    public boolean touchDown(
        final int screenX,
        final int screenY,
        final int pointer,
        final int button
    ) {
        final var result = this.main.touchDown(screenX, screenY, pointer, button);
        for (final var delegate : this.delegates) {
            delegate.touchDown(screenX, screenY, pointer, button);
        }
        return result;
    }

    @Override
    public boolean touchUp(
        final int screenX,
        final int screenY,
        final int pointer,
        final int button
    ) {
        final var result = this.main.touchUp(screenX, screenY, pointer, button);
        for (final var delegate : this.delegates) {
            delegate.touchUp(screenX, screenY, pointer, button);
        }
        return result;
    }

    @Override
    public boolean touchCancelled(
        final int screenX,
        final int screenY,
        final int pointer,
        final int button
    ) {
        final var result = this.main.touchCancelled(screenX, screenY, pointer, button);
        for (final var delegate : this.delegates) {
            delegate.touchCancelled(screenX, screenY, pointer, button);
        }
        return result;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        final var result = this.main.touchDragged(screenX, screenY, pointer);
        for (final var delegate : this.delegates) {
            delegate.touchDragged(screenX, screenY, pointer);
        }
        return result;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        final var result = this.main.mouseMoved(screenX, screenY);
        for (final var delegate : this.delegates) {
            delegate.mouseMoved(screenX, screenY);
        }
        return result;
    }

    @Override
    public boolean scrolled(final float amountX, final float amountY) {
        final var result = this.main.scrolled(amountX, amountY);
        for (final var delegate : this.delegates) {
            delegate.scrolled(amountX, amountY);
        }
        return result;
    }
}

package dev.crmodders.flux.impl.input.client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.Objects;

public final class FluxInput implements Input {
    private final Input delegate;

    private final FluxInputProcessor inputProcessor;

    public FluxInput(final Input delegate) {
        Objects.requireNonNull(delegate);

        this.delegate = delegate;
        this.inputProcessor = new FluxInputProcessor();

        this.inputProcessor.setMainProcessor(this.delegate.getInputProcessor());
        this.delegate.setInputProcessor(this.inputProcessor);
    }

    /**
     * Adds an input processor.
     *
     * @param processor  The processor to add.
     * @return {@code boolean} value representing addition success.
     * @see FluxInputProcessor#addProcessor
     */
    public boolean addInputProcessor(final InputProcessor processor) {
        return this.inputProcessor.addProcessor(processor);
    }

    /**
     * Checks presence of input processor.
     *
     * @param processor  The processor to check.
     * @return {@code true} if present; {@code false} otherwise.
     * @see FluxInputProcessor#containsProcessor
     */
    public boolean containsInputProcessor(final InputProcessor processor) {
        return this.inputProcessor.containsProcessor(processor);
    }

    /**
     * Removes an input processor.
     *
     * @param processor  The processor to remove.
     * @return {@code boolean} value representing removal success.
     * @see FluxInputProcessor#removeProcessor(InputProcessor)
     */
    public boolean removeInputProcessor(final InputProcessor processor) {
        return this.inputProcessor.removeProcessor(processor);
    }

    /**
     * @param processor {@inheritDoc}
     * @see FluxInputProcessor#setMainProcessor
     */
    @Override
    public void setInputProcessor(final InputProcessor processor) {
        this.inputProcessor.setMainProcessor(processor);
    }

    /**
     * @return {@inheritDoc}
     * @see FluxInputProcessor#getMainProcessor
     */
    @Override
    public InputProcessor getInputProcessor() {
        return this.inputProcessor.getMainProcessor();
    }

    ////////////////////////////////
    // DELEGATED IMPLEMENTATION
    ////////////////////////////////

    @Override
    public float getAccelerometerX() {
        return delegate.getAccelerometerX();
    }

    @Override
    public float getAccelerometerY() {
        return delegate.getAccelerometerY();
    }

    @Override
    public float getAccelerometerZ() {
        return delegate.getAccelerometerZ();
    }

    @Override
    public float getGyroscopeX() {
        return delegate.getGyroscopeX();
    }

    @Override
    public float getGyroscopeY() {
        return delegate.getGyroscopeY();
    }

    @Override
    public float getGyroscopeZ() {
        return delegate.getGyroscopeZ();
    }

    @Override
    public int getMaxPointers() {
        return delegate.getMaxPointers();
    }

    @Override
    public int getX() {
        return delegate.getX();
    }

    @Override
    public int getX(final int pointer) {
        return delegate.getX(pointer);
    }

    @Override
    public int getDeltaX() {
        return delegate.getDeltaX();
    }

    @Override
    public int getDeltaX(final int pointer) {
        return delegate.getDeltaX(pointer);
    }

    @Override
    public int getY() {
        return delegate.getY();
    }

    @Override
    public int getY(final int pointer) {
        return delegate.getY(pointer);
    }

    @Override
    public int getDeltaY() {
        return delegate.getDeltaY();
    }

    @Override
    public int getDeltaY(final int pointer) {
        return delegate.getDeltaY(pointer);
    }

    @Override
    public boolean isTouched() {
        return delegate.isTouched();
    }

    @Override
    public boolean justTouched() {
        return delegate.justTouched();
    }

    @Override
    public boolean isTouched(final int pointer) {
        return delegate.isTouched(pointer);
    }

    @Override
    public float getPressure() {
        return delegate.getPressure();
    }

    @Override
    public float getPressure(final int pointer) {
        return delegate.getPressure(pointer);
    }

    @Override
    public boolean isButtonPressed(final int button) {
        return delegate.isButtonPressed(button);
    }

    @Override
    public boolean isButtonJustPressed(final int button) {
        return delegate.isButtonJustPressed(button);
    }

    @Override
    public boolean isKeyPressed(final int key) {
        return delegate.isKeyPressed(key);
    }

    @Override
    public boolean isKeyJustPressed(final int key) {
        return delegate.isKeyJustPressed(key);
    }

    @Override
    public void getTextInput(
        final TextInputListener listener,
        final String title,
        final String text,
        final String hint
    ) {
        delegate.getTextInput(listener, title, text, hint);
    }

    @Override
    public void getTextInput(
        final TextInputListener listener,
        final String title,
        final String text,
        final String hint,
        final OnscreenKeyboardType type
    ) {
        delegate.getTextInput(listener, title, text, hint, type);
    }

    @Override
    public void setOnscreenKeyboardVisible(final boolean visible) {
        delegate.setOnscreenKeyboardVisible(visible);
    }

    @Override
    public void setOnscreenKeyboardVisible(final boolean visible, final OnscreenKeyboardType type) {
        delegate.setOnscreenKeyboardVisible(visible, type);
    }

    @Override
    public void vibrate(final int milliseconds) {
        delegate.vibrate(milliseconds);
    }

    @Override
    public void vibrate(final int milliseconds, final boolean fallback) {
        delegate.vibrate(milliseconds, fallback);
    }

    @Override
    public void vibrate(final int milliseconds, final int amplitude, final boolean fallback) {
        delegate.vibrate(milliseconds, amplitude, fallback);
    }

    @Override
    public void vibrate(final VibrationType vibrationType) {
        delegate.vibrate(vibrationType);
    }

    @Override
    public float getAzimuth() {
        return delegate.getAzimuth();
    }

    @Override
    public float getPitch() {
        return delegate.getPitch();
    }

    @Override
    public float getRoll() {
        return delegate.getRoll();
    }

    @Override
    public void getRotationMatrix(final float[] matrix) {
        delegate.getRotationMatrix(matrix);
    }

    @Override
    public long getCurrentEventTime() {
        return delegate.getCurrentEventTime();
    }

    @Deprecated
    @Override
    public void setCatchBackKey(final boolean catchBack) {
        delegate.setCatchBackKey(catchBack);
    }

    @Deprecated
    @Override
    public boolean isCatchBackKey() {
        return delegate.isCatchBackKey();
    }

    @Deprecated
    @Override
    public void setCatchMenuKey(final boolean catchMenu) {
        delegate.setCatchMenuKey(catchMenu);
    }

    @Deprecated
    @Override
    public boolean isCatchMenuKey() {
        return delegate.isCatchMenuKey();
    }

    @Override
    public void setCatchKey(final int keycode, final boolean catchKey) {
        delegate.setCatchKey(keycode, catchKey);
    }

    @Override
    public boolean isCatchKey(final int keycode) {
        return delegate.isCatchKey(keycode);
    }

    @Override
    public boolean isPeripheralAvailable(final Peripheral peripheral) {
        return delegate.isPeripheralAvailable(peripheral);
    }

    @Override
    public int getRotation() {
        return delegate.getRotation();
    }

    @Override
    public Orientation getNativeOrientation() {
        return delegate.getNativeOrientation();
    }

    @Override
    public void setCursorCatched(final boolean catched) {
        delegate.setCursorCatched(catched);
    }

    @Override
    public boolean isCursorCatched() {
        return delegate.isCursorCatched();
    }

    @Override
    public void setCursorPosition(final int x, final int y) {
        delegate.setCursorPosition(x, y);
    }
}

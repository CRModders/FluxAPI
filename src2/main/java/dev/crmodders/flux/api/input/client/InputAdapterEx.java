package dev.crmodders.flux.api.input.client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public abstract class InputAdapterEx implements InputProcessor {
    public static class Modifiers {
        public static final int ALT = 1;
        public static final int ALT_LEFT = 2;
        public static final int ALT_RIGHT = 4;
        public static final int CONTROL = 8;
        public static final int CONTROL_LEFT = 16;
        public static final int CONTROL_RIGHT = 32;
        public static final int SHIFT = 64;
        public static final int SHIFT_LEFT = 128;
        public static final int SHIFT_RIGHT = 256;

        public static final int ANY_ALT = ALT | ALT_LEFT | ALT_RIGHT;
        public static final int ANY_CONTROL = CONTROL | CONTROL_LEFT | CONTROL_RIGHT;
        public static final int ANY_SHIFT = SHIFT | SHIFT_LEFT | SHIFT_RIGHT;

        public static boolean isAltDown(final int i) {
            return (i & ANY_ALT) != 0;
        }

        public static boolean isControlDown(final int i) {
            return (i & ANY_CONTROL) != 0;
        }

        public static boolean isShiftDown(final int i) {
            return (i & ANY_SHIFT) != 0;
        }

        public static boolean isAltOnly(final int i) {
            return isAltDown(i) && (i & ~ANY_ALT) == 0;
        }

        public static boolean isControlOnly(final int i) {
            return isControlDown(i) && (i & ~ANY_CONTROL) == 0;
        }

        public static boolean isShiftOnly(final int i) {
            return isShiftDown(i) && (i & ~ANY_SHIFT) == 0;
        }
    }

    private int modifiers;

    @Override
    public boolean keyDown(final int keycode) {
        final var modifiers = this.modifiers |= switch (keycode) {
            case Input.Keys.ALT_LEFT -> Modifiers.ALT | Modifiers.ALT_LEFT;
            case Input.Keys.ALT_RIGHT -> Modifiers.ALT | Modifiers.ALT_RIGHT;
            case Input.Keys.SHIFT_LEFT -> Modifiers.SHIFT | Modifiers.SHIFT_LEFT;
            case Input.Keys.SHIFT_RIGHT -> Modifiers.SHIFT | Modifiers.CONTROL_RIGHT;
            case Input.Keys.CONTROL_LEFT -> Modifiers.CONTROL | Modifiers.CONTROL_LEFT;
            case Input.Keys.CONTROL_RIGHT -> Modifiers.CONTROL | Modifiers.CONTROL_RIGHT;
            default -> 0;
        };
        return this.keyDownEx(keycode, modifiers);
    }

    protected abstract boolean keyDownEx(int keycode, int modifiers);

    @Override
    public boolean keyUp(final int keycode) {
        final var modifiers = this.modifiers;
        final var result = this.keyUpEx(keycode, modifiers);

        final int mask1;
        final int mask2;
        switch (keycode) {
            case Input.Keys.ALT_LEFT -> {
                mask1 = Modifiers.ALT | Modifiers.ALT_LEFT;
                mask2 = Modifiers.ALT | Modifiers.ALT_RIGHT;
            }
            case Input.Keys.ALT_RIGHT -> {
                mask1 = Modifiers.ALT | Modifiers.ALT_RIGHT;
                mask2 = Modifiers.ALT | Modifiers.ALT_LEFT;
            }
            case Input.Keys.CONTROL_LEFT -> {
                mask1 = Modifiers.CONTROL | Modifiers.CONTROL_LEFT;
                mask2 = Modifiers.CONTROL | Modifiers.CONTROL_RIGHT;
            }
            case Input.Keys.CONTROL_RIGHT -> {
                mask1 = Modifiers.CONTROL | Modifiers.CONTROL_RIGHT;
                mask2 = Modifiers.CONTROL | Modifiers.CONTROL_LEFT;
            }
            case Input.Keys.SHIFT_LEFT -> {
                mask1 = Modifiers.SHIFT | Modifiers.SHIFT_LEFT;
                mask2 = Modifiers.SHIFT | Modifiers.SHIFT_RIGHT;
            }
            case Input.Keys.SHIFT_RIGHT -> {
                mask1 = Modifiers.SHIFT | Modifiers.SHIFT_RIGHT;
                mask2 = Modifiers.SHIFT | Modifiers.SHIFT_LEFT;
            }
            default -> {
                mask1 = 0;
                mask2 = 0;
            }
        }
        final var mask = (modifiers & mask2) != mask2 ? mask1 : mask1 & ~mask2;
        this.modifiers = modifiers & ~mask;

        return result;
    }

    protected abstract boolean keyUpEx(int keycode, int modifiers);

    @Override
    public boolean touchDown(
        final int screenX,
        final int screenY,
        final int pointer,
        final int button
    ) {
        return this.touchDownEx(screenX, screenY, pointer, button, this.modifiers);
    }

    protected abstract boolean touchDownEx(int screenX, int screenY, int pointer, int button, int modifiers);

    @Override
    public boolean touchUp(
        final int screenX,
        final int screenY,
        final int pointer,
        final int button
    ) {
        return this.touchUpEx(screenX, screenY, pointer, button, this.modifiers);
    }

    protected abstract boolean touchUpEx(int screenX, int screenY, int pointer, int button, int modifiers);

    @Override
    public boolean touchCancelled(
        final int screenX,
        final int screenY,
        final int pointer,
        final int button
    ) {
        return this.touchCancelledEx(screenX, screenY, pointer, button, this.modifiers);
    }

    protected abstract boolean touchCancelledEx(int screenX, int screenY, int pointer, int button, int modifiers);

    @Override
    public boolean touchDragged(
        final int screenX,
        final int screenY,
        final int pointer
    ) {
        return this.touchDraggedEx(screenX, screenY, pointer, this.modifiers);
    }

    protected abstract boolean touchDraggedEx(int screenX, int screenY, int pointer, int modifiers);

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return this.mouseMovedEx(screenX, screenY, this.modifiers);
    }

    protected abstract boolean mouseMovedEx(int screenX, int screenY, int modifiers);

    @Override
    public boolean scrolled(final float amountX, final float amountY) {
        return this.scrolledEx(amountX, amountY, this.modifiers);
    }

    protected abstract boolean scrolledEx(float amountX, float amountY, int modifiers);
}

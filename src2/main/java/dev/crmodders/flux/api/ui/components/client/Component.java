package dev.crmodders.flux.api.ui.components.client;

import static finalforeach.cosmicreach.ui.UIElement.*;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.input.client.InputAdapterEx;
import dev.crmodders.flux.api.input.client.MouseProcessorEx;
import dev.crmodders.flux.api.math.Intersection;
import finalforeach.cosmicreach.audio.SoundManager;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIObject;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Experimental
public class Component implements MouseProcessorEx, UIObject {
    public static Color defaultTextColor() {
        final var self = new Color();

        // SAFETY: sets the value to WHITE, without clamping
        self.a = 1.0f;
        self.r = 1.0f;
        self.g = 1.0f;
        self.b = 1.0f;

        return self;
    }

    ///////////////////////////////
    // SIZE AND POSITIONING
    ///////////////////////////////

    /**
     * Stores the anchor position and size.
     */
    private final Rectangle bounds;

    @Override
    public void setX(final float x) {
        this.bounds.x = x;
    }

    @Override
    public void setY(final float y) {
        this.bounds.y = y;
    }

    @Override
    public float getWidth() {
        return this.bounds.width;
    }

    @Override
    public float getHeight() {
        return this.bounds.height;
    }

    private HorizontalAnchor horizontalAnchor;

    public HorizontalAnchor getHorizontalAnchor() {
        return this.horizontalAnchor;
    }

    public void setHorizontalAnchor(final HorizontalAnchor horizontalAnchor) {
        Objects.requireNonNull(horizontalAnchor);
        this.horizontalAnchor = horizontalAnchor;
    }

    public void alignToLeft() {
        this.horizontalAnchor = HorizontalAnchor.LEFT_ALIGNED;
    }

    public void centerHorizontally() {
        this.horizontalAnchor = HorizontalAnchor.CENTERED;
    }

    public void alignToRight() {
        this.horizontalAnchor = HorizontalAnchor.RIGHT_ALIGNED;
    }

    private VerticalAnchor verticalAnchor;

    public VerticalAnchor getVerticalAnchor() {
        return this.verticalAnchor;
    }

    public void setVerticalAnchor(final VerticalAnchor verticalAnchor) {
        Objects.requireNonNull(verticalAnchor);
        this.verticalAnchor = verticalAnchor;
    }

    public void alignToTop() {
        this.verticalAnchor = VerticalAnchor.TOP_ALIGNED;
    }

    public void centerVertically() {
        this.verticalAnchor = VerticalAnchor.CENTERED;
    }

    public void alignToBottom() {
        this.verticalAnchor = VerticalAnchor.BOTTOM_ALIGNED;
    }

    /**
     * The main color, usually used to tint text.
     */
    private final Color color;

    /**
     * Buffer used when dealing with the {@code ScissorStack}.
     */
    private final Rectangle scissors;

    private final Vector2 tmpVec;

    private boolean active;

    private final List<Component> children;

    private final List<? extends Component> childrenView;

    private boolean disabled;

    private int focusedChildIndex;

    private boolean hovered;

    private int hoveredChildIndex;

    private final ComponentInputAdapter inputProcessor;

    private final float[] mouseEnterExit;

    private int prevMouseX;

    private int prevMouseY;

    private @Nullable String text;

    private final Viewport viewport;

    private boolean visible;

    public Component(final Viewport viewport) {
        this.bounds = new Rectangle();
        this.children = new ArrayList<>();
        this.childrenView = Collections.unmodifiableList(this.children);
        this.color = Component.defaultTextColor();
        this.focusedChildIndex = -1;
        this.horizontalAnchor = HorizontalAnchor.CENTERED;
        this.inputProcessor = new ComponentInputAdapter();
        this.mouseEnterExit = new float[Intersection.Convex.BOTH.bufferSize()];
        this.prevMouseX = -1;
        this.prevMouseY = -1;
        this.scissors = new Rectangle();
        this.tmpVec = new Vector2();
        this.verticalAnchor = VerticalAnchor.CENTERED;
        this.viewport = viewport;
    }

    public void addChild(final Component component) {
        this.children.add(component);
    }

    public void insertChild(final Component component, final int index) {
        this.children.add(index, component);

        final var curr = this.focusedChildIndex;
        if (curr >= index) {
            this.focusNextChildFrom(curr);
        }
    }

    public Component removeChildAt(final int index) {
        final var component = this.children.remove(index);

        final var curr = this.focusedChildIndex;
        if (curr == index) {
            this.focusedChildIndex = -1;
        } else if (curr > index) {
            this.focusPrevChildFrom(curr);
        }

        return component;
    }

    public void clearChildren() {
        this.focusedChildIndex = -1;
        this.children.clear();
    }

    public final List<? extends Component> getChildrenView() {
        return this.childrenView;
    }

    protected void focusNextChildFrom(int index) {
        if (++index >= this.children.size()) {
            index = -1;
        }
        this.focusedChildIndex = index;
    }

    protected void focusPrevChildFrom(int index) {
        if (index < 0) {
            index = this.children.size();
        }
        this.focusedChildIndex = index - 1;
    }

    public boolean isHoveredOver(Viewport viewport, float x, float y) {
        float dx = this.getDisplayX(viewport);
        float dy = this.getDisplayY(viewport);
        return x >= dx && y >= dy && x < dx + this.bounds.width && y < dy + this.bounds.height;
    }

    protected float getDisplayX(final Viewport viewport) {
        final var x = this.bounds.x;

        return switch (this.horizontalAnchor) {
            case LEFT_ALIGNED -> x - viewport.getWorldWidth() / 2.0F;
            case RIGHT_ALIGNED -> x + viewport.getWorldWidth() / 2.0F - this.bounds.width;
            default -> x - this.bounds.width / 2.0F;
        };
    }

    protected float getDisplayY(final Viewport viewport) {
        final var y = this.bounds.y;

        return switch (this.verticalAnchor) {
            case TOP_ALIGNED -> y - viewport.getWorldHeight() / 2.0F;
            case BOTTOM_ALIGNED -> y + viewport.getWorldHeight() / 2.0F - this.bounds.height;
            default -> y - this.bounds.height / 2.0F;
        };
    }

    protected float getDisplayX2(final Viewport viewport) {
        final var x = this.bounds.x + this.bounds.width;

        return switch (this.horizontalAnchor) {
            case LEFT_ALIGNED -> x - viewport.getWorldWidth() / 2.0F;
            case RIGHT_ALIGNED -> x + viewport.getWorldWidth() / 2.0F - this.bounds.width;
            default -> x - this.bounds.width / 2.0F;
        };
    }

    protected float getDisplayY2(final Viewport viewport) {
        final var y = this.bounds.y + this.bounds.height;

        return switch (this.verticalAnchor) {
            case TOP_ALIGNED -> y - viewport.getWorldHeight() / 2.0F;
            case BOTTOM_ALIGNED -> y + viewport.getWorldHeight() / 2.0F - this.bounds.height;
            default -> y - this.bounds.height / 2.0F;
        };
    }

    @Override
    public void drawBackground(
        final Viewport viewport,
        final SpriteBatch batch,
        final float mouseX,
        final float mouseY
    ) {
        if (!this.visible) {
            return;
        }

        // Cosmic Reach currently only has hover, no focusing, especially tab
        // focusing, and thus the lack of a dedicated texture.
        final var boundsTexture =
            this.active || this.hovered ? uiPanelHoverBoundsTex : uiPanelBoundsTex;
        final var buttonTexture = this.active ? uiPanelPressedTex : uiPanelTex;
        final var x = this.getDisplayX(viewport);
        final var y = this.getDisplayY(viewport);

        batch.draw(boundsTexture, x, y, 0.0F, 0.0F, this.bounds.width, this.bounds.height, 1.0F, 1.0F, 0.0F, 0, 0, buttonTexture.getWidth(), buttonTexture.getHeight(), false, true);
        batch.draw(buttonTexture, x + 1.0F, y + 1.0F, 1.0F, 1.0F, this.bounds.width - 2.0F, this.bounds.height - 2.0F, 1.0F, 1.0F, 0.0F, 0, 0, buttonTexture.getWidth(), buttonTexture.getHeight(), false, true);
    }

    @Override
    public void drawText(final Viewport viewport, final SpriteBatch batch) {
        final var text = this.text;

        if (!this.visible || text == null || text.isEmpty()) {
            return;
        }

        var x = this.getDisplayX(viewport);
        var y = this.getDisplayY(viewport);

        FontRenderer.getTextDimensions(viewport, text, this.tmpVec);

        if (this.tmpVec.x > this.bounds.width) {
            FontRenderer.drawTextbox(batch, viewport, text, x, y, this.bounds.width);
            return;
        }

        var maxX = x;
        var maxY = y;

        for (var i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);

            var f = FontRenderer.getFontTexOfChar(c);

            if (f == null) {
                c = '?';
                f = FontRenderer.getFontTexOfChar(c);
            }

            final var texReg = f.getTexRegForChar(c);
            x -= f.getCharStartPos(c).x % (float) texReg.getRegionWidth();

            switch (c) {
                case '\n' -> {
                    y += (float) texReg.getRegionHeight();
                    x = this.bounds.x;
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
                case ' ' -> {
                    x += f.getCharSize(c).x / 4.0F;
                    maxX = Math.max(maxX, x);
                }
                default -> {
                    x += f.getCharSize(c).x + f.getCharStartPos(c).x % (float) texReg.getRegionWidth() + 2.0F;
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y + (float) texReg.getRegionHeight());
                }
            }
        }

        x = this.getDisplayX(viewport);
        y = this.getDisplayY(viewport);
        x += this.bounds.width / 2.0F - (maxX - x) / 2.0F;
        y += this.bounds.height / 2.0F - (maxY - y) / 2.0F;

        final var oldColor = new Color(batch.getColor());
        batch.setColor(this.color);
        FontRenderer.drawText(batch, viewport, text, x, y);
        batch.setColor(oldColor);
    }

    public @Nullable String getText() {
        return this.text;
    }

    public void setText(final @Nullable String text) {
        this.text = text;
    }

    public @Nullable Component getChildAt(final int index) {
        return 0 <= index && index < this.children.size() ? this.children.get(index) : null;
    }

    public int getFocusedChildIndex() {
        return this.focusedChildIndex;
    }

    public @Nullable Component getFocusedChild() {
        return this.getChildAt(this.focusedChildIndex);
    }

    public int getHoveredChildIndex() {
        return this.hoveredChildIndex;
    }

    public @Nullable Component getHoveredChild() {
        return this.getChildAt(this.hoveredChildIndex);
    }

    @Override
    public void updateText() {

    }

    @Override
    public void show() {
        this.visible = true;
    }

    @Override
    public void hide() {
        this.visible = false;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return this.inputProcessor.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return this.inputProcessor.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return this.inputProcessor.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        var index = -1;

        for (var i = 0; i < this.children.size(); i++) {
            final var child = this.children.get(i);

            if (child != null && child.isHoveredOver(this.viewport, screenX, screenY)) {
                child.mouseEntered(screenX, screenY);
                index = i;
            }
        }

        final var focused = this.getFocusedChild();
        if (focused != null) {
            focused.active = false;
        }

        final var child = this.children.get(index);
        if (child != null) {
            child.active = true;
            child.hovered = true;
        }

        this.hoveredChildIndex = index;

        if (this.inputProcessor.touchDown(screenX, screenY, pointer, button)) {
            SoundManager.INSTANCE.playSound(onClickSound);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        final var child = this.getHoveredChild();
        if (child != null) {
            child.mouseExited(screenX, screenY);
            child.hovered = false;
        }
        this.hoveredChildIndex = 0;
        return this.inputProcessor.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        final var child = this.getHoveredChild();
        if (child != null) {
            child.mouseExited(screenX, screenY);
            child.hovered = false;
        }
        this.hoveredChildIndex = 0;
        return this.inputProcessor.touchCancelled(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.handleMouseMoved(screenX, screenY);
        return this.inputProcessor.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.handleMouseMoved(screenX, screenY);
        return this.inputProcessor.mouseMoved(screenX, screenY);
    }

    protected void handleMouseMoved(final int screenX, final int screenY) {
        final var prevMouseX = this.prevMouseX;
        final var prevMouseY = this.prevMouseY;

        var index = -1;

        for (var i = 0; i < this.children.size(); i++) {
            final var child = this.children.get(i);

            final var axes = Intersection.lineSegmentAndAabbUnchecked(
                prevMouseX,
                prevMouseY,
                screenX,
                screenY,
                this.getDisplayX(this.viewport),
                this.getDisplayY(this.viewport),
                this.getDisplayX2(this.viewport),
                this.getDisplayY2(this.viewport),
                this.mouseEnterExit,
                0
            );

            // Preserves last hovered when a child is entered-exited in one event
            final var prev = index;

            if (axes.entered()) {
                child.mouseEntered(this.mouseEnterExit[0], this.mouseEnterExit[1]);
                index = i;
            }

            if (axes.exited()) {
                child.mouseExited(this.mouseEnterExit[2], this.mouseEnterExit[3]);
                child.hovered = false;
                index = prev;
            }
        }

        final var child = this.children.get(index);
        if (child != null) {
            child.hovered = true;
        }

        this.hoveredChildIndex = index;
        this.prevMouseX = screenX;
        this.prevMouseY = screenY;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return this.inputProcessor.scrolled(amountX, amountY);
    }

    public void setSize(final float width, final float height) {
        this.bounds.setSize(width, height);
    }

    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public boolean mouseEntered(final float screenX, final float screenY) {
        return true;
    }

    @Override
    public boolean mouseExited(final float screenX, final float screenY) {
        return true;
    }

    private final class ComponentInputAdapter extends InputAdapterEx {
        @Override
        protected boolean keyDownEx(final int keycode, final int modifiers) {
            final var index = Component.this.focusedChildIndex;
            final var child = Component.this.getChildAt(index);
            if (child != null && child.keyDown(keycode)) {
                return false;
            }
            if (keycode != Input.Keys.TAB) {
                return true;
            }
            if (modifiers == 0) {
                Component.this.focusNextChildFrom(index);
                return false;
            }
            if (Modifiers.isShiftOnly(modifiers)) {
                Component.this.focusPrevChildFrom(index);
                return false;
            }
            return false;
        }

        @Override
        protected boolean keyUpEx(final int keycode, final int modifiers) {
            final var child = Component.this.getFocusedChild();
            return child == null || child.keyUp(keycode);
        }

        @Override
        protected boolean touchDownEx(
            final int screenX,
            final int screenY,
            final int pointer,
            final int button,
            final int modifiers
        ) {
            final var child = Component.this.getFocusedChild();
            return child == null || child.touchDown(screenX, screenY, pointer, button);
        }

        @Override
        protected boolean touchUpEx(
            final int screenX,
            final int screenY,
            final int pointer,
            final int button,
            final int modifiers
        ) {
            final var child = Component.this.getFocusedChild();
            return child == null || child.touchUp(screenX, screenY, pointer, button);
        }

        @Override
        protected boolean touchCancelledEx(
            final int screenX,
            final int screenY,
            final int pointer,
            final int button,
            final int modifiers
        ) {
            final var child = Component.this.getFocusedChild();
            return child == null || child.touchCancelled(screenX, screenY, pointer, button);
        }

        @Override
        protected boolean touchDraggedEx(
            final int screenX,
            final int screenY,
            final int pointer,
            final int modifiers
        ) {
            final var child = Component.this.getFocusedChild();
            return child == null || child.touchDragged(screenX, screenY, pointer);
        }

        @Override
        protected boolean mouseMovedEx(
            final int screenX,
            final int screenY,
            final int modifiers
        ) {
            final var child = Component.this.getFocusedChild();
            return child == null || child.mouseMoved(screenX, screenY);
        }

        @Override
        protected boolean scrolledEx(
            final float amountX,
            final float amountY,
            final int modifiers
        ) {
            final var child = Component.this.getHoveredChild();
            return child == null || child.scrolled(amountX, amountY);
        }

        @Override
        public boolean keyTyped(final char character) {
            final var child = Component.this.getFocusedChild();
            return child == null || child.keyTyped(character);
        }
    }
}

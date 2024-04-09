package dev.crmodders.flux.api.gui.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.interfaces.Component;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public abstract class BaseElement implements Component {

    public float x, y, width,  height;
    public HorizontalAnchor hAnchor = HorizontalAnchor.CENTERED;
    public VerticalAnchor vAnchor=VerticalAnchor.CENTERED;
    public boolean visible = true;
    protected boolean dirty = true;

    protected boolean mouseInside = false;
    protected boolean mousePressed = false;

    public void setPosition(float x,float y){
        this.x=x;this.y=y;
    }

    public void setBounds(float x,float y,float w,float h){
        this.x=x;this.y=y;this.width=w;this.height=h;
    }

    public void setAnchors(HorizontalAnchor h,VerticalAnchor v){
        this.hAnchor=h;
        this.vAnchor=v;
    }

    public float getDisplayX(Viewport uiViewport) {
        return switch (this.hAnchor) {
            case LEFT_ALIGNED -> this.x - uiViewport.getWorldWidth() / 2.0F;
            case RIGHT_ALIGNED -> this.x + uiViewport.getWorldWidth() / 2.0F - this.width;
            default -> this.x - this.width / 2.0F;
        };
    }

    public float getDisplayY(Viewport uiViewport) {
        return switch (this.vAnchor) {
            case TOP_ALIGNED -> this.y - uiViewport.getWorldHeight() / 2.0F;
            case BOTTOM_ALIGNED -> this.y + uiViewport.getWorldHeight() / 2.0F - this.height;
            default -> this.y - this.height / 2.0F;
        };
    }

    public boolean isHoveredOver(Viewport viewport, float x, float y) {
        float dx = this.getDisplayX(viewport);
        float dy = this.getDisplayY(viewport);
        return x >= dx && y >= dy && x < dx + this.width && y < dy + this.height;
    }

    @Override
    public void update(UIRenderer renderer, Viewport viewport, Vector2 mouse) {
        if(isHoveredOver(viewport, mouse.x, mouse.y)) {
            if(!mouseInside) {
                onMouseEnter();
                mouseInside = true;
            }
        } else {
            if(mouseInside) {
                onMouseExit();
                mouseInside = false;
            }
        }
        if(mouseInside) {
            if(Gdx.input.isButtonJustPressed(0)) {
                onMousePressed();
                mousePressed = true;
            }
        }
        if(!Gdx.input.isButtonPressed(0) && mousePressed) {
            onMouseReleased();
            mousePressed = false;
        }
        if(mousePressed) {
            onMouseDrag(viewport, mouse.x, mouse.y);
        }
    }

    @Override
    public void paint(UIRenderer renderer) {
    }

    @Override
    public void draw(UIRenderer renderer, Viewport viewport) {
    }

    @Override
    public void deactivate() {

    }

    public void repaint(){
        dirty = true;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    public void onMouseEnter() {
    }

    public void onMouseExit() {
    }

    public void onMousePressed() {
    }

    public void onMouseReleased() {
    }

    public void onMouseDrag(Viewport viewport, float x, float y) {

    }

    public void onMouseScroll(float amount) {

    }

}

package dev.crmodders.flux.ui.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ChildViewport {

    private static final Vector2 tmp = new Vector2();

    public Viewport viewport;

    public ChildViewport(Viewport vp) {
        viewport = vp;
    }

    public void setScreenBounds(Viewport parent, float x, float y, float width, float height) {
        float ratioX = (float)parent.getScreenWidth() / parent.getWorldWidth();
        float ratioY = (float)parent.getScreenHeight() / parent.getWorldHeight();
        float sw = width * ratioX;
        float sh = height * ratioY;
        tmp.set(x, y);
        parent.project(tmp);
        viewport.setScreenBounds((int) tmp.x, (int) tmp.y, (int)sw, (int)sh);
    }

    public void apply() {
        viewport.apply();
    }

}

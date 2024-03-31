package dev.crmodders.flux.menus;

import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public class LayoutMenu extends BasicMenu {

    private int ix = 0;
    private int iy = 0;

    protected boolean layout = true;

    public LayoutMenu(GameState previousState) {
        super(previousState);
    }

    protected void setLayoutEnabled(boolean layout) {
        this.layout = layout;
    }

    @Override
    protected void addUIElement(UIElement element) {
        if (layout) {
            element.x = 275.0f * ((float) this.iy - 1.0f);
            element.y = 50 + 75 * this.ix;
            element.w = 250;
            element.h = 50;
            element.vAnchor = VerticalAnchor.TOP_ALIGNED;
            element.updateText();
            element.show();
            this.ix++;
            if (this.ix > 5) {
                this.ix = 0;
                this.iy++;
            }
        }
        super.addUIElement(element);
    }
}

package dev.crmodders.flux.menus;

import dev.crmodders.flux.api.gui.base.BaseElement;
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
    public LayoutMenu() {}

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
            this.ix++;
            if (this.ix > 5) {
                this.ix = 0;
                this.iy++;
            }
        }
        element.updateText();
        element.show();
        super.addUIElement(element);
    }

    @Override
    protected void addFluxElement(BaseElement element) {
        if (layout) {
            element.x = 275.0f * ((float) this.iy - 1.0f);
            element.y = 50 + 75 * this.ix;
            element.width = 250;
            element.height = 50;
            element.vAnchor = VerticalAnchor.TOP_ALIGNED;
            this.ix++;
            if (this.ix > 5) {
                this.ix = 0;
                this.iy++;
            }
        }
        element.visible = true;
        element.repaint();
        super.addFluxElement(element);
    }
}

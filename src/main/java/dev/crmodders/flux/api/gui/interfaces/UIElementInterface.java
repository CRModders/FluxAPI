package dev.crmodders.flux.api.gui.interfaces;

import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.font.Font;

public interface UIElementInterface {

	float displayX(Viewport uiViewport);

	float displayY(Viewport uiViewport);

	void update(Viewport uiViewport, float mouseX, float mouseY);

	void setFontSize(float fontSize);

	float getFontSize();

	void setSoundEnabled(boolean on);

	boolean isSoundEnabled();

	void setFont(Font font);

	Font getFont();

	void setActive(boolean on);

	boolean isActive();

	void setBorderEnabled(boolean on);

	boolean isBorderEnabled();

	void setBorderThickness(float thickness);

	float getBorderThickness();

	void setAutomaticSize(boolean on);

	boolean isAutomaticSize();

	void setAutomaticSizePadding(float padding);

	float getAutomaticSizePadding();

}

package dev.crmodders.flux.menus;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.gui.BooleanToggleElement;
import dev.crmodders.flux.api.gui.ButtonElement;
import dev.crmodders.flux.api.gui.SteppedIntSliderElement;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public class FluxOptionMenu extends MenuState {

	private int ix = 0;
	private int iy = 0;

	public FluxOptionMenu(GameState previousState) {
		super(previousState);
		ButtonElement doneButton = new ButtonElement(0.0f, -50.0f, 250.0f, 50.0f, b -> GameState.switchToGameState(previousState));
		doneButton.vAnchor = VerticalAnchor.BOTTOM_ALIGNED;
		doneButton.setText("Done");
		doneButton.show();

		SteppedIntSliderElement msaa = new SteppedIntSliderElement(0, 0, 0, 0, 0, 16, new int[] { 0, 2, 4, 8, 16 }, FluxConstants.antiAliasing, "MSAA: %dx");
		addElement(msaa);

		BooleanToggleElement font = new BooleanToggleElement(0, 0, 0, 0, FluxConstants.replaceFontRenderer, "Vector Font Renderer %s", "On", "Off");
		addElement(font);

		this.uiElements.add(doneButton);
	}

	public void addElement(UIElement element) {
		element.x = 275.0f * ((float) this.iy - 1.0f);
		element.y = 50 + 75 * this.ix;
		element.w = 250;
		element.h = 50;
		element.vAnchor = VerticalAnchor.TOP_ALIGNED;
		element.updateText();
		element.show();
		uiElements.add(element);
		this.ix++;
		if (this.ix > 5) {
			this.ix = 0;
			this.iy++;
		}
	}

}

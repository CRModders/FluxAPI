package dev.crmodders.flux.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.gui.ButtonElement;
import dev.crmodders.flux.api.gui.TextElement;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public class BasicMenu extends GameState {
	protected GameState previousState;

	public BasicMenu(final GameState previousState) {
		this.previousState = previousState;
	}

	protected void addUIElement(UIElement element) {
		element.show();
		this.uiElements.add(element);
	}

	protected void onBack() {
		switchToGameState(previousState);
	}

	protected void addBackButton() {
		ButtonElement button = new ButtonElement(0.0f, -50.0f, 250.0f, 50.0f, b -> onBack(), FluxConstants.TextBack);
		button.vAnchor = VerticalAnchor.BOTTOM_ALIGNED;
		addUIElement(button);
	}

	protected void onDone() {
		switchToGameState(previousState);
	}

	protected void addDoneButton() {
		ButtonElement button = new ButtonElement(0.0f, -50.0f, 250.0f, 50.0f, b -> onDone(), FluxConstants.TextDone);
		button.vAnchor = VerticalAnchor.BOTTOM_ALIGNED;
		addUIElement(button);
	}

	protected void onCancel() {
		switchToGameState(previousState);
	}

	protected void addCancelButton() {
		ButtonElement button = new ButtonElement(-150.0f, -50.0f, 250.0f, 50.0f, b -> onCancel(), FluxConstants.TextCancel);
		button.vAnchor = VerticalAnchor.BOTTOM_ALIGNED;
		addUIElement(button);
	}

	protected void onSave() {
		switchToGameState(previousState);
	}

	protected void addSaveButton() {
		ButtonElement button = new ButtonElement(150.0f, -50.0f, 250.0f, 50.0f, b -> onSave(), FluxConstants.TextSave);
		button.vAnchor = VerticalAnchor.BOTTOM_ALIGNED;
		addUIElement(button);
	}

	protected void onEscape() {
		switchToGameState(previousState);
	}

	protected void addTextElement(int x, int y, float fontSize, TranslationKey textKey) {
		TextElement element = new TextElement(x, y, 0, 0, textKey);
		((UIElementInterface) element).setFontSize(fontSize);
		element.show();
		this.uiElements.add(element);
	}

	@Override
	public void render(float partTime) {
		super.render(partTime);
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			onEscape();
		}
		ScreenUtils.clear(0, 0, 0, 1.0f, true);
		Gdx.gl.glEnable(2929);
		Gdx.gl.glDepthFunc(513);
		Gdx.gl.glEnable(2884);
		Gdx.gl.glCullFace(1029);
		Gdx.gl.glEnable(3042);
		Gdx.gl.glBlendFunc(770, 771);
		this.drawUIElements();
	}

}

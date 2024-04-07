package dev.crmodders.flux.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.gui.ButtonElement;
import dev.crmodders.flux.api.gui.TextElement;
import dev.crmodders.flux.api.gui.base.BaseElement;
import dev.crmodders.flux.api.gui.interfaces.GameStateInterface;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
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

	protected void addFluxElement(BaseElement element) {
		element.visible = true;
		((GameStateInterface) this).getComponents().add(element);
	}

	protected void onBack() {
		switchToGameState(previousState);
	}

	protected void addBackButton() {
		ButtonElement button = new ButtonElement(this::onBack);
		button.setBounds(0.0f, -50.0f, 250.0f, 50.0f);
		button.translation = FluxConstants.TextBack;
		button.setAnchors(HorizontalAnchor.CENTERED, VerticalAnchor.BOTTOM_ALIGNED);
		button.updateText();
		addFluxElement(button);
	}

	protected void onDone() {
		switchToGameState(previousState);
	}

	protected void addDoneButton() {
		ButtonElement button = new ButtonElement(this::onDone);
		button.setBounds(0.0f, -50.0f, 250.0f, 50.0f);
		button.translation = FluxConstants.TextDone;
		button.setAnchors(HorizontalAnchor.CENTERED, VerticalAnchor.BOTTOM_ALIGNED);
		button.updateText();
		addFluxElement(button);
	}

	protected void onCancel() {
		switchToGameState(previousState);
	}

	protected void addCancelButton() {
		ButtonElement button = new ButtonElement(this::onCancel);
		button.setBounds(-150.0f, -50.0f, 250.0f, 50.0f);
		button.translation = FluxConstants.TextCancel;
		button.setAnchors(HorizontalAnchor.CENTERED, VerticalAnchor.BOTTOM_ALIGNED);
		button.updateText();
		addFluxElement(button);
	}

	protected void onSave() {
		switchToGameState(previousState);
	}

	protected void addSaveButton() {
		ButtonElement button = new ButtonElement(this::onSave);
		button.setBounds(150.0f, -50.0f, 250.0f, 50.0f);
		button.translation = FluxConstants.TextSave;
		button.setAnchors(HorizontalAnchor.CENTERED, VerticalAnchor.BOTTOM_ALIGNED);
		button.updateText();
		addFluxElement(button);
	}

	protected void onEscape() {
		switchToGameState(previousState);
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

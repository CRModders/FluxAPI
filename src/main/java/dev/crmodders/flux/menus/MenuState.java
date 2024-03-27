package dev.crmodders.flux.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.OptionsMenu;

public class MenuState extends GameState {
	protected GameState previousState;

	public static void switchToGameStateIf(boolean cond, GameState state) {
		if (cond) {
			switchToGameState(state);
		}
	}

	public MenuState(final GameState previousState) {
		this.previousState = previousState;
	}

	@Override
	public void render(float partTime) {
		super.render(partTime);
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			OptionsMenu.switchToGameState(previousState);
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

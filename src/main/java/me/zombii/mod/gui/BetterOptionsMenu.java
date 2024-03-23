package me.zombii.mod.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.KeybindsMenu;
import finalforeach.cosmicreach.settings.ControlSettings;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import finalforeach.cosmicreach.settings.SoundSettings;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.UISlider;
import io.github.crmodders.flux.menus.BasicMainMenu;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BetterOptionsMenu extends BasicMainMenu {

    GameState previousState;

    public BetterOptionsMenu(GameState lastState) {
        this.previousState = lastState;
    }

    @Override
    public void create() {
        super.create();

        addUIElement("Vsync: On", new UIElement(-137.0F, -200.0F, 250.0F, 50.0F) {
            public void onCreate() {
                super.onCreate();
                this.updateText();
            }

            public void onClick() {
                super.onClick();
                GraphicsSettings.vSyncEnabled.toggleValue();
                Gdx.graphics.setVSync(GraphicsSettings.vSyncEnabled.getValue());
                this.updateText();
            }

            public void updateText() {
                this.setText("Vsync: " + (GraphicsSettings.vSyncEnabled.getValue() ? "On" : "Off"));
            }
        });
        addUIElement("Distslider", new UISlider(6.0F, 96.0F, (float)GraphicsSettings.renderDistanceInChunks.getValue(), 137.0F, -200.0F, 250.0F, 50.0F) {
            public void onCreate() {
                super.onCreate();
                this.updateText();
            }

            public void onMouseUp() {
                super.onMouseUp();
                GraphicsSettings.renderDistanceInChunks.setValue((int)this.currentValue);
                this.updateText();
            }

            public void validate() {
                super.validate();
                this.currentValue = (float)((int)this.currentValue);
                this.updateText();
            }

            public void updateText() {
                this.setText("View Distance: " + (int)this.currentValue + " Chunks");
            }
        });
        addUIElement("Invert Mouse", new UIElement(-137.0F, -125.0F, 250.0F, 50.0F) {
            public void onCreate() {
                super.onCreate();
                this.updateText();
            }

            public void onClick() {
                super.onClick();
                ControlSettings.invertedMouse.toggleValue();
                this.updateText();
            }

            public void updateText() {
                this.setText("Invert Mouse: " + (ControlSettings.invertedMouse.getValue() ? "On" : "Off"));
            }
        });

        addUIElement("Mouse Sensitivity", new UISlider(0.01F, 5.0F, ControlSettings.mouseSensitivity.getValue(), 137.0F, -125.0F, 250.0F, 50.0F) {
            NumberFormat format;

            public void onCreate() {
                super.onCreate();
                this.updateText();
            }

            public void onMouseUp() {
                super.onMouseUp();
                ControlSettings.mouseSensitivity.setValue(this.currentValue);
                this.updateText();
            }

            public void validate() {
                super.validate();
                this.updateText();
            }

            public void updateText() {
                if (this.format == null) {
                    this.format = DecimalFormat.getPercentInstance();
                }

                this.setText("Mouse sensitivity: " + this.format.format((double)this.currentValue));
            }
        });

        addUIElement("Sound: ", new UIElement(-137.0F, -50.0F, 250.0F, 50.0F) {
            public void onCreate() {
                super.onCreate();
                this.updateText();
            }

            public void onClick() {
                super.onClick();
                SoundSettings.toggleSound();
                this.updateText();
            }

            public void updateText() {
                this.setText("Sound: " + (SoundSettings.isSoundEnabled() ? "On" : "Off"));
            }
        });

        addUIElement("Change FOV:", new UISlider(10.0F, 170.0F, GraphicsSettings.fieldOfView.getValue(), 137.0F, -50.0F, 250.0F, 50.0F) {
            public void onCreate() {
                super.onCreate();
                this.updateText();
            }

            public void onMouseUp() {
                super.onMouseUp();
                GraphicsSettings.fieldOfView.setValue(this.currentValue);
                this.updateText();
            }

            public void validate() {
                super.validate();
                this.currentValue = (float)((int)this.currentValue);
                this.updateText();
            }

            public void updateText() {
                this.setText("FOV: " + this.currentValue);
            }
        });

        addUIElement("Change Keybinds", new UIElement(0.0F, 100.0F, 250.0F, 50.0F) {
            public void onClick() {
                super.onClick();
                GameState.switchToGameState(new KeybindsMenu(BetterOptionsMenu.this));
            }
        });

        addUIElement("Done", new UIElement(0.0F, 200.0F, 250.0F, 50.0F) {
            public void onClick() {
                super.onClick();
                GameState.switchToGameState(previousState);
            }
        });

    }

    @Override
    public void render(float partTick) {
        super.render(partTick);
        if (Gdx.input.isKeyJustPressed(111)) {
            switchToGameState(this.previousState);
        }

        ScreenUtils.clear(0.145F, 0.078F, 0.153F, 1.0F, true);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2884);
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
        this.drawUIElements();
    }

}

package dev.crmodders.flux.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.gui.base.BaseElement;
import dev.crmodders.flux.ui.font.Font;
import dev.crmodders.flux.ui.font.FontTexture;
import dev.crmodders.flux.ui.text.TextBatch;
import dev.crmodders.flux.ui.text.TextBatchBuilder;
import dev.crmodders.flux.ui.util.StyleStringParser;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.FontRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UIRenderer {

	public static final String CHARACTER_SET;
	public static final String DEFAULT_FONT = "cosmic-reach";

	static {
		StringBuilder chars = new StringBuilder();

		List<UnicodeBlock> blocks = new ArrayList<>();
		blocks.add(UnicodeBlock.BASIC_LATIN);
		blocks.add(UnicodeBlock.LATIN_1_SUPPLEMENT);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_A);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_ADDITIONAL);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_B);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_C);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_D);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_E);
		blocks.add(UnicodeBlock.CYRILLIC);
		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_A);
		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_B);
		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_C);
		blocks.add(UnicodeBlock.CYRILLIC_SUPPLEMENTARY);
		blocks.add(UnicodeBlock.KATAKANA);
		blocks.add(UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS);
		blocks.add(UnicodeBlock.CJK_COMPATIBILITY);
		blocks.add(UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
		blocks.add(UnicodeBlock.CJK_STROKES);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_E);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_F);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_G);

		for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
			if (blocks.contains(UnicodeBlock.of(c))) {
				chars.append(c);
			}
		}

		CHARACTER_SET = chars.toString();
	}

	public static Texture white;
	public static final Font cosmicReachFont;
	public static Font font;
	public static UIRenderer uiRenderer;
	static {
		Pixmap whitePixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		whitePixel.setColor(1, 1, 1, 1);
		whitePixel.drawPixel(0, 0);
		white = new Texture(whitePixel);

		try {
			Class<?> fontTextureType = Class.forName("finalforeach.cosmicreach.ui.FontTexture");
			List<FontTexture> fontTextures = new ArrayList<>();
			for(Field field : FontRenderer.class.getDeclaredFields()) {
				field.setAccessible(true);
				Object value = field.get(null);
				if(fontTextureType.isInstance(value)) {
					FontTexture tex = new FontTexture(value, fontTextureType);
					fontTextures.add(tex);
				}
			}
			cosmicReachFont = Font.generateFontTextureFont(fontTextures);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		font = cosmicReachFont;
		uiRenderer = new UIRenderer(GameState.batch);
	}

	public Batch batch;
	public ShapeDrawer renderer;

	public UIRenderer(Batch batch) {
		changeBatch(batch);
	}

	public void changeBatch(Batch batch) {
		boolean wasDrawing = this.batch != null && this.batch.isDrawing();
		if(wasDrawing) this.batch.end();
		this.batch = batch;
		this.renderer = new ShapeDrawer(batch, new TextureRegion(white));
		if(wasDrawing) this.batch.begin();
	}

	public TextBatchBuilder buildText() {
		return new TextBatchBuilder(font, 18.0f);
	}

	public TextBatchBuilder buildText(Font font, float fontSize) {
		return new TextBatchBuilder(font, fontSize);
	}

	public TextBatch createText(Font font, float fontSize, String string, Color color) {
		TextBatchBuilder builder = buildText(font, fontSize);
		builder.color(color);
		builder.append(string);
		return builder.build();
	}

	public TextBatch createStyledText(Font font, float fontSize, String string) {
		TextBatchBuilder builder = buildText(font, fontSize);
		StyleStringParser.parse(builder, string, true);
		return builder.build();
	}

	public TextBatch createText(String text, Color color) {
		return createText(font, 18.0f, text, color);
	}

	private boolean wasDrawingBeforeCustomShader;

	public void prepareForCustomShader() {
		wasDrawingBeforeCustomShader = batch.isDrawing();
		if(wasDrawingBeforeCustomShader) batch.end();
	}

	public void resetAfterCustomShader() {
		if(wasDrawingBeforeCustomShader) batch.begin();
		wasDrawingBeforeCustomShader = false;
	}

	public void render(List<Component> components, Camera uiCamera, Viewport uiViewport, Vector2 mouse) {
		Gdx.gl.glDisable(GL11.GL_CULL_FACE);
		Gdx.gl.glEnable(GL13.GL_MULTISAMPLE);
		uiViewport.apply();
		batch.setProjectionMatrix(uiCamera.combined);
		batch.enableBlending();
		batch.begin();
		for(Component component : components) {
			if (component.isDirty()) {
				component.paint(this);
			}
			component.update(this, uiViewport, mouse);
		}
		for (Component component : components) {
			if(component instanceof BaseElement element) {
				element.drawBackground(this, uiViewport);
			}
		}
		for (Component component : components) {
			component.draw(this, uiViewport);
		}
		for (Component component : components) {
			if(component instanceof BaseElement element) {
				element.drawOverlay(this, uiViewport);
			}
		}
		batch.end();
		Gdx.gl.glDisable(GL13.GL_MULTISAMPLE);
	}

}

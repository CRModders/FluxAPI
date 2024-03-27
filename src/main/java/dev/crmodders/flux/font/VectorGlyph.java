package dev.crmodders.flux.font;

import com.badlogic.gdx.math.Vector2;

public class VectorGlyph {
	public final char chr;
	public final Vector2[] geom;
	public final float ascent;
	public final float advance;

	public VectorGlyph(char chr, Vector2[] geom, float ascent, float advance) {
		this.chr = chr;
		this.geom = geom;
		this.ascent = ascent;
		this.advance = advance;
	}

}

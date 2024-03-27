package dev.crmodders.flux.font;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.awt.ShapeReader;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import com.badlogic.gdx.math.Vector2;

public class FontGlyphUtil {

	private static final AffineTransform INVERT_Y = AffineTransform.getScaleInstance(1, -1);
	private static final double FLATNESS_FACTOR = 400;

	public static Geometry createGlyphGeometry(char chr, Font font, FontRenderContext fontContext, ShapeReader reader, GeometryFactory geomFact) {
		GlyphVector gv = font.createGlyphVector(fontContext, String.valueOf(chr));
		List<Geometry> polys = new ArrayList<>();
		for (int i = 0; i < gv.getNumGlyphs(); i++) {
			Geometry geom = reader.read(gv.getGlyphOutline(i).getPathIterator(INVERT_Y, font.getSize2D() / FLATNESS_FACTOR));
			for (int j = 0; j < geom.getNumGeometries(); j++) {
				polys.add(geom.getGeometryN(j));
			}
		}
		return geomFact.buildGeometry(polys);
	}

	public static VectorGlyph createGlyphMetric(char chr, Vector2[] vertices, Font awtFont, FontRenderContext ctx) {
		Rectangle2D bounds = awtFont.getStringBounds(String.valueOf(chr), ctx);
		LineMetrics metric = awtFont.getLineMetrics(String.valueOf(chr), ctx);
		return new VectorGlyph(chr, vertices, metric.getAscent(), (float) bounds.getWidth());
	}

}

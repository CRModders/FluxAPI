package dev.crmodders.flux.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.awt.ShapeReader;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.util.AffineTransformation;
import org.locationtech.jts.geom.util.GeometryFixer;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.polygon.PolygonTriangulator;
import org.locationtech.jts.triangulate.tri.Tri;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

public class VectorFont {
	private VectorGlyph[] glyphs;

	public VectorFont(FileHandle file, String characters) throws FontFormatException, IOException {
		this(Font.createFont(Font.TRUETYPE_FONT, file.file()), characters);
	}

	public VectorFont(Font awtFont, String characters) {
		FontRenderContext ctx = new FontRenderContext(null, true, true);
		GeometryFactory factory = new GeometryFactory();
		ShapeReader reader = new ShapeReader(factory);

		AffineTransformation affine = new AffineTransformation();
		affine.scale(1, -1);

		Map<Character, Vector2[]> glyphs = new HashMap<>();
		for (char c : characters.toCharArray()) {
			if (!awtFont.canDisplay(c))
				continue;

			Geometry geom = FontGlyphUtil.read(c, awtFont, ctx, reader, factory);
			geom = GeometryFixer.fix(geom);
			geom = affine.transform(geom);

			if (geom.getNumPoints() == 0) {
				glyphs.put(c, new Vector2[0]);
				continue;
			}

			DelaunayTriangulationBuilder builder = new DelaunayTriangulationBuilder();
			builder.setSites(geom);
			builder.setTolerance(0);
			Geometry triangulation = builder.getTriangles(factory);

			triangulation = triangulation.intersection(geom);
			List<Vector2> vertices = new ArrayList<>();
			for (Tri tri : new PolygonTriangulator(triangulation).getTriangles()) {
				for (int i = 0; i < 3; i++) {
					vertices.add(new Vector2((float) tri.getCoordinate(i).getX(), (float) tri.getCoordinate(i).getY()));
				}
			}
			glyphs.put(c, vertices.toArray(Vector2[]::new));
		}

		VectorGlyph space = FontGlyphUtil.getGlypth(' ', glyphs.get(' '), awtFont, ctx);
		this.glyphs = new VectorGlyph[65536];
		for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
			if (glyphs.containsKey(c)) {
				this.glyphs[c] = FontGlyphUtil.getGlypth(c, glyphs.get(c), awtFont, ctx);
			} else {
				this.glyphs[c] = space;
			}
		}

	}

	public VectorGlyph glyph(char chr) {
		return glyphs[chr];
	}

}

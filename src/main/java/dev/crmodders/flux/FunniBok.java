package dev.crmodders.flux;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.BlockModelGenerator;
import dev.crmodders.flux.tags.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FunniBok implements IModBlock {

        Pixmap pixmap;
        Identifier id;

        public FunniBok() {
            pixmap = new Pixmap(128, 128, Pixmap.Format.RGB888);
            Random random = new Random();
            for (int x = 0; x < 128; x++) {
                for (int y = 0; y < 128; y++) {
                    int nextInt = random.nextInt(256*256*256);
                    pixmap.drawPixel(x, y, Color.valueOf(String.format("%06x", nextInt)).toIntBits());
                }
            }

            id = new Identifier("test", "test");
        }


        @Override
        public BlockGenerator getBlockGenerator() {
            BlockGenerator generator = new BlockGenerator(id, "test");
            generator.createBlockState("default", "model", true);
            return generator;
        }

        @Override
        public List<BlockModelGenerator> getBlockModelGenerators(Identifier blockId) {
            BlockModelGenerator generator = new BlockModelGenerator(blockId, "model");
            generator.createTexture("texture", pixmap);
            BlockModelGenerator.Cuboid cuboid = generator.createCuboid(0, 0, 0, 128, 128, 128);
            Arrays.stream(cuboid.faces).forEach((f) -> {
                f.texture = "texture";
                f.u1 = 0;
                f.u2 = 128;
                f.v1 = 0;
                f.v2 = 128;
            });

            return List.of(generator);
        }
    }
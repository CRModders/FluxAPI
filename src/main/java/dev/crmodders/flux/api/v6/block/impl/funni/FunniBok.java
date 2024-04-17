package dev.crmodders.flux.api.v6.block.impl.funni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import dev.crmodders.flux.api.v6.block.IModBlock;
import dev.crmodders.flux.api.v6.generators.BlockGenerator;
import dev.crmodders.flux.api.v6.generators.BlockModelGenerator;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FunniBok implements IModBlock {

        Pixmap pixmap;
        Identifier id;

        public FunniBok() {
            pixmap = new Pixmap(ChunkShader.allBlocksTexSize, ChunkShader.allBlocksTexSize, Pixmap.Format.RGB888);
            Random random = new Random();
            for (int x = 0; x < ChunkShader.allBlocksTexSize; x++) {
                for (int y = 0; y < ChunkShader.allBlocksTexSize; y++) {
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
            BlockModelGenerator.Cuboid cuboid = generator.createCuboid(0, 0, 0, ChunkShader.allBlocksTexSize, ChunkShader.allBlocksTexSize, ChunkShader.allBlocksTexSize);
            Arrays.stream(cuboid.faces).forEach((f) -> {
                f.texture = "texture";
                f.u1 = 0;
                f.u2 = ChunkShader.allBlocksTexSize;
                f.v1 = 0;
                f.v2 = ChunkShader.allBlocksTexSize;
            });

//            generator.createColoredCuboid(0, 0, 0, 32, 32, 32, "green", Color.GREEN);
            return List.of(generator);
        }
    }
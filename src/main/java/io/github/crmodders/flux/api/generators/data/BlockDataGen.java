package io.github.crmodders.flux.api.generators.data;

import finalforeach.cosmicreach.world.blocks.Block;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.api.registries.BlockReg;
import io.github.crmodders.flux.api.registries.Identifier;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hjson.Stringify;

public class BlockDataGen implements Datagen {

    public static BlockDataGen createGenerator(Identifier id) {
        return new BlockDataGen(id);
    }

    Identifier id;
    JsonObject Data;

    protected BlockDataGen(Identifier id) {
        this.id = id;
        Data = new JsonObject();
        setID(id.toString());
        setBlockstate("default", BlockStateDataGen.createGenerator());
    }

    public BlockDataGen setBlockstate(String type, BlockStateDataGen gen) {
        Data.set("blockStates", new JsonObject().set(type, gen.Generate()));
        return this;
    }

    public BlockDataGen setID(String id) {
        Data.set("stringId", id);
        this.id = Identifier.fromString(id);
        return this;
    }

    public BlockDataGen setData(String key, JsonValue data) {
        Data.set(key, data);
        return this;
    }

    @Override
    public Block Generate() {
        FluxAPI.LOGGER.info(Data.toString(Stringify.FORMATTED));
        return BlockReg.getBlockFromJson(id, Data.toString());
    }

    public static class BlockStateDataGen {
        public static BlockStateDataGen createGenerator() {
            return new BlockStateDataGen();
        }

        JsonObject Data;

        protected BlockStateDataGen() {
            Data = new JsonObject();
            setData( "modelName", JsonValue.valueOf("model_c4"));
            setData("blockEventsId", JsonValue.valueOf("base:block_events_c4"));
        }

        public BlockStateDataGen setData(String key, JsonValue data) {
            Data.set(key, data);
            return this;
        }

        public JsonObject Generate() {
            FluxAPI.LOGGER.info(Data.toString(Stringify.FORMATTED));
            return Data;
        }
    }
}

package dev.crmodders.flux.loading.block;

import com.badlogic.gdx.utils.Json;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;
import finalforeach.cosmicreach.rendering.blockmodels.IBlockModelInstantiator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BlockModelFactory implements IBlockModelInstantiator {

    public record InstanceKey(String modelName, int rotXZ) {}

    public final Map<InstanceKey, BlockModel> models = new LinkedHashMap<>();

    static Logger logger = LoggerFactory.getLogger("FluxAPI / BlockModelFactory");

    public void registerBlockModel(String modelName, int rotXZ, BlockModel model) {
        final InstanceKey key = new InstanceKey(modelName, rotXZ);
        if (models.containsKey(key)) {
            return;
        }
        models.put(key, model);
    }

    @Override
    public BlockModel getInstance(String modelName, int rotXZ) {
        final InstanceKey key = new InstanceKey(modelName, rotXZ);
        if (models.containsKey(key)) {
            return models.get(key);
        }

        String modelJson = GameAssetLoader.loadAsset("models/blocks/" + modelName + ".json").readString();
        BlockModelFlux model = BlockModelFlux.fromJson(modelJson, modelName, rotXZ);
        if (model.parent != null) {
            getInstance(model.parent, rotXZ);
        }

        models.put(key, model);
        return model;
    }

    public BlockModel createFromJson(String modelName, int rotXZ, String modelJson) {
        final InstanceKey key = new InstanceKey(modelName, rotXZ);
        if (models.containsKey(key)) {
            return models.get(key);
        }

        BlockModelFlux model = BlockModelFlux.fromJson(modelJson, modelName, rotXZ);
        if (model.parent != null) {
            getInstance(model.parent, rotXZ);
        }

        models.put(key, model);
        return model;
    }

    @Override
    public void createSlabInstance(String modelName, BlockState blockState, String modelSlabType, int rotXZ) {
        final InstanceKey key = new InstanceKey(modelName, rotXZ);
        if (models.containsKey(key)) {
            return;
        }

        if (blockState.getModel() instanceof BlockModelFlux oldModel) {
            Json json = new Json();

            StringBuilder slabModelJson = new StringBuilder();
            slabModelJson.append("{\"parent\":\"");
            slabModelJson.append(modelSlabType);
            slabModelJson.append("\", textures:");
            slabModelJson.append(json.toJson(oldModel.getTextures()));
            slabModelJson.append("}");

            String modelJson = slabModelJson.toString();
            BlockModelFlux model = BlockModelFlux.fromJson(modelJson, modelName, rotXZ);
            if (model.parent != null) {
                getInstance(model.parent, rotXZ);
            }

            models.put(key, model);
        } else {
            logger.error("can't create slab instances for '" + blockState.getModel().getClass().getSimpleName() + "'");
        }
    }

    private int getNumberOfParents(BlockModelFlux model) {
        int n = 0;
        String parent = model.parent;
        while (parent != null) {
            BlockModelFlux parentModel = null;

            InstanceKey parentKey;
            if (models.containsKey(parentKey = new InstanceKey(parent, 0)))
                parentModel = (BlockModelFlux) models.get(parentKey);
            else if (models.containsKey(parentKey = new InstanceKey(parent, 90)))
                parentModel = (BlockModelFlux) models.get(parentKey);
            else if (models.containsKey(parentKey = new InstanceKey(parent, 180)))
                parentModel = (BlockModelFlux) models.get(parentKey);
            else if (models.containsKey(parentKey = new InstanceKey(parent, 270)))
                parentModel = (BlockModelFlux) models.get(parentKey);

            parent = parentModel == null ? null : parentModel.parent;
            n++;
        }
        return n;
    }

    public int compare(BlockModel o1, BlockModel o2) {
        if (o1 instanceof BlockModelFlux f1 && o2 instanceof BlockModelFlux f2) {
            return Integer.compare(getNumberOfParents(f1), getNumberOfParents(f2));
        }
        return 0;
    }

    public List<BlockModel> sort() {
        List<BlockModel> models = new ArrayList<>(this.models.values());
        models.sort(this::compare);
        return models;
    }

}
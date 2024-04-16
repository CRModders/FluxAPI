package dev.crmodders.flux.api.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.api.resource.ResourceLocation;
import finalforeach.cosmicreach.io.SaveLocation;

import java.util.ArrayList;
import java.util.List;

public class VanillaAssetLocations {

    public static List<ResourceLocation> getInternalFiles(String folder, String extension) {
        List<ResourceLocation> files = new ArrayList<>();
        String[] internalFilesList = Gdx.files.internal("assets.txt").readString().split("\n");
        for(String internalFileName : internalFilesList) {
            if (internalFileName.startsWith(folder) && internalFileName.endsWith(extension) && Gdx.files.internal(internalFileName).exists()) {
                files.add(new ResourceLocation("base", internalFileName));
            }
        }
        return files;
    }

    public static List<ResourceLocation> getVanillaModFiles(String folder, String extension) {
        List<ResourceLocation> files = new ArrayList<>();
        for(FileHandle f : Gdx.files.absolute(SaveLocation.getSaveFolderLocation() + "/mods/assets/" + folder).list()) {
            String fileName = f.name();
            if (fileName.endsWith(extension)) {
                files.add(new ResourceLocation("base", fileName));
            }
        }
        return files;
    }

    public static ResourceLocation getBlock(String blockName) {
        return new ResourceLocation("base", "blocks/" + blockName + ".json");
    }

    public static ResourceLocation getBlockTexture(String blockTextureName) {
        return new ResourceLocation("base", "textures/blocks/" + blockTextureName + ".png");
    }

    public static ResourceLocation getBlockModel(String blockModelName) {
        return new ResourceLocation("base", "models/blocks/" + blockModelName + ".json");
    }

    public static ResourceLocation getBlockEvents(String blockEventsName) {
        return new ResourceLocation("base", "block_events/" + blockEventsName + ".json");
    }

    public static ResourceLocation getTexture(String textureName) {
        return new ResourceLocation("base", "textures/" + textureName + ".png");
    }

    public static ResourceLocation getLanguage(String languageName) {
        return new ResourceLocation("base", "lang/" + languageName + ".json");
    }

    public static ResourceLocation getFontTexture(String fontTextureName) {
        return new ResourceLocation("base", "lang/textures/" + fontTextureName + ".png");
    }

    public static ResourceLocation getShader(String shaderName) {
        return new ResourceLocation("base", "shaders/" + shaderName + ".glsl");
    }
}

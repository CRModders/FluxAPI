package dev.crmodders.flux.mixins.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.llamalad7.mixinextras.sugar.Local;
import dev.crmodders.flux.impl.assets.AssetFinder;
import dev.crmodders.flux.util.LoaderKind;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

@Mixin(GameAssetLoader.class)
public class GameAssetLoaderMixin {
    @Shadow @Final private static HashMap<String, FileHandle> ALL_ASSETS;

    @Inject(
        method = "forEachAsset(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;Z)V",
        at = @At("RETURN")
    )
    private static void forEachIncludeJarMod(
        final String prefix,
        final String extension,
        final BiConsumer<String, FileHandle> assetConsumer,
        final boolean includeDirectories,
        final CallbackInfo callback,
        final @Local(ordinal = 0) HashSet<Identifier> allPaths
    ) {
        final var subAssets = Identifier.of(prefix).getName();

        final var finder = new AssetFinder(subAssets, extension, (identifier, path) -> {
            final var id = identifier.toString();
            final var handle = Gdx.files.absolute(path.toString());
            ALL_ASSETS.put(id, handle);
            assetConsumer.accept(id, handle);
        });

        LoaderKind.getModLocations()
            .map(Path::normalize)
            .forEach(root -> {
                if (Files.isDirectory(root)) {
                    finder.scan(root);
                    return;
                }
                try (final var zfs = FileSystems.newFileSystem(root)) {
                    finder.scan(zfs.getPath("/"));
                } catch (final ProviderNotFoundException cause) {
                    System.out.println("No file system for " + root + ": " + cause);
                } catch (final IOException cause) {
                    System.out.println("Unable to access file system " + root + ": " + cause);
                }
            });
    }
}

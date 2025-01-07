package dev.crmodders.flux.impl.resource.loader;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.api.resource.loader.FluxFileHandle;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Unique;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class FluxAssetLoading {
    static final Logger LOGGER = LoggerFactory.getLogger("Flux Resource Loader");

    public static void loadJarModAssets(
        final String prefixString,
        final String extension,
        final BiConsumer<String, FileHandle> assetConsumer,
        final boolean includeDirectories,
        final HashMap<? super String, ? super FileHandle> allAssets
    ) {
        final var finder = createAssetFinder(prefixString, extension, assetConsumer, allAssets);
        if (finder == null) {
            return;
        }

        QuiltLoader.getAllMods()
            .stream()
            .filter(mod -> mod.getSourceType() != ModContainer.BasicSourceType.BUILTIN)
            .map(ModContainer::getSourcePaths)
            .flatMap(List::stream)
            .flatMap(List::stream)
            .map(Path::normalize)
            .forEach(root -> {
                if (Files.isDirectory(root)) {
                    finder.scan(root);
                    return;
                }
                try (final var zfs = FileSystems.newFileSystem(root)) {
                    finder.scan(zfs.getPath("/"));
                } catch (final ProviderNotFoundException cause) {
                    LOGGER.warn("No file system provider for {}", root, cause);
                } catch (final IOException cause) {
                    LOGGER.warn("Unable to access file system for {}", root, cause);
                }
            });
    }

    @Unique
    private static @Nullable AssetFinder createAssetFinder(
        final String prefixNotation,
        final String extension,
        final BiConsumer<String, FileHandle> assetConsumer,
        final HashMap<? super String, ? super FileHandle> allAssets
    ) {
        final String namespace;
        final Path prefix;
        {
            final var separator = prefixNotation.indexOf(':');
            final String prefixString;

            if (separator >= 0) {
                namespace = prefixNotation.substring(0, separator);
                prefixString = prefixNotation.substring(separator + 1);
            } else {
                namespace = null;
                prefixString = prefixNotation;
            }

            try {
                prefix = Path.of(prefixString);
            } catch (final InvalidPathException cause) {
                LOGGER.warn("Invalid prefix path: {}", prefixNotation, cause);
                return null;
            }
        }

        return new AssetFinder(namespace, prefix, extension, (identifier, path) -> {
            final var handle = new FluxFileHandle(path);
            final var id = identifier.toString();
            allAssets.put(id, handle);
            assetConsumer.accept(id, handle);
        });
    }
}

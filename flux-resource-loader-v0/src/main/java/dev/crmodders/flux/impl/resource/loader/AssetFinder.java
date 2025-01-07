package dev.crmodders.flux.impl.resource.loader;

import finalforeach.cosmicreach.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.BiConsumer;

import static dev.crmodders.flux.impl.resource.loader.FluxAssetLoading.LOGGER;

/**
 * Helper class to find assets.
 *
 * @param namespace  The specific asset namespace; {@code null} matches any.
 * @param prefix  The assets folder prefix.
 * @param extension  The asset file extension.
 * @param action  The action to run for each asset.
 */
public record AssetFinder(
    @Nullable String namespace,
    Path prefix,
    String extension,
    BiConsumer<? super Identifier, ? super Path> action
) {
    public static void findAll(
        final Path prefix,
        final String extension,
        final Path root,
        final BiConsumer<? super Identifier, ? super Path> action
    ) {
        final var rootAssets = root.normalize().resolve("assets");

        try (final var namespacedPaths = Files.list(rootAssets)) {
            namespacedPaths.forEach((final var namespacedPath) -> {
                final var namespace = rootAssets.relativize(namespacedPath).toString();

                AssetFinder.findNamespaced(
                    namespace,
                    namespacedPath,
                    prefix,
                    extension,
                    action
                );
            });
        } catch (final NoSuchFileException | NotDirectoryException ignored) {

        } catch (final IOException cause) {
            LOGGER.warn("Unable to list namespaces at '{}'", rootAssets, cause);
        }
    }

    public static void findNamespaced(
        final String namespace,
        final Path namespacedPath,
        final Path prefix,
        final String extension,
        final BiConsumer<? super Identifier, ? super Path> action
    ) {
        final var prefixOnFs = namespacedPath.getFileSystem().getPath(prefix.toString());

        try (final var matches = Files.list(namespacedPath.resolve(prefixOnFs))) {
            matches
                .filter(it -> endsWithIgnoreCase(it.getFileName().toString(), extension))
                .filter(Files::isRegularFile)
                .forEach(it -> {
                    final var name = namespacedPath.relativize(it).toString();
                    final var identifier = Identifier.of(namespace, name);
                    action.accept(identifier, it);
                });
        } catch (final NoSuchFileException | NotDirectoryException ignored) {

        } catch (final IOException cause) {
            LOGGER.warn("Unable to list namespaces at '{}'", namespacedPath, cause);
        }
    }

    private static boolean endsWithIgnoreCase(final String self, final String rhs) {
        return self.regionMatches(true, self.length() - rhs.length(), rhs, 0, rhs.length());
    }

    public AssetFinder {
        Objects.requireNonNull(prefix, "Parameter subAssetComponent is null");
        Objects.requireNonNull(extension, "Parameter extension is null");
        Objects.requireNonNull(action, "Parameter action is null");
    }

    public void scan(final Path root) {
        if (this.namespace != null) {
            AssetFinder.findNamespaced(
                this.namespace,
                root.resolve(this.namespace),
                this.prefix,
                this.extension,
                this.action
            );
        } else {
            AssetFinder.findAll(this.prefix, this.extension, root, this.action);
        }
    }
}

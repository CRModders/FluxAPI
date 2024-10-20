package dev.crmodders.flux.impl.assets;

import dev.crmodders.flux.impl.util.Strings;
import finalforeach.cosmicreach.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.BiConsumer;

public record AssetFinder(
    String subAssetComponent,
    String extension,
    BiConsumer<? super Identifier, ? super Path> action
) {
    public static void findInFs(
        final String subAssetComponent,
        final String extension,
        final Path root,
        final BiConsumer<? super Identifier, ? super Path> action
    ) {
        final var rootAssets = root.normalize().resolve("assets");

        try (final var namespacedPaths = Files.list(rootAssets)) {
            namespacedPaths.forEach((final var namespacedPath) -> {
                final var namespace = rootAssets.relativize(namespacedPath).toString();

                try (final var matches = Files.list(namespacedPath.resolve(subAssetComponent))) {
                    matches
                        .filter(it -> Strings.endsWithIgnoreCase(it.getFileName().toString(), extension))
                        .filter(Files::isRegularFile)
                        .forEach(it -> {
                            final var name = namespacedPath.relativize(it).toString();
                            final var identifier = Identifier.of(namespace, name);
                            action.accept(identifier, it);
                        });
                } catch (final NoSuchFileException | NotDirectoryException ignored) {

                } catch (final IOException cause) {
                    System.out.println("Unable to list " + namespacedPath + ": " + cause);
                }
            });
        } catch (final NoSuchFileException | NotDirectoryException ignored) {

        } catch (final IOException cause) {
            System.out.println("Unable to list " + rootAssets + ": " + cause);
        }
    }

    public AssetFinder {
        Objects.requireNonNull(subAssetComponent, "Parameter subAssetComponent is null");
        Objects.requireNonNull(extension, "Parameter extension is null");
        Objects.requireNonNull(action, "Parameter action is null");
    }

    public void scan(final Path root) {
        AssetFinder.findInFs(this.subAssetComponent, this.extension, root, this.action);
    }
}

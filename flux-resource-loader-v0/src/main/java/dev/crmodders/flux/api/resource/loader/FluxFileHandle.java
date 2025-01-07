package dev.crmodders.flux.api.resource.loader;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * A {@link FileHandle} that delegates to Java NIO.
 * <p>
 * Some {@link Path}s may have a non-default {@link FileSystem}. Such paths are
 * not always convertible to {@link File}s. This mostly applies to
 * {@link #file()}, as the conversion happens there. Inherited methods are
 * specialized to use a {@code Path} instead of a {@code File}.
 */
@ApiStatus.Experimental
public class FluxFileHandle extends FileHandle {
    protected Path path;

    protected FluxFileHandle() {
    }

    public FluxFileHandle(final String fileName) {
        this.path = Path.of(fileName);
        this.type = FileType.Absolute;
    }

    public FluxFileHandle(final Path path) {
        this.path = path;
        this.type = FileType.Absolute;
    }

    public FluxFileHandle(final String fileName, final FileType type) {
        this.path = Path.of(fileName);
        this.type = type;
    }

    public FluxFileHandle(final Path path, final FileType type) {
        this.path = path;
        this.type = type;
    }

    @Override
    public String path() {
        return this.path.toString().replace('\\', '/');
    }

    @Override
    public String name() {
        return this.path.getFileName().toString();
    }

    @Override
    public String extension() {
        final var name = this.path.getFileName().toString();
        final var dotIndex = name.lastIndexOf('.');
        return dotIndex == -1 ? name : name.substring(dotIndex + 1);
    }

    @Override
    public String nameWithoutExtension() {
        final var name = this.path.getFileName().toString();
        final var dotIndex = name.lastIndexOf('.');
        return dotIndex == -1 ? name : name.substring(0, dotIndex);
    }

    @Override
    public String pathWithoutExtension() {
        final var name = this.path.toString().replace('\\', '/');
        final var dotIndex = name.lastIndexOf('.');
        return dotIndex == -1 ? name : name.substring(0, dotIndex);
    }

    @Override
    public File file() {
        try {
            this.file = this.path.toFile();
            return super.file();
        } catch (final UnsupportedOperationException cause) {
            throw new RuntimeException("Path handle to java.io.File", cause);
        }
    }

    @Override
    public InputStream read() {
        final var path = this.path;
        final var type = this.type;

        if (
            type != FileType.Classpath
                && (type != FileType.Internal || Files.exists(path))
                && (type != FileType.Local || Files.exists(path))
        ) {
            try {
                return Files.newInputStream(path);
            } catch (final Exception cause) {
                if (Files.isDirectory(path)) {
                    final var message = "Cannot open a stream to a directory: "
                        + path
                        + " ("
                        + type
                        + ")";
                    throw new GdxRuntimeException(message, cause);
                } else {
                    final var message = "Error reading file: " + path + " (" + type + ")";
                    throw new GdxRuntimeException(message, cause);
                }
            }
        } else {
            final var name = "/" + path.toString().replace('\\', '/');
            final var input = FileHandle.class.getResourceAsStream(name);
            if (input == null) {
                throw new GdxRuntimeException("File not found: " + path + " (" + type + ")");
            } else {
                return input;
            }
        }
    }

    @Override
    public ByteBuffer map(final FileChannel.MapMode mode) {
        final var path = this.path;
        final var type = this.type;

        if (type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot map a classpath file: " + this);
        }


        try (final var fileChannel = FileChannel.open(path)) {
            final var map = fileChannel.map(mode, 0L, Files.size(path));
            map.order(ByteOrder.nativeOrder());
            return map;
        } catch (final Exception cause) {
            final var message = "Error memory mapping file: " + this + " (" + type + ")";
            throw new GdxRuntimeException(message, cause);
        }
    }

    @Override
    public OutputStream write(final boolean append) {
        final var path = this.path;
        final var type = this.type;

        if (type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot write to a classpath file: " + path);
        } else if (type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot write to an internal file: " + path);
        } else {
            this.parent().mkdirs();

            try {
                if (append) {
                    return Files.newOutputStream(
                        path,
                        StandardOpenOption.APPEND,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE
                    );
                } else {
                    return Files.newOutputStream(path);
                }
            } catch (final Exception cause) {
                if (Files.isDirectory(path)) {
                    final var message = "Cannot open a stream to a directory: "
                        + path
                        + " ("
                        + type
                        + ")";
                    throw new GdxRuntimeException(message, cause);
                } else {
                    final var message = "Error writing file: " + path + " (" + type + ")";
                    throw new GdxRuntimeException(message, cause);
                }
            }
        }
    }

    @Override
    public void write(final InputStream input, final boolean append) {
        final var path = this.path;
        final var type = this.type;

        OutputStream output = null;

        try {
            output = this.write(append);
            StreamUtils.copyStream(input, output);
        } catch (final Exception cause) {
            final var message = "Error stream writing to file: " + path + " (" + type + ")";
            throw new GdxRuntimeException(message, cause);
        } finally {
            StreamUtils.closeQuietly(input);
            StreamUtils.closeQuietly(output);
        }
    }

    @Override
    public Writer writer(final boolean append, final String charset) {
        final var path = this.path;
        final var type = this.type;

        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot write to a classpath file: " + path);
        }
        if (this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot write to an internal file: " + path);
        }

        this.parent().mkdirs();

        try {
            final OutputStream output;
            if (append) {
                output = Files.newOutputStream(
                    path,
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE
                );
            } else {
                output = Files.newOutputStream(path);
            }
            return charset == null
                ? new OutputStreamWriter(output)
                : new OutputStreamWriter(output, charset);
        } catch (final IOException cause) {
            if (Files.isDirectory(path)) {
                final var message = "Cannot open a stream to a directory: "
                    + path
                    + " ("
                    + type
                    + ")";
                throw new GdxRuntimeException(message, cause);
            } else {
                final var message = "Error writing file: " + path + " (" + type + ")";
                throw new GdxRuntimeException(message, cause);
            }
        }
    }

    @Override
    public void writeString(final String string, final boolean append, final String charset) {
        Writer writer = null;

        final var path = this.path;
        final var type = this.type;

        try {
            writer = this.writer(append, charset);
            writer.write(string);
        } catch (final Exception cause) {
            throw new GdxRuntimeException("Error writing file: " + path + " (" + type + ")", cause);
        } finally {
            StreamUtils.closeQuietly(writer);
        }
    }

    @Override
    public void writeBytes(final byte[] bytes, final boolean append) {
        final var path = this.path;
        final var type = this.type;

        final var output = this.write(append);

        try {
            output.write(bytes);
        } catch (final IOException cause) {
            throw new GdxRuntimeException("Error writing file: " + path + " (" + type + ")", cause);
        } finally {
            StreamUtils.closeQuietly(output);
        }
    }

    @Override
    public void writeBytes(final byte[] bytes, final int offset, final int length, final boolean append) {
        final var path = this.path;
        final var type = this.type;

        final var output = this.write(append);

        try {
            output.write(bytes, offset, length);
        } catch (final IOException cause) {
            throw new GdxRuntimeException("Error writing file: " + path + " (" + type + ")", cause);
        } finally {
            StreamUtils.closeQuietly(output);
        }
    }

    @Override
    public FileHandle[] list() {
        final var path = this.path;

        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + path);
        }

        try (final var children = Files.list(path)) {
            return children
                .map(child -> this.child(child.getFileName().toString()))
                .toArray(FileHandle[]::new);
        } catch (final IOException cause) {
            return new FileHandle[0];
        }
    }

    @Override
    public FileHandle[] list(final FileFilter filter) {
        final var path = this.path;

        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + path);
        }

        try (final var children = Files.list(path)) {
            return children
                // NOTE: Path::toFile is not used here
                .filter(child -> filter.accept(new File(child.toString())))
                .map(child -> this.child(child.getFileName().toString()))
                .toArray(FileHandle[]::new);
        } catch (final IOException cause) {
            return new FileHandle[0];
        }
    }

    @Override
    public FileHandle[] list(final FilenameFilter filter) {
        final var path = this.path;

        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + path);
        }

        try (final var children = Files.list(path)) {
            return children
                .map(child -> child.getFileName().toString())
                // NOTE: Path::toFile is not used here
                .filter(name -> filter.accept(new File(path.toString()), name))
                .map(this::child)
                .toArray(FileHandle[]::new);
        } catch (final IOException cause) {
            return new FileHandle[0];
        }
    }

    @Override
    public FileHandle[] list(final String suffix) {
        final var path = this.path;

        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + path);
        }

        try (final var children = Files.list(path)) {
            return children
                .map(child -> child.getFileName().toString())
                .filter(name -> name.endsWith(suffix))
                .map(this::child)
                .toArray(FileHandle[]::new);
        } catch (final IOException cause) {
            return new FileHandle[0];
        }
    }

    @Override
    public boolean isDirectory() {
        final var path = this.path;
        final var type = this.type;
        return type != FileType.Classpath && Files.isDirectory(path);
    }

    @Override
    public FileHandle child(final String name) {
        final var path = this.path;
        final var type = this.type;
        return new FluxFileHandle(path.resolve(name), type);
    }

    @Override
    public FileHandle sibling(final String name) {
        final var path = this.path;
        final var type = this.type;

        if (path.toString().isEmpty()) {
            throw new GdxRuntimeException("Cannot get the sibling of the root.");
        } else {
            return new FluxFileHandle(path.getParent().resolve(name), type);
        }
    }

    @Override
    public FileHandle parent() {
        final var path = this.path;
        final var type = this.type;

        var parent = path.getParent();
        if (parent == null) {
            if (type == FileType.Absolute) {
                parent = path.getFileSystem().getPath("/");
            } else {
                parent = path.getFileSystem().getPath("");
            }
        }

        return new FluxFileHandle(parent, type);
    }

    @Override
    public void mkdirs() {
        final var path = this.path;
        final var type = this.type;

        if (type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot mkdirs with a classpath file: " + path);
        }
        if (type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot mkdirs with an internal file: " + path);
        }
        try {
            Files.createDirectories(this.path);
        } catch (final Exception ignored) {

        }
    }

    @Override
    public boolean exists() {
        final var path = this.path;
        final var type = this.type;

        switch (type) {
            case Internal:
                if (Files.exists(path)) {
                    return true;
                }
            case Classpath:
                final var resource = "/" + this.path.toString().replace('\\', '/');
                return FileHandle.class.getResource(resource) != null;
            default:
                return Files.exists(path);
        }
    }

    @Override
    public boolean delete() {
        final var path = this.path;
        final var type = this.type;

        if (type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + path);
        }
        if (type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot delete an internal file: " + path);
        }
        try {
            return Files.deleteIfExists(path);
        } catch (final IOException ignored) {
            return false;
        }
    }

    @Override
    public boolean deleteDirectory() {
        final var path = this.path;
        final var type = this.type;

        if (type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + path);
        } else if (type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot delete an internal file: " + path);
        } else {
            return deleteDirectory(path);
        }
    }

    @Override
    public void emptyDirectory() {
        this.emptyDirectory(false);
    }

    @Override
    public void emptyDirectory(final boolean preserveTree) {
        final var path = this.path;
        final var type = this.type;

        if (type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + path);
        } else if (type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot delete an internal file: " + path);
        } else {
            emptyDirectory(path, preserveTree);
        }
    }

    @Override
    public void copyTo(FileHandle dest) {
        if (!this.isDirectory()) {
            if (dest.isDirectory()) {
                dest = dest.child(this.name());
            }

            copyFile(this, dest);
        } else {
            if (dest.exists()) {
                if (!dest.isDirectory()) {
                    final var message = "Destination exists but is not a directory: " + dest;
                    throw new GdxRuntimeException(message);
                }
            } else {
                dest.mkdirs();
                if (!dest.isDirectory()) {
                    final var message = "Destination directory cannot be created: " + dest;
                    throw new GdxRuntimeException(message);
                }
            }

            copyDirectory(this, dest.child(this.name()));
        }
    }

    @Override
    public void moveTo(final FileHandle dest) {
        final var path = this.path;
        final var type = this.type;

        switch (type) {
            case Internal:
                throw new GdxRuntimeException("Cannot move an internal file: " + path);
            case Classpath:
                throw new GdxRuntimeException("Cannot move a classpath file: " + path);
            case Absolute:
            case External:
                if (Files.isDirectory(path)) {
                    return;
                }
            default:
                this.copyTo(dest);
                this.delete();
                if (this.exists() && this.isDirectory()) {
                    this.deleteDirectory();
                }
        }
    }

    @Override
    public long length() {
        final var path = this.path;
        final var type = this.type;

        if (type != FileType.Classpath && (type != FileType.Internal || Files.exists(path))) {
            try {
                return Files.size(path);
            } catch (final Exception ignored) {

            }
        } else {
            final var input = this.read();

            try {
                return input.available();
            } catch (final Exception ignored) {

            } finally {
                StreamUtils.closeQuietly(input);
            }
        }

        return 0L;
    }

    @Override
    public long lastModified() {
        try {
            return Files.getLastModifiedTime(this.path).toMillis();
        } catch (final Exception ignored) {
            return 0L;
        }
    }

    @Override
    public String toString() {
        return this.path.toString().replace('\\', '/');
    }

    public static FileHandle tempFile(final String prefix) {
        try {
            return new FluxFileHandle(Files.createTempFile(prefix, null));
        } catch (final IOException cause) {
            throw new GdxRuntimeException("Unable to create temp file.", cause);
        }
    }

    public static FileHandle tempDirectory(String prefix) {
        try {
            final var path = Files.createTempDirectory(prefix);

            try {
                Files.deleteIfExists(path);
            } catch (final Exception ignored) {
                throw new IOException("Unable to delete temp file: " + path);
            }

            try {
                Files.createDirectory(path);
            } catch (final Exception ignored) {
                throw new IOException("Unable to create temp directory: " + path);
            }

            return new FluxFileHandle(path);
        } catch (final IOException cause) {
            throw new GdxRuntimeException("Unable to create temp file.", cause);
        }
    }

    private static void emptyDirectory(final Path path, final boolean preserveTree) {
        try {
            final var visitor = preserveTree
                ? new PreserveTreeDeleteVisitor<>()
                : new ClearTreeDeleteVisitor<>(path);
            Files.walkFileTree(path, visitor);
        } catch (final Exception ignored) {

        }
    }

    private static boolean deleteDirectory(final Path path) {
        emptyDirectory(path, false);
        try {
            return Files.deleteIfExists(path);
        } catch (final Exception ignored) {
            return false;
        }
    }

    private static void copyFile(final FileHandle source, final FileHandle dest) {
        try {
            dest.write(source.read(), false);
        } catch (final Exception cause) {
            final var message = "Error copying source file: "
                + getPathOrFileString(source)
                + " ("
                + source.type()
                + ")\nTo destination: "
                + getPathOrFileString(dest)
                + " ("
                + dest.type()
                + ")";
            throw new GdxRuntimeException(message, cause);
        }
    }

    private static void copyDirectory(final FileHandle sourceDir, final FileHandle destDir) {
        destDir.mkdirs();

        for (final var srcFile : sourceDir.list()) {
            final var destFile = destDir.child(srcFile.name());

            if (srcFile.isDirectory()) {
                copyDirectory(srcFile, destFile);
            } else {
                copyFile(srcFile, destFile);
            }
        }
    }

    private static Object getPathOrFileString(final FileHandle handle) {
        return handle instanceof final FluxFileHandle flux ? flux.path : handle.file();
    }

    private static sealed class PreserveTreeDeleteVisitor<T extends Path>
    extends SimpleFileVisitor<T>
    {
        @Override
        public FileVisitResult visitFile(
            final T file,
            final BasicFileAttributes attrs
        ) {
            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);
            try {
                Files.delete(file);
            } catch (final IOException ignored) {

            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(final T file, final IOException exc) {
            Objects.requireNonNull(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final T dir, final IOException exc) {
            Objects.requireNonNull(dir);
            return FileVisitResult.CONTINUE;
        }
    }

    private static final class ClearTreeDeleteVisitor<T extends Path>
    extends PreserveTreeDeleteVisitor<T>
    {
        private final T start;

        public ClearTreeDeleteVisitor(final T start) {
            Objects.requireNonNull(start);
            this.start = start;
        }

        @Override
        public FileVisitResult postVisitDirectory(
            final T dir,
            final IOException exc
        ) {
            Objects.requireNonNull(dir);
            if (!this.start.equals(dir)) {
                try {
                    Files.delete(dir);
                } catch (final IOException ignored) {

                }
            }
            return FileVisitResult.CONTINUE;
        }
    }
}

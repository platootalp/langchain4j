package dev.langchain4j.data.document.source;

import static dev.langchain4j.data.document.Document.ABSOLUTE_DIRECTORY_PATH;
import static dev.langchain4j.data.document.Document.FILE_NAME;
import static dev.langchain4j.internal.ValidationUtils.ensureNotNull;

import dev.langchain4j.data.document.DocumentSource;
import dev.langchain4j.data.document.Metadata;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemSource implements DocumentSource {

    private final Path path;

    public FileSystemSource(Path path) {
        this.path = ensureNotNull(path, "path");
    }

    @Override
    public InputStream inputStream() throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public Metadata metadata() {
        return new Metadata()
                .put(FILE_NAME, path.getFileName().toString())
                .put(ABSOLUTE_DIRECTORY_PATH, path.toAbsolutePath().getParent().toString());
    }

    public static FileSystemSource from(Path filePath) {
        return new FileSystemSource(filePath);
    }

    public static FileSystemSource from(String filePath) {
        return new FileSystemSource(Paths.get(filePath));
    }

    public static FileSystemSource from(URI fileUri) {
        return new FileSystemSource(Paths.get(fileUri));
    }

    public static FileSystemSource from(File file) {
        return new FileSystemSource(file.toPath());
    }

    @Override
    public String toString() {
        return "FileSystemSource { path = " + path + " }";
    }
}

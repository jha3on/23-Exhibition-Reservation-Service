package app.share.storage.payload;

import org.springframework.lang.Nullable;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOriginDto implements MultipartFile {
    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final byte[] content;

    public FileOriginDto(String name, @Nullable byte[] content) {
        this(name, "", null, content);
    }

    public FileOriginDto(String name, InputStream contentStream) throws IOException {
        this(name, "", null, FileCopyUtils.copyToByteArray(contentStream));
    }

    public FileOriginDto(String name, @Nullable String originalFilename, @Nullable String contentType, @Nullable byte[] content) {
        this.name = name;
        this.originalFilename = (originalFilename != null ? originalFilename : "");
        this.contentType = contentType;
        this.content = (content != null ? content : new byte[0]);
    }

    public FileOriginDto(String name, @Nullable String originalFilename, @Nullable String contentType, InputStream contentStream) throws IOException {
        this(name, originalFilename, contentType, FileCopyUtils.copyToByteArray(contentStream));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return (this.content.length == 0);
    }

    @Override
    public long getSize() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.content, dest);
    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(getInputStream(), Files.newOutputStream(dest));
    }
}
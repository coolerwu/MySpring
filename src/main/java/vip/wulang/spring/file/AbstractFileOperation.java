package vip.wulang.spring.file;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * It implements {@link IFileOperation}.
 *
 * @author CoolerWu on 2019/1/23.
 * @version 1.0
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractFileOperation implements IFileOperation {
    private static final String EMPTY_STRING = "";
    private File file;
    private FileChannel readChannel;
    private FileChannel writeChannel;
    private ByteBuffer byteBuffer;
    private Boolean appendable;

    public AbstractFileOperation() {
    }

    public AbstractFileOperation(String path, int memorySize) throws IOException, NullPointerException {
        checkFileExistRelative(path);
        if (memorySize <= 0) {
            memorySize = 1024;
        }

        byteBuffer = ByteBuffer.allocate(memorySize);
    }

    @Override
    public void checkFileExistRelative(String path) throws IOException, NullPointerException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(EMPTY_STRING);

        if (Objects.isNull(url)) {
            throw new NullPointerException("url must not be null");
        }

        File file = new File(url.getPath(), path);

        if (file.exists()) {
            this.file = file;
            return;
        }

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return;
        }

        if (file.createNewFile()) {
            this.file = file;
        }
    }

    private void checkFileNotNull() throws FileNotFoundException {
        if (Objects.isNull(file)) {
            throw new FileNotFoundException();
        }
    }

    private void loadReadChannel() throws FileNotFoundException {
        if (Objects.isNull(readChannel)) {
            readChannel = new FileInputStream(file).getChannel();
        }
    }

    private void loadWriteChannel(boolean appendable) throws FileNotFoundException {
        if (Objects.nonNull(this.appendable) && this.appendable.equals(appendable)) {
            return;
        }

        writeChannel = new FileOutputStream(file, appendable).getChannel();
        this.appendable = appendable;
    }

    private void checkByteBuffer() {
        if (Objects.isNull(byteBuffer)) {
            byteBuffer = ByteBuffer.allocate(1024 >> 4);
        }
    }

    @Override
    public byte[] read() throws IOException {
        checkFileNotNull();
        loadReadChannel();
        checkByteBuffer();
        readChannel.read(byteBuffer);
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit() - byteBuffer.position()];
        int index = 0;

        for (int i = byteBuffer.position(); i < byteBuffer.limit(); i++) {
            bytes[index] = byteBuffer.get(i);
            index++;
        }

        return bytes;
    }

    @Override
    public void write(byte[] bytes, boolean appendable) throws IOException {
        checkFileNotNull();
        loadWriteChannel(appendable);
        writeChannel.write(ByteBuffer.wrap(bytes));
    }
}

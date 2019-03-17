package vip.wulang.spring.file;

import java.io.IOException;

/**
 * File Operation.
 *
 * @deprecated nio.
 * @author CoolerWu on 2019/1/23.
 * @version 1.0
 */
@Deprecated
public interface IFileOperation {
    /**
     * If file exists, object will save it.
     * If file does not exists, object will create file and save it.
     *
     * @param path relative path from project.
     * @throws IOException {@link IOException}.
     * @throws NullPointerException {@link NullPointerException}.
     */
    void checkFileExistRelative(String path) throws IOException, NullPointerException;

    /**
     * Get array of byte.
     *
     * @return returns a array.
     * @throws IOException {@link IOException}.
     */
    byte[] read() throws IOException;

    /**
     * add array of byte into file.
     *
     * @param bytes array of byte.
     * @param appendable it decides on overriding.
     * @throws IOException {@link IOException}.
     */
    void write(byte[] bytes, boolean appendable) throws IOException;
}

package vip.wulang.spring.file;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is base on {@link AbstractFileOperation}.
 *
 * @author CoolerWu on 2019/1/23.
 * @version 1.0
 */
@SuppressWarnings("WeakerAccess")
public class BaseFileOperation extends AbstractFileOperation {
    private static BaseFileOperation instance;

    public static boolean createFileOnly(String path) {
        if (Objects.isNull(instance)) {
            instance = new BaseFileOperation();
        }

        if (path.startsWith(".")) {
            path = path.substring(1);
        }

        boolean no_exception = true;

        try {
            instance.checkFileExistRelative(path);
        } catch (Exception e) {
            no_exception = false;
        }

        return no_exception;
    }

    private BaseFileOperation() {
        super();
    }

    public BaseFileOperation(String path, int memorySize) throws IOException {
        super(path, memorySize);
    }
}

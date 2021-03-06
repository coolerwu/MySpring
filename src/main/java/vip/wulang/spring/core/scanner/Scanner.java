package vip.wulang.spring.core.scanner;

import java.io.IOException;
import java.util.List;

/**
 * This interface is used to start scanning.
 *
 * @author CoolerWu on 2018/12/13.
 * @version 1.0
 */
public interface Scanner {

    /**
     * Start scanning.
     * @return scanning results
     */
    List startScan() throws IOException;
}

package vip.wulang.spring.server;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * @author CoolerWu on 2019/2/26.
 * @version 1.0
 */
public class NettyTest {

    @Test
    public void test01() {
        long l = System.currentTimeMillis();
        System.out.println(l);
        System.out.println(Instant.ofEpochMilli(l));
        System.out.println(Instant.ofEpochMilli(l).toEpochMilli());
    }

    @Test
    public void test02() throws IOException, URISyntaxException {
        File file = new File(System.getProperty("user.dir"), "use/sd/1/sda.txt");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
        Path path = Paths.get(new URI(file.getAbsolutePath()));
        Files.createFile(path);
    }
}

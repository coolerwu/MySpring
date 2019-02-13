package vip.wulang.spring.auth;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import vip.wulang.spring.file.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * The class is used for searching authentication.xml and parsing authentication.xml.
 *
 * @author CoolerWu on 2018/12/5.
 * @version 1.0
 */
public class LookupAndParseAuthXml {
    private List<String> containContainer = new ArrayList<>();
    private List<String> equalContainer = new ArrayList<>();

    public LookupAndParseAuthXml() throws IOException, DocumentException {
        lookupAuthXml();
    }

    /**
     * The method is used for searching file whose name is authentication.xml.
     * @throws FileNotFoundException {@link FileNotFoundException}.
     * @throws DocumentException {@link DocumentException}.
     */
    @SuppressWarnings("all")
    private void lookupAuthXml() throws IOException, DocumentException {
        InputStream is = new ClassPathResource("./auth/authentication.xml").getInputStream();
        if (is != null) {
            loadingData(is);
            return;
        }

        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getFile();
        File file = new File(classpath);
        String[] result = new String[1];
        traversalAllFilesToGetAuthenticationXml(file, result);
        if (result[0] == null) {
            throw new FileNotFoundException("Not Found: authentication.xml in project.");
        }

        loadingData(new FileInputStream(result[0]));
    }

    /**
     * The method is used for traversing files to find file whose name is authentication.xml.
     * @param currentFile The parameter represents current file.
     * @param result The parameter represents the result of the search.
     */
    private void traversalAllFilesToGetAuthenticationXml(File currentFile, String[] result) {
        if (result[0] != null) {
            return;
        }

        if (currentFile.isDirectory()) {
            File[] childFiles = currentFile.listFiles();

            if (childFiles == null) {
                return;
            }

            for (File childFile : childFiles) {
                traversalAllFilesToGetAuthenticationXml(childFile, result);
            }
        } else {
            if ("authentication.xml".equals(currentFile.getName())) {
                result[0] = currentFile.getAbsolutePath();
            }
        }
    }

    /**
     * The method is used for loading data.
     * @param is The parameter represents {@link InputStream}.
     * @throws DocumentException {@link DocumentException}.
     */
    @SuppressWarnings("unchecked")
    private void loadingData(InputStream is) throws DocumentException {
        Document document = new SAXReader().read(is);
        Element rootElement = document.getRootElement();
        Iterator<Element> equalsIterator = rootElement.element("equals").elementIterator();
        Iterator<Element> containsIterator = rootElement.element("contains").elementIterator();

        while (equalsIterator.hasNext()) {
            equalContainer.add(((String) equalsIterator.next().getData()));
        }

        while (containsIterator.hasNext()) {
            containContainer.add(((String) containsIterator.next().getData()));
        }
    }

    public List<String> getContainContainer() {
        return containContainer;
    }

    public List<String> getEqualContainer() {
        return equalContainer;
    }
}

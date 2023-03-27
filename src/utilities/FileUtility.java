package utilities;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class FileUtility {

    private FileUtility() {
    }

    /**
     *
     * @param path
     * @param encodingCharset
     * @return
     * @throws IOException
     */
    public static String read(String path, Charset encodingCharset) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(path));
        return new String(fileContent, encodingCharset);
    }

    /**
     *
     * @param path
     * @param encodingCharset
     * @return
     * @throws IOException
     */
    public static List<String> readLines(String path, Charset encodingCharset) throws IOException {
        return Files.readAllLines(Paths.get(path), encodingCharset);
    }

    /**
     *
     * @param path
     * @return
     */
    public static boolean isCSV(String path) {
        String extension = path.substring(path.lastIndexOf('.') + 1);
        return "csv".equals(extension);
    }
}

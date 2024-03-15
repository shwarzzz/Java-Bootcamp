package ex03;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Loader implements Runnable {
    private final String START_STATUS = "start";
    private final String FINISH_STATUS = "finish";
    private final SynchronizedURLMap map;

    public Loader(SynchronizedURLMap map) {
        this.map = map;
    }

    @Override
    public void run() {
        while (!map.isEmpty()) {
            Map.Entry<Integer, URL> data = map.remove();
            if (data == null) {
                continue;
            }
            URL file = data.getValue();
            String filePath = file.getPath();
            String fileName = filePath.
                    substring(filePath.lastIndexOf('/') + 1);
            printDownloadStatus(START_STATUS, data.getKey());
            try (InputStream input = file.openStream()) {
                Files.copy(input, Paths.get(fileName));
                printDownloadStatus(FINISH_STATUS, data.getKey());
            } catch (FileAlreadyExistsException e) {
                System.out.println("File " + e.getMessage() + " already exist");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printDownloadStatus(String status, Integer fileNumber) {
        System.out.println(Thread.currentThread().getName() + " " + status +
                " download file number " + fileNumber);
    }
}

package ex03;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadsFileLoader {
    private final SynchronizedURLMap URLMap;

    private final List<Thread> threadsList;

    private final int threadsCount;

    public ThreadsFileLoader(int threadsCount) {
        URLMap = new SynchronizedURLMap();
        threadsList = new ArrayList<>();
        this.threadsCount = threadsCount;
    }

    public void downloadFiles(String filesURLsPath) {
        loadURLFromFile(filesURLsPath);
        createThreads();
        for (Thread t : threadsList) {
            t.start();
        }
        for (Thread t : threadsList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void createThreads() {
        for (int i = 0; i < threadsCount; i++) {
            threadsList.add(new Thread(new Loader(URLMap),
                    "Thread " + (i + 1)));
        }
    }

    private void loadURLFromFile(String filesURLsPath) {
        try (Scanner inp = new Scanner(Files
                .newInputStream(Paths.get(filesURLsPath)))) {
            while (inp.hasNextLine()) {
                addURLToMap(inp.nextLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addURLToMap(String fileURL) {
        try {
            URLMap.add(new URL(fileURL));
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }
}

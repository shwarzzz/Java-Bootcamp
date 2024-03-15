package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Commander {
    private final int KILOBYTE = 1024;
    private Path directory;

    public Commander(String startDirectory) {
        checkPath(Paths.get(startDirectory));
    }

    public void changeDirectory(String newPath) {
        checkPath(buildPath(newPath));
    }

    public void showListDirectoryContent() {
        File current = directory.toFile();
        for (File file : current.listFiles()) {
            System.out.println(file.getName() + " "
                    + (file.length() / KILOBYTE) + " KB");
        }
    }

    public void moveFile(String srcPath, String dstPath) {
        Path src = buildPath(srcPath);
        Path dst = buildPath(dstPath);
        if (Files.isDirectory(src)) {
            System.out.println("Source file can't be the directory!");
            return;
        }
        if (Files.isDirectory(dst) && Files.exists(dst)) {
            dst = dst.resolve(src.getFileName());
        }
        try {
            Files.move(src, dst);
        } catch (IOException e) {
            System.out.println("Can't move " + srcPath + " to " + dstPath);
        }
    }

    public String getPath() {
        return directory.toString();
    }

    private Path buildPath(String path) {
        return directory.resolve(Paths.get(path)).normalize();
    }

    private void checkPath(Path newPath) {
        if (Files.notExists(newPath) || !Files.isDirectory(newPath)) {
            throw new IllegalArgumentException("");
        }
        directory = newPath;
    }
}
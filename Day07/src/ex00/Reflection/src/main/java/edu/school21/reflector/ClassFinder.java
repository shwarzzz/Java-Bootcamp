package edu.school21.reflector;

import edu.school21.exception.BadPackageException;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassFinder {
    private static final String PKG_SEPARATOR = ".";
    private static final String DIR_SEPARATOR = "/";
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static String packageName;

    public ClassFinder() {
    }

    public static List<Class<?>> getClasses(String packageName) {
        String scannedPath = packageName.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader()
                .getResource(scannedPath);
        if (scannedUrl == null) {
            throw new BadPackageException("Can't get resources from package");
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(findClasses(file, packageName));
        }
        return classes;
    }

    private static List<Class<?>> findClasses(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(findClasses(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}

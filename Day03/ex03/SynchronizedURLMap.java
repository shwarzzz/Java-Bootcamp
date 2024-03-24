package ex03;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class SynchronizedURLMap {
    private final TreeMap<Integer, URL> urlMap;
    private int filesCount;

    public SynchronizedURLMap() {
        urlMap = new TreeMap<>();
        filesCount = 0;
    }

    public synchronized void add(URL newUrl) {
        urlMap.put(++filesCount, newUrl);
    }

    public synchronized Map.Entry<Integer, URL> remove() {
        Map.Entry<Integer, URL> elem = urlMap.firstEntry();
        urlMap.remove(elem.getKey());
        return elem;
    }

    public synchronized boolean isEmpty() {
        return urlMap.isEmpty();
    }
}

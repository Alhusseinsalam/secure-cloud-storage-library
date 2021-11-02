package dev.husein.securecloudstorage.sync;

public class DirectoryWatcher implements Runnable {

    public DirectoryWatcher() {
    }

    @Override
    public void run() {
        System.out.println("Hello From Directory Watcher");
        // here go check the directory for changes
    }
}

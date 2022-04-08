package dev.husein;

import com.google.api.services.drive.model.File;
import dev.husein.securecloudstorage.cloudstorage.CloudServiceProvider;
import dev.husein.securecloudstorage.cloudstorage.CloudStorageService;
import dev.husein.securecloudstorage.cloudstorage.CloudStorageServiceFactory;
import dev.husein.securecloudstorage.sync.DirectoryWatcher;
import dev.husein.securecloudstorage.util.AppSettingsFileHandler;
import dev.husein.securecloudstorage.util.DirectoryUtils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CloudStorageService service = CloudStorageServiceFactory.create(CloudServiceProvider.GOOGLE_DRIVE);

        // call the thread to check directory every x seconds
//        DirectoryWatcher directoryWatcher = new DirectoryWatcher();
//        ScheduledExecutorService directoryCheckExecutor = Executors.newScheduledThreadPool(1);
//        directoryCheckExecutor.scheduleAtFixedRate(directoryWatcher, 0, 1, TimeUnit.SECONDS);

        //
        List<File> files = service.getAllAppHandledFiles();
        printFiles(files);
//        service.createFolder("", "");
//        DirectoryUtils.createLocalStorageFolders("/mnt/Data/Temp/todo_app");
//        AppSettingsFileHandler.initAppSettingsFile();
//        System.out.println(DirectoryUtils.getAppDataFolderPath());
//        System.out.println("Done!");


        // just for test
//        Thread.sleep(5000);

        // stop the executor when the application exit
//        directoryCheckExecutor.shutdown();
    }

    public static void printFiles(List<File> files) {
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            System.out.printf("%s\t- %s\t- %s\t- %s\t- %s\t- %s\t- %s\n", "name", "id", "size", "type", "parents", "time", "trashed");
            for (File file : files) {
                System.out.printf("%s\t- %s\t- %d\t- %s\t- %s\t- %s\t- %s\n", file.getName(), file.getId(), file.getSize() == null ? null : file.getSize(), file.getMimeType(), file.getParents(), file.getModifiedTime().toString(), file.getTrashed());
            }
        }
    }
}
package dev.husein.securecloudstorage.cloudstorage;

import dev.husein.securecloudstorage.cloudstorage.storageimpl.dropbox.DropboxService;
import dev.husein.securecloudstorage.cloudstorage.storageimpl.googledrive.GoogleDriveService;

public class CloudStorageServiceFactory {


    public static CloudStorageService create(CloudServiceProvider provider) {
        if (provider == CloudServiceProvider.GOOGLE_DRIVE) {
            return GoogleDriveService.getService();
        } else if (provider == CloudServiceProvider.DROPBOX) {
            return DropboxService.getService();
        }

        // log some error
        return null;
    }
}
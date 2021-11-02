package dev.husein.securecloudstorage.cloudstorage.storageimpl.dropbox;

import dev.husein.securecloudstorage.cloudstorage.CloudStorageService;

import java.util.List;

public class DropboxService extends CloudStorageService {
    private static DropboxService service;

    public static DropboxService getService() {
        if (service == null) {
            // initialize dropbox stuff
            service = new DropboxService();
        }
        return service;
    }

    @Override
    public List getAllAppHandledFiles() {
        return null;
    }

    @Override
    public String uploadFile(String newFileNameInCloud, String fileToUploadPath, String parentFolderId, String fileType) {
        return "";
    }

    @Override
    public void downloadFile(String fileId, String fileNameToBeStored) {

    }

    @Override
    public void updateFile(String fileId, String newFilePath) {

    }

    @Override
    public void removeFile(String fileId) {

    }

    @Override
    public String createFolder(String newFolderName, String parentFolderId) {
        return null;
    }

    @Override
    public void removeFolder(String folderId) {

    }

}

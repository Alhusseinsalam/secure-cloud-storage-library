package dev.husein.securecloudstorage.cloudstorage;

import java.util.List;

public abstract class CloudStorageService {
    public abstract List getAllAppHandledFiles();

    public abstract String uploadFile(String newFileNameInCloud, String fileToUploadPath, String parentFolderId, String fileType);

    public abstract void downloadFile(String fileId, String fileNameToBeStored);

    public abstract void updateFile(String fileId, String newFilePath);

    public abstract void removeFile(String fileId);

    public abstract String createFolder(String newFolderName, String parentFolderId);

    public abstract void removeFolder(String folderId);

//    public abstract void createBaseFolder(String baseFolderName);
//
//    public abstract String getBaseFolderId();
}
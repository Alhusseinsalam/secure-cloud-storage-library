package dev.husein.securecloudstorage.cloudstorage.storageimpl.googledrive;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.common.base.Strings;
import dev.husein.securecloudstorage.cloudstorage.CloudStorageService;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleDriveService extends CloudStorageService {
    private static final List<String> SCOPES =  Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String GD_FOLDER_MIMETYPE_VALUE = "application/vnd.google-apps.folder";

    // should be retrieved from the settings file
    private static String BASE_FOLDER_ID;

    private static GoogleDriveService service;
    private static Drive drive;

    public static GoogleDriveService getService() {
        if (service == null) {
            drive = getGoogleDriveService();
            service = new GoogleDriveService();
        }
        return service;
    }

    @Override
    public List<File> getAllAppHandledFiles() {
        List<File> files = new ArrayList<>();
        try {
            FileList result = drive.files().list()
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name, size, mimeType, createdTime, fullFileExtension, modifiedTime, parents, trashed)")
                    .execute();
            files = result.getFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    @Override
    public String uploadFile(String newFileNameInCloud, String fileToUploadPath, String parentFolderId, String fileType) {
        String newFileId = "";
        try {
            File fileMetadata = new File();
            fileMetadata.setName(newFileNameInCloud);
            if (!Strings.isNullOrEmpty(parentFolderId)) {
                fileMetadata.setParents(Collections.singletonList(parentFolderId));
            } else {
                fileMetadata.setParents(Collections.singletonList(BASE_FOLDER_ID));
            }
            java.io.File filePath = new java.io.File(fileToUploadPath);
            FileContent content = new FileContent(fileType, filePath);
            File file = drive.files().create(fileMetadata, content)
                    .setFields("id, parents")
                    .execute();
            newFileId = file.getId();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFileId;
    }

    @Override
    public void downloadFile(String fileId, String fileNameToBeStored) {
        try {
            OutputStream outputStream = new FileOutputStream(fileNameToBeStored);
            drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFile(String fileId, String newFilePath) {
        try {
            File existingFile = getFileById(fileId);
            File newMetadata = new File();
            newMetadata.setName(existingFile.getName());
            newMetadata.setParents(existingFile.getParents());
            newMetadata.setMimeType(existingFile.getMimeType());
            newMetadata.setFileExtension(existingFile.getFileExtension());
            FileContent newFileContent = new FileContent(null, new java.io.File(newFilePath));
            drive.files().update(fileId, newMetadata, newFileContent).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFile(String fileId) {
        try {
            drive.files().delete(fileId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createFolder(String newFolderName, String parentFolderId) {
        String newFolderId = "";
        try {
            File folderMetaData = new File();
            folderMetaData.setName(newFolderName);
            folderMetaData.setMimeType(GD_FOLDER_MIMETYPE_VALUE);
            if (!Strings.isNullOrEmpty(parentFolderId)) {
                folderMetaData.setParents(Collections.singletonList(parentFolderId));
            } else {
                folderMetaData.setParents(Collections.singletonList(BASE_FOLDER_ID));
            }
            File fileContent = drive.files().create(folderMetaData).setFields("id, parents").execute();
            newFolderId = fileContent.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFolderId;
    }

    @Override
    public void removeFolder(String folderId) {
        removeFile(folderId);
    }

    /* Utility Methods */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static Drive getGoogleDriveService() {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public File getFileById(String fileId) {
        try {
            return drive.files().get(fileId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
package dev.husein.securecloudstorage.util;

import com.google.common.base.Strings;

import java.io.File;

public class DirectoryUtils {
    private static String BASE_FOLDER_PATH;
    private static final String APP_DATA_FOLDER_NAME = "/app_data";
    private static final String LIB_SETTINGS_FOLDER_NAME = "/lib_settings";

    public static String getBaseFolderPath() {
        return BASE_FOLDER_PATH;
    }

    private static void setBaseFolderPath(String path) {
        // this case should never happen because i must force the developer to pass a non-null or empty string for the path
        if (Strings.isNullOrEmpty(path)) {
            BASE_FOLDER_PATH = "./SECURE_CLOUD_STORAGE_BASE_FOLDER";
        } else {
            BASE_FOLDER_PATH = path;
        }
    }

    public static String getAppDataFolderPath() {
        return BASE_FOLDER_PATH + APP_DATA_FOLDER_NAME;
    }

    public static String getLibSettingsFolderPath() {
        return BASE_FOLDER_PATH + LIB_SETTINGS_FOLDER_NAME;
    }

    public static void createLocalStorageFolders(String baseFolderPath) {
        setBaseFolderPath(baseFolderPath);

        createFolder(getBaseFolderPath());
        createFolder(getAppDataFolderPath());
        createFolder(getLibSettingsFolderPath());
    }

    private static boolean createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            boolean createFolder = folder.mkdir();
            if (!createFolder) {
                throw new RuntimeException("Can't create folder");
            }
        }
        return true;
    }
}

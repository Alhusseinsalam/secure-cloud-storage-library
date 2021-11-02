package dev.husein.securecloudstorage.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.husein.securecloudstorage.model.AppSettingsFile;

import java.io.File;
import java.io.IOException;


public class AppSettingsFileHandler {
    private static final String LOCAL_SETTINGS_FILE_PATH = DirectoryUtils.getLibSettingsFolderPath() + "/AppSettingsFile.json";
//    private static final String CLOUD_SETTINGS_FILE_ID =
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void initAppSettingsFile() {
        try {
            File file = new File(LOCAL_SETTINGS_FILE_PATH);
            if (file.exists()) return;
            mapper.writer().writeValue(file, new AppSettingsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initCloudAppSettingsFile() {

    }

    public static AppSettingsFile loadAppSettingsFile() {
        try {
            File file = new File(LOCAL_SETTINGS_FILE_PATH);
            return mapper.reader().readValue(file, AppSettingsFile.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateAppSettingsFile(AppSettingsFile appSettingsFile) {
        try {
            File file = new File(LOCAL_SETTINGS_FILE_PATH);
            mapper.writer().writeValue(file, appSettingsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

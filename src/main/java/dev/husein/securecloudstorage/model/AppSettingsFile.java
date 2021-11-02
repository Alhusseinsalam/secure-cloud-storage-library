package dev.husein.securecloudstorage.model;

import com.google.api.client.util.DateTime;

import java.util.Date;

public class AppSettingsFile {
    private String cloudBaseFolderName;
    private String cloudBaseFolderId;
    private String dateTimeSynced;
    private String dateTimeCreated;
    private String dateTimeModified;
    private String appId;

    public AppSettingsFile() {
        dateTimeCreated = new DateTime(new Date()).toString();
    }

    @Override
    public String toString() {
        return "AppSettingsFile{" +
                "cloudBaseFolderName='" + cloudBaseFolderName + '\'' +
                ", cloudBaseFolderId='" + cloudBaseFolderId + '\'' +
                ", dateTimeSynced='" + dateTimeSynced + '\'' +
                ", dateTimeCreated='" + dateTimeCreated + '\'' +
                ", dateTimeModified='" + dateTimeModified + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }

    public String getCloudBaseFolderName() {
        return cloudBaseFolderName;
    }

    public void setCloudBaseFolderName(String cloudBaseFolderName) {
        this.cloudBaseFolderName = cloudBaseFolderName;
    }

    public String getCloudBaseFolderId() {
        return cloudBaseFolderId;
    }

    public void setCloudBaseFolderId(String cloudBaseFolderId) {
        this.cloudBaseFolderId = cloudBaseFolderId;
    }

    public String getDateTimeSynced() {
        return dateTimeSynced;
    }

    public void setDateTimeSynced(String dateTimeSynced) {
        this.dateTimeSynced = dateTimeSynced;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getDateTimeModified() {
        return dateTimeModified;
    }

    public void setDateTimeModified(String dateTimeModified) {
        this.dateTimeModified = dateTimeModified;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

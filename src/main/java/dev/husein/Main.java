package dev.husein;

import com.google.api.services.drive.Drive;
import dev.husein.googledriveapiimp.DriveQuickstart;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        Drive service = DriveQuickstart.getDriveService();
        DriveQuickstart.listAllFiles(service);
    }

}

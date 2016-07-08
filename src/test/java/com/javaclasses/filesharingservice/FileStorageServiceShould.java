package com.javaclasses.filesharingservice;


import com.google.common.io.ByteStreams;
import com.javaclasses.filesharingservice.dao.FileRepository;
import com.javaclasses.filesharingservice.dao.FileRepositoryImpl;
import com.javaclasses.filesharingservice.dao.entities.File;
import com.javaclasses.filesharingservice.dao.entities.User;
import com.javaclasses.filesharingservice.services.FileStorageService;
import com.javaclasses.filesharingservice.services.NoPermissionException;
import com.javaclasses.filesharingservice.services.customdatatypes.*;
import com.javaclasses.filesharingservice.services.impl.FileStorageServiceImpl;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FileStorageServiceShould {


    private final FileRepository fileRepository = new FileRepositoryImpl();
    private final FileStorageService fileStorageService = new FileStorageServiceImpl(fileRepository);

    private final UserID userID = new UserID(0);



    @Test
    public void uploadFile() throws IOException, NoPermissionException {

        final AccessKey key = new AccessKey(1);
        final File file = new File(new FileID(0), "Hey.txt", userID);

        fileStorageService.uploadFile(key, "Hey.txt", new FileInputStream("src/main/resources/Hey.txt"));

        assertEquals("File was not uploaded", file, fileRepository.findFileByID(new FileID(0)));

    }


    @Test
    public void failUploadFileNoPermission() throws IOException{

        final AccessKey key = new AccessKey(0);

        try{
            fileStorageService.uploadFile(key, "Hey.txt", new FileInputStream("src/main/resources/Hey.txt"));
            fail("Expected NoPermissionException was not thrown");
        } catch (NoPermissionException e){
            assertEquals("User does not have a permission to upload the file", e.getMessage());
        }

    }

    @Test
    public void browseUsersFiles() throws NoPermissionException {

        final AccessKey key = new AccessKey(1);

        final Collection<File> files = new ArrayList<File>() {{
            add(new File(new FileID(0), "Empty.txt", userID));
        }};

        assertEquals("Files were not found", files, fileStorageService.browseUsersFiles(key, userID));
    }

    @Test
    public void failBrowseFilesNoPermission() throws IOException{

        final AccessKey key = new AccessKey(0);

        try{
            fileStorageService.browseUsersFiles(key, userID);
            fail("Expected NoPermissionException was not thrown");
        } catch (NoPermissionException e){
            assertEquals("User does not have a permission to browse the files", e.getMessage());
        }

    }

    @Test
    public void downloadFile() throws NoPermissionException, IOException {

        final AccessKey key = new AccessKey(1);

        InputStream inputStream = fileStorageService.downloadFile(key, new FileID(0));

        assertTrue("File was not downloaded", inputStream != null);

    }

    @Test
    public void failDownloadFileNoPermission() throws IOException{

        final AccessKey key = new AccessKey(0);

        try{
            fileStorageService.downloadFile(key, new FileID(0));
            fail("Expected NoPermissionException was not thrown");
        } catch (NoPermissionException e){
            assertEquals("User does not have a permission to download the file", e.getMessage());
        }

    }

    @Test
    public void deleteFile() throws NoPermissionException {

        final AccessKey key = new AccessKey(1);
        final FileID id = new FileID(0);

        final File file = new File(id, "Hey.txt", userID);

        assertEquals("File does not exist before removing", file, fileRepository.findFileByID(id));
        
        fileStorageService.deleteFile(key, id);

        assertEquals("File was not deleted", null, fileRepository.findFileByID(id));

    }

    @Test
    public void failDeleteFileNoPermission() throws NoPermissionException {

        final AccessKey key = new AccessKey(0);
        final FileID id = new FileID(0);

        try {
            fileStorageService.deleteFile(key, id);
            fail("Expected NoPermissionException was not thrown");
        } catch (NoPermissionException e) {
            assertEquals("User does not have a permission to delete the file", e.getMessage());
        }

    }
    }

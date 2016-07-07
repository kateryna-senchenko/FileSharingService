package com.javaclasses.filesharingservice;


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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileStorageServiceShould {


    private final FileRepository fileRepository = new FileRepositoryImpl();
    private final FileStorageService fileStorageService = new FileStorageServiceImpl(fileRepository);
    private final Email email = new Email("makeawish@gmail.com");
    private final Password password = new Password("followthewhiterabbit");
    private final FirstName firstName = new FirstName("Alice");
    private final LastName lastName = new LastName("FromWonderland");
    private final User user = new User(new UserID(1), email, password, firstName, lastName);



    @Test
    public void uploadFile() throws IOException, NoPermissionException {

        final AccessKey key = new AccessKey(1);
        final File file = new File(new FileID(0), "Hey.txt", user);

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
}

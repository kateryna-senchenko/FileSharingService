package com.javaclasses.filesharingservice.dao;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.javaclasses.filesharingservice.dao.entities.File;
import com.javaclasses.filesharingservice.dao.entities.User;
import com.javaclasses.filesharingservice.services.NoPermissionException;
import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;
import com.javaclasses.filesharingservice.services.customdatatypes.FileID;
import com.javaclasses.filesharingservice.services.customdatatypes.UserID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of the FileRepository interface
 */
public class FileRepositoryImpl implements FileRepository{

    private static final Logger log = LoggerFactory.getLogger(FileRepositoryImpl.class);

    private final UserRepository userRepository = new UserRepositoryImpl();

    private AtomicLong count = new AtomicLong(0);

    private Map<FileID, File> files = new HashMap<FileID, File>(){{

        put(new FileID(count.get()), new File(new FileID(count.get()), "Empty.txt", new UserID(0)));
    }};

    private Map<FileID, byte[]> contentStorage = new HashMap<FileID, byte[]>(){{

        try {
            put(new FileID(count.getAndIncrement()), ByteStreams.toByteArray(new FileInputStream("src/main/resources/Hey.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }};

    @Override
    public void uploadFile(AccessKey key, String name, InputStream fileContent) throws NoPermissionException {

        checkNotNull(key, "Access key should not be null");
        checkNotNull(name, "File name should not be null");
        checkNotNull(fileContent, "File content should not be null");

        if(log.isInfoEnabled()){
            log.info("Looking for a user with access key {}", key.getAccessKey());
        }

        User user = userRepository.findActiveUserByAccessKey(key);

        if(user == null){
            log.error("User with access key {} does not have a permission to upload the file", key.getAccessKey());
            throw new NoPermissionException("User does not have a permission to upload the file");
        }

        if(log.isInfoEnabled()){
            log.info("User with access key {} can upload the file", key.getAccessKey());
        }

        FileID fileID = new FileID(count.getAndIncrement());
        try {
            if(log.isDebugEnabled()){
                log.debug("Start uploading the file {} into byte array", name);
            }
            byte[] content = ByteStreams.toByteArray(fileContent);
            contentStorage.put(fileID, content);

            if(log.isInfoEnabled()){
                log.info("File {} uploaded and stored in the file storage", name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        File file = new File(fileID, name, user.getId());

        if(log.isInfoEnabled()){
            log.info("Created entity for file {}", name);
        }

        files.put(file.getId(), file);

    }

    @Override
    public File findFileByID(FileID id) {

        checkNotNull(id, "File id should not be null");

        return files.get(id);
    }

    @Override
    public Collection<File> browseFiles(AccessKey key, UserID userID) throws NoPermissionException {

        checkNotNull(key, "Access key should not be null");
        checkNotNull(userID, "User id should not be null");

        if(log.isInfoEnabled()){
            log.info("Looking for a user with access key {}", key.getAccessKey());
        }

        User user = userRepository.findActiveUserByAccessKey(key);

        if(user == null || !user.getId().equals(userID)){
            log.error("User with access key {} does not have a permission to browse the files", key.getAccessKey());
            throw new NoPermissionException("User does not have a permission to browse the files");
        }

        if(log.isInfoEnabled()){
            log.info("User with access key {} can browse the files", key.getAccessKey());
        }

        Collection<File> userFiles = new ArrayList<>();
        for(File file: files.values()){

            if(file.getOwner().equals(userID)){
                userFiles.add(file);
            }

        }
        return userFiles;

    }

    @Override
    public InputStream downloadFile(AccessKey key, FileID fileID) throws NoPermissionException {

        checkNotNull(key, "Access key should not be null");
        checkNotNull(fileID, "File id should not be null");

        if(log.isInfoEnabled()){
            log.info("Looking for a user with access key {}", key.getAccessKey());
        }

        User user = userRepository.findActiveUserByAccessKey(key);

        if(user == null || !files.get(fileID).getOwner().equals(user.getId())){
            log.error("User with access key {} does not have a permission to download the file", key.getAccessKey());
            throw new NoPermissionException("User does not have a permission to download the file");
        }

        if(log.isInfoEnabled()){
            log.info("User with access key {} can download the file", key.getAccessKey());
        }
        InputStream content = new ByteArrayInputStream(contentStorage.get(fileID));

        return content;
    }

    @Override
    public void deleteFile(AccessKey key, FileID fileID) throws NoPermissionException {

        checkNotNull(key, "Access key should not be null");
        checkNotNull(fileID, "File id should not be null");

        if(log.isInfoEnabled()){
            log.info("Looking for a user with access key {}", key.getAccessKey());
        }

        User user = userRepository.findActiveUserByAccessKey(key);

        if(user == null || !files.get(fileID).getOwner().equals(user.getId())){
            log.error("User with access key {} does not have a permission to delete the file", key.getAccessKey());
            throw new NoPermissionException("User does not have a permission to delete the file");
        }

        if(log.isInfoEnabled()){
            log.info("User with access key {} can delete the file", key.getAccessKey());
        }

        files.remove(fileID);
        contentStorage.remove(fileID);
    }


}

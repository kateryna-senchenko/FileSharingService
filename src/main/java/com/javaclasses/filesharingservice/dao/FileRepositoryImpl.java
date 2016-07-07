package com.javaclasses.filesharingservice.dao;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.javaclasses.filesharingservice.dao.entities.File;
import com.javaclasses.filesharingservice.dao.entities.User;
import com.javaclasses.filesharingservice.services.NoPermissionException;
import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;
import com.javaclasses.filesharingservice.services.customdatatypes.FileID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of the FileRepository interface
 */
public class FileRepositoryImpl implements FileRepository{

    private static final Logger log = LoggerFactory.getLogger(FileRepositoryImpl.class);

    private final UserRepository userRepository = new UserRepositoryImpl();

    private long count = 0;

    private Map<FileID, File> files = new HashMap<>();

    private Map<FileID, byte[]> contentStorage = new HashMap<>();

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

        FileID fileID = new FileID(count++);
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


        File file = new File(fileID, name, user);

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


}

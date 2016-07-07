package com.javaclasses.filesharingservice.services.impl;

import com.javaclasses.filesharingservice.dao.FileRepository;
import com.javaclasses.filesharingservice.dao.FileRepositoryImpl;
import com.javaclasses.filesharingservice.services.FileStorageService;
import com.javaclasses.filesharingservice.services.NoPermissionException;
import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Implementation of the FileStorageService interface
 */
public class FileStorageServiceImpl implements FileStorageService{

    private static final Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private final FileRepository fileRepository;

    public FileStorageServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Override
    public void uploadFile(AccessKey key, String fileName, InputStream fileContent) throws NoPermissionException {

        if(log.isInfoEnabled()){
            log.info("Start uploading file {}", fileName);
        }

        fileRepository.uploadFile(key, fileName, fileContent);

    }
}

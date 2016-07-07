package com.javaclasses.filesharingservice.services.impl;

import com.javaclasses.filesharingservice.dao.FileRepository;
import com.javaclasses.filesharingservice.dao.FileRepositoryImpl;
import com.javaclasses.filesharingservice.dao.entities.File;
import com.javaclasses.filesharingservice.services.FileStorageService;
import com.javaclasses.filesharingservice.services.NoPermissionException;
import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;
import com.javaclasses.filesharingservice.services.customdatatypes.FileID;
import com.javaclasses.filesharingservice.services.customdatatypes.UserID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Collection;

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

    @Override
    public Collection<File> browseUsersFiles(AccessKey key, UserID userID) throws NoPermissionException {

        if(log.isInfoEnabled()){
            log.info("Start browsing files of user with id {}", userID.getUserID());
        }
        Collection<File> userFiles = fileRepository.browseFiles(key, userID);

        if(log.isInfoEnabled()){
            log.info("Formed collection of files for user with id {}", userID.getUserID());
        }

        return userFiles;
    }

    @Override
    public InputStream downloadFile(AccessKey key, FileID fileID) throws NoPermissionException {

        if(log.isInfoEnabled()){
            log.info("Start downloading file with id {}", fileID.getId());
        }

        InputStream inputStream = fileRepository.downloadFile(key, fileID);

        if(log.isInfoEnabled()){
            log.info("Formed input stream");
        }

        return inputStream;
    }
}

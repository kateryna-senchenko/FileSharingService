package com.javaclasses.filesharingservice.dao;

import com.javaclasses.filesharingservice.dao.entities.File;
import com.javaclasses.filesharingservice.services.NoPermissionException;
import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;
import com.javaclasses.filesharingservice.services.customdatatypes.FileID;
import com.javaclasses.filesharingservice.services.customdatatypes.UserID;

import java.io.InputStream;
import java.util.Collection;

/**
 * Interface of the service that manages File entities
 */
public interface FileRepository {


    void uploadFile(AccessKey key, String name, InputStream fileContent) throws NoPermissionException;


    File findFileByID(FileID id);

    Collection<File> browseFiles(AccessKey key, UserID userID) throws NoPermissionException;

    InputStream downloadFile(AccessKey key, FileID fileID) throws NoPermissionException;

    void deleteFile(AccessKey key, FileID fileID) throws NoPermissionException;
}

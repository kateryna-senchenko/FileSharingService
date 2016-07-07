package com.javaclasses.filesharingservice.services;

import com.javaclasses.filesharingservice.dao.entities.File;
import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;
import com.javaclasses.filesharingservice.services.customdatatypes.FileID;
import com.javaclasses.filesharingservice.services.customdatatypes.UserID;

import java.io.InputStream;
import java.util.Collection;

/**
 *  Public API for file storage service
 */
public interface FileStorageService {


    /**
     * Uploads file
     * @param key - access key to verify that user has necessary permission to upload the file
     * @param fileName - name of the file
     * @param inputStream - stream to be uploaded
     * @throws NoPermissionException if user does not have necessary permission to upload a file
     */
    void uploadFile(AccessKey key, String fileName, InputStream inputStream) throws NoPermissionException;


    /**
     * Looks for users files
     * @param key - access key to verify that user has necessary permission to browse files
     * @param userID - id of the user whose files are to be found
     * @return collection of users files
     * @throws NoPermissionException if user does not have necessary permission to browse a file
     */
    Collection<File> browseUsersFiles (AccessKey key, UserID userID) throws NoPermissionException;


    /**
     * Downloads file
     * @param key - access key to verify that user has necessary permission to browse files
     * @param fileID - id of the file to be downloaded
     * @return InputStream of the file content
     * @throws NoPermissionException if user does not have necessary permission to download a file
     */
    InputStream downloadFile(AccessKey key, FileID fileID) throws NoPermissionException;

    /**
     * Deletes a file
     * @param key - access key to verify that user has necessary permission to delete the file
     * @param fileID - id of the file to be deleted
     * @throws NoPermissionException if user does not have necessary permission to delete a file
     */
    void deleteFile(AccessKey key, FileID fileID) throws NoPermissionException;
}

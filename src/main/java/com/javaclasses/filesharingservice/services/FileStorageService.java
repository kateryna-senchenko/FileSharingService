package com.javaclasses.filesharingservice.services;

import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;

import java.io.InputStream;

/**
 *  Public API for file storage service
 */
public interface FileStorageService {


    /**
     * Uploads file
     * @param key - access key to verify that user has necessary permission to upload the file
     * @param fileContent - file to be uploaded
     * @throws NoPermissionException if user does not have necessary permission to upload a file
     */
    void uploadFile(AccessKey key, InputStream fileContent) throws NoPermissionException;
}

package com.javaclasses.filesharingservice.dao;

import com.javaclasses.filesharingservice.dao.entities.File;
import com.javaclasses.filesharingservice.services.NoPermissionException;
import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;

import java.io.InputStream;

/**
 * Interface of the service that manages File entities
 */
public interface FileRepository {


    void uploadFile(AccessKey key, String name, InputStream fileContent) throws NoPermissionException;


    File findFileByID(long id);
}

package com.javaclasses.filesharingservice.services.customdatatypes;

/**
 * Custom data type to hold an access key
 */
public class AccessKey {

    private final long accessKey;

    public AccessKey(long accessKey) {
        this.accessKey = accessKey;
    }

    public long getAccessKey() {
        return accessKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessKey accessKey1 = (AccessKey) o;

        return accessKey == accessKey1.accessKey;

    }

    @Override
    public int hashCode() {
        return (int) (accessKey ^ (accessKey >>> 32));
    }
}

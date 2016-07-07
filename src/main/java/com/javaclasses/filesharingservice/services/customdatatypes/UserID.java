package com.javaclasses.filesharingservice.services.customdatatypes;

/**
 * Custom data type to hold user id
 */
public class UserID {

    private final long id;

    public UserID(long id) {
        this.id = id;
    }

    public long getUserID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserID userID = (UserID) o;

        return id == userID.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}


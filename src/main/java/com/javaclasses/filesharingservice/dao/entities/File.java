package com.javaclasses.filesharingservice.dao.entities;


import com.javaclasses.filesharingservice.services.customdatatypes.FileID;

/**
 * Contains file info
 */
public class File {

    private FileID id;

    private String name;

    private User owner;


    public File(FileID id, String name, User owner){
        this.id = id;
        this.name = name;
        this.owner = owner;
    }


    public FileID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public void setId(FileID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        return id.equals(file.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

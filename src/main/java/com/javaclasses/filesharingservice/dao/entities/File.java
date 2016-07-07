package com.javaclasses.filesharingservice.dao.entities;



/**
 * Contains file info
 */
public class File {

    private long id;

    private String name;

    private User owner;


    public File(long id, String name, User owner){
        this.id = id;
        this.name = name;
        this.owner = owner;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public void setId(long id) {
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

        return id == file.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

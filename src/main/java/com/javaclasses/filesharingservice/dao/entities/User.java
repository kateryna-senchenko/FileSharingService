package com.javaclasses.filesharingservice.dao.entities;

import com.javaclasses.filesharingservice.services.customdatatypes.*;

import java.util.Collection;

/**
 * Contains user info
 */
public class User {

    private final UserID id;

    private Email email;

    private Password password;

    private FirstName firstName;

    private LastName lastName;

    private Collection<File> usersFiles;

    public User(UserID id, Email email, Password password, FirstName firstName, LastName lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserID getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }


    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setLastName(LastName lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(FirstName firstName) {
        this.firstName = firstName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
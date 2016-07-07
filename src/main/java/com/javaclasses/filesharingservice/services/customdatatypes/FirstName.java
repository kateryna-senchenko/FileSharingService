package com.javaclasses.filesharingservice.services.customdatatypes;

/**
 * Custom data type to hold a first name
 */
public class FirstName {

    private final String firstName;

    public FirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FirstName firstName1 = (FirstName) o;

        return firstName.equals(firstName1.firstName);

    }

    @Override
    public int hashCode() {
        return firstName.hashCode();
    }
}

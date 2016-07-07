package com.javaclasses.filesharingservice.services.customdatatypes;

/**
 * Custom data type to hold a last name
 */
public class LastName {

    private final String lastName;

    public LastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastName lastName1 = (LastName) o;

        return lastName.equals(lastName1.lastName);

    }

    @Override
    public int hashCode() {
        return lastName.hashCode();
    }
}

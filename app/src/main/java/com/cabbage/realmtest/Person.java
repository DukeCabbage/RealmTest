package com.cabbage.realmtest;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by lwang on 04/07/2016.
 */
public class Person extends RealmObject {

    private String firstName;
    private String lastName;

    private RealmList<Dog> pets;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public RealmList<Dog> getPets() { return pets; }
    public void setPets(RealmList<Dog> pets) { this.pets = pets; }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

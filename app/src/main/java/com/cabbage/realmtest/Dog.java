package com.cabbage.realmtest;

import io.realm.RealmObject;

/**
 * Created by lwang on 04/07/2016.
 */
public class Dog extends RealmObject {

    private String name;
    private int identification;
    private int age;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getIdentification() { return identification; }
    public void setIdentification(int identification) { this.identification = identification; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isAdult() { return age >= 2; }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", identification=" + identification +
                ", age=" + age +
                '}';
    }
}

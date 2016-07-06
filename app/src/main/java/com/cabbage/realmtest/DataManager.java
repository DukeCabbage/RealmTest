package com.cabbage.realmtest;


import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private Realm realmInstance;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(MyApp.getInstance()).build();
        Realm.setDefaultConfiguration(realmConfig);
        realmInstance = Realm.getDefaultInstance();
    }

    public void savePerson(Person... persons) {
        realmInstance.beginTransaction();
        for (Person person : persons) {
            realmInstance.insert(person);
        }
        realmInstance.commitTransaction();
    }
}

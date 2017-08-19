package com.example.ensardz.registrohuella.Datos;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by ensardz on 18/08/2017.
 */

public class RealmProvider {

    public static OrderedRealmCollection<Professor> getOrderedProfessors(Realm mRealm){
        return mRealm.where(Professor.class).findAllSorted(Professor.RAW_NAME_FIELD);
    }

    public static int getProfessorsCount(Realm mRealm){
        return mRealm.where(Professor.class).findAll().size();
    }

    public static void saveProfessorsToRealm(Realm mRealm, final List<Professor> professors){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Professor professor : professors){
                    realm.copyToRealmOrUpdate(professor);
                }
            }
        });
    }

}

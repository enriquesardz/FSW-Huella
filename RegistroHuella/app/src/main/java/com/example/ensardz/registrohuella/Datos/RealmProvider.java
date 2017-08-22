package com.example.ensardz.registrohuella.Datos;

import java.util.List;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by ensardz on 18/08/2017.
 */

public class RealmProvider {

    public static OrderedRealmCollection<Professor> getOrderedProfessors(Realm mRealm) {
        return mRealm.where(Professor.class)
                .isEmpty(Professor.FINGER_PRINT_FIELD)
                .findAllSorted(Professor.RAW_NAME_FIELD);
    }

    public static OrderedRealmCollection<Professor> getProfessorsByQuery(Realm mRealm, String query) {
        return mRealm.where(Professor.class)
                .contains(Professor.RAW_NAME_FIELD, query, Case.INSENSITIVE)
                .findAllSorted(Professor.RAW_NAME_FIELD);
    }

    public static Professor getProfessorByRawName(Realm mRealm, String rawName) {
        return mRealm.where(Professor.class)
                .equalTo(Professor.RAW_NAME_FIELD, rawName)
                .findFirst();
    }

    public static int getProfessorsCount(Realm mRealm) {
        return mRealm.where(Professor.class).findAll().size();
    }

    public static void saveProfessorsToRealm(Realm mRealm, final List<Professor> professors) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Professor professor : professors) {
                    realm.copyToRealmOrUpdate(professor);
                }
            }
        });
    }

}

package com.example.ensardz.registrohuella.Datos;

import android.util.Log;

import com.example.ensardz.registrohuella.RegistroActivity;

import java.util.List;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by ensardz on 18/08/2017.
 */

public class RealmProvider {

    public static final String TAG = RealmProvider.class.getSimpleName();


    public static OrderedRealmCollection<Professor> getOrderedProfessors(Realm mRealm) {
        return mRealm.where(Professor.class)
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
                    Professor prof = realm.copyToRealmOrUpdate(professor);

                    Log.d(TAG, prof.toString());
                }
            }
        });
    }

    public static void saveProfessorFingerprint(Realm mRealm, final Professor professor, final String fingerPrint){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                professor.setFingerPrint(fingerPrint);
            }
        });
    }

}

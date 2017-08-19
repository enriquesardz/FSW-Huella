package com.example.ensardz.registrohuella;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ensardz.registrohuella.API.APICallbackListener;
import com.example.ensardz.registrohuella.API.APIManager;
import com.example.ensardz.registrohuella.Datos.Professor;
import com.example.ensardz.registrohuella.Datos.RealmProvider;
import com.example.ensardz.registrohuella.UI.ProfessorRecyclerViewAdapter;
import com.example.ensardz.registrohuella.UI.RecyclerViewItemClickListener;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class ProfessorListActivity extends AppCompatActivity {

    private Context mContext;
    private Realm mRealm;

    private RecyclerView rvProfessors;
    private ProfessorRecyclerViewAdapter rvProfessorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_list);

        mContext = ProfessorListActivity.this;
        mRealm = Realm.getDefaultInstance();

        initComponentes();
    }

    public void initComponentes(){
        rvProfessors = (RecyclerView) findViewById(R.id.professor_recyclerview);

        if (RealmProvider.getProfessorsCount(mRealm) > 0){
            setRVProfessors();
        } else {
            //No data for Professor in the Realm table.... download ? ....
            APIManager.getInstance().downloadProfessors(new APICallbackListener<List<Professor>>() {
                @Override
                public void response(List<Professor> professors) {
                    RealmProvider.saveProfessorsToRealm(mRealm, professors);
                    setRVProfessors();
                }

                @Override
                public void failure() {

                }
            });
        }
    }

    public void setRVProfessors(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvProfessors.setHasFixedSize(true);
        rvProfessors.setLayoutManager(linearLayoutManager);

        OrderedRealmCollection<Professor> professorsData = RealmProvider.getOrderedProfessors(mRealm);

        rvProfessorsAdapter = new ProfessorRecyclerViewAdapter(mContext, professorsData, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Iniciar actividad de recoleccion
                Toast.makeText(mContext, "Test", Toast.LENGTH_SHORT).show();
            }
        });

        rvProfessors.setAdapter(rvProfessorsAdapter);
    }
}

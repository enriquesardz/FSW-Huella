package com.example.ensardz.registrohuella;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensardz.registrohuella.API.APICallbackListener;
import com.example.ensardz.registrohuella.API.APIManager;
import com.example.ensardz.registrohuella.Datos.Professor;
import com.example.ensardz.registrohuella.Datos.RealmProvider;
import com.example.ensardz.registrohuella.UI.ProfessorRecyclerViewAdapter;
import com.example.ensardz.registrohuella.UI.RecyclerViewItemClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

public class ProfessorListActivity extends AppCompatActivity {

    private Context mContext;
    private Realm mRealm;

    private RecyclerView rvProfessors;
    private ProfessorRecyclerViewAdapter rvProfessorsAdapter;
    private TextView tvSearch;

    private Button btnSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_list);

        mContext = ProfessorListActivity.this;
        mRealm = Realm.getDefaultInstance();

        initComponentes();
    }

    public void initComponentes() {

        rvProfessors = (RecyclerView) findViewById(R.id.professor_recyclerview);
        tvSearch = (TextView) findViewById(R.id.search_textview);
        btnSubir = (Button) findViewById(R.id.subir_button);

        setSearchListener();

        if (RealmProvider.getProfessorsCount(mRealm) > 0) {
            setRVProfessors(null);
        } else {
            //No data for Professor in the Realm table.... download ? ....
            APIManager.getInstance().downloadProfessors(new APICallbackListener<List<Professor>>() {
                @Override
                public void response(List<Professor> professors) {
                    RealmProvider.saveProfessorsToRealm(mRealm, professors);
                    setRVProfessors(null);
                }

                @Override
                public void failure() {

                }
            });
        }

        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Se esta subiendo.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setRVProfessors(OrderedRealmCollection<Professor> profData) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvProfessors.setHasFixedSize(true);
        rvProfessors.setLayoutManager(linearLayoutManager);

        OrderedRealmCollection<Professor> professorsData;

        if (profData == null) {
            professorsData = RealmProvider.getOrderedProfessors(mRealm);
        } else {
            professorsData = profData;
        }

        rvProfessorsAdapter = new ProfessorRecyclerViewAdapter(mContext, professorsData, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Iniciar actividad de recoleccion
                Intent intent = new Intent(mContext, RegistroActivity.class);
                intent.putExtra(Professor.RAW_NAME_KEY, rvProfessorsAdapter.getItem(position).getRawName());
                startActivity(intent);
            }
        });

        rvProfessors.setAdapter(rvProfessorsAdapter);

    }

    public void setSearchListener() {
        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                setRVProfessors(RealmProvider.getProfessorsByQuery(mRealm, charSequence.toString().toLowerCase()));
                rvProfessorsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}

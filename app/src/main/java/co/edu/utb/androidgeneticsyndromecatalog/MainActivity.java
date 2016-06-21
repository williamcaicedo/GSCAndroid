package co.edu.utb.androidgeneticsyndromecatalog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import co.edu.utb.androidgeneticsyndromecatalog.data.SyndromesAdapter;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Syndrome;

public class MainActivity extends AppCompatActivity {

    public final static String SYNDROME = "co.edu.utb.androidgeneticsyndromecatalog.SYNDROME";


    private RecyclerView syndromeReciclerView;
    private SyndromesAdapter sAdapter;

    private List<Syndrome> syndromeData = new ArrayList<Syndrome>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        syndromeReciclerView = (RecyclerView) findViewById(R.id.syndrome_recycler_view);
        sAdapter  = new SyndromesAdapter(syndromeData, new CustomListItemClickListener() {
            @Override
            public void onItemClick(View v, int syndromeId) {
                Syndrome s = new Syndrome();
                s.setId(syndromeId);
                s = syndromeData.get(syndromeData.indexOf(s));
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra(MainActivity.SYNDROME, s);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(getApplicationContext());
        syndromeReciclerView.setLayoutManager(sLayoutManager);
        syndromeReciclerView.setItemAnimator(new DefaultItemAnimator());
        syndromeReciclerView.setAdapter(sAdapter);

        getJsonData();

        TextView searchBox = (TextView)findViewById(R.id.search_box);
        searchBox.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void getJsonData() {

        String json = null;
        try {
            InputStream is = getAssets().open("syndromes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);

            JSONArray syndromes = obj.getJSONArray("syndromes");



            for (int i = 0; i < syndromes.length(); i++) {
                JSONObject s = syndromes.getJSONObject(i);
                JSONArray bibJson = s.getJSONArray("bibliography");
                String[] bib = new String[bibJson.length()];
                for (int j = 0; j < bibJson.length(); j++) {
                    bib[j] = bibJson.getString(j);
                }
                String retardation = "";
                try {
                    retardation = s.getBoolean("retardation")?"Sí":"No";
                }
                catch (JSONException je) {
                    retardation = "?";
                }
                String retardationNotes;
                try {
                    retardationNotes = s.getString("retardationNotes");
                }catch(JSONException je) {
                    retardationNotes = "";
                }


                syndromeData.add(new Syndrome(s.getInt("id"), s.getString("name"), s.getString("synonym"),
                        s.getString("inheritance"), retardation, retardationNotes, s.getString("evolution"),
                        s.getString("clinicalExams"), bib));
            }

            sAdapter.notifyDataSetChanged();


        } catch (IOException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }






    }

}

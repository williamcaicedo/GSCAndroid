package co.edu.utb.androidgeneticsyndromecatalog;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.edu.utb.androidgeneticsyndromecatalog.entity.Feature;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Syndrome;
import co.edu.utb.androidgeneticsyndromecatalog.fragment.FeatureSearchFragment;
import co.edu.utb.androidgeneticsyndromecatalog.fragment.NameSearchFragment;

public class MainActivity extends AppCompatActivity implements DetailActivityLauncher{

    public final static String SYNDROME = "co.edu.utb.androidgeneticsyndromecatalog.SYNDROME";



    private ListView drawerItemList;
    private String[] drawerOptions;
    private DrawerLayout drawerLayout;
    private CharSequence title;
    private CharSequence drawerTitle;
    private ActionBarDrawerToggle drawerToggle;

    public static List<Syndrome> syndromeData;
    public static List<Feature> featureData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getJsonData();

        title = drawerTitle = getTitle();
        drawerOptions = getResources().getStringArray(R.array.drawer_items);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerItemList = (ListView)findViewById(R.id.left_drawer);
        drawerItemList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, drawerOptions));

        drawerItemList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(drawerTitle);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(title);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        if (savedInstanceState == null) {
            selectDrawerItem(0);
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectDrawerItem(int position) {

        Bundle args = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = (position == 0) ?
                new FeatureSearchFragment()
                : new NameSearchFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        drawerItemList.setItemChecked(position, true);
        setTitle(drawerOptions[position]);
        drawerLayout.closeDrawer(drawerItemList);

    }


    @Override
    public void setTitle(CharSequence title) {
        //mTitle = title;
        getSupportActionBar().setTitle(title);
    }


    private Feature findFeatureById(int id) {
        int index = Collections.binarySearch(featureData, new Feature(id, ""), new Comparator<Feature>() {
            @Override
            public int compare(Feature lhs, Feature rhs) {
                if (lhs.getId() < rhs.getId()) return -1;
                if (lhs.getId() == rhs.getId()) return 0;
                return 1;
            }
        });
        if (index >= 0 && index < featureData.size())
            return featureData.get(index);
        return null;
    }

    private void getJsonData() {
        MainActivity.syndromeData  = new ArrayList<>();
        MainActivity.featureData = new ArrayList<>();
        String json = null;
        try {

            InputStream is = getAssets().open("features.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(json);

            JSONArray features = obj.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject f = features.getJSONObject(i);
                Feature feature = new Feature(f.getInt("id"), f.getString("name"));
                featureData.add(feature);

            }

            is = getAssets().open("syndromes.json");
            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            obj = new JSONObject(json);

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
                List<Feature> syndromeFeatures = new ArrayList<>();
                JSONArray featIds = s.getJSONArray("features");
                for (int j = 0; j < featIds.length(); j++) {
                    syndromeFeatures.add(findFeatureById(featIds.getInt(j)));
                }

                syndromeData.add(new Syndrome(s.getInt("id"), s.getString("name"), s.getString("synonym"),
                        s.getString("site"), s.getString("gene/locus"), s.getString("genetic_anomaly"),
                        s.getString("inheritance"), retardation, retardationNotes, s.getString("evolution"),
                        s.getString("clinicalExams"), s.getString("omim"), bib, syndromeFeatures));
            }

            Collections.sort(syndromeData, new Comparator<Syndrome>() {
                @Override
                public int compare(Syndrome s1, Syndrome s2) {
                    return s1.getName().compareTo(s2.getName());
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void launchActivity(Syndrome s) {
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra(MainActivity.SYNDROME, s);
        startActivity(intent);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selectDrawerItem(position);

        }
    }

}

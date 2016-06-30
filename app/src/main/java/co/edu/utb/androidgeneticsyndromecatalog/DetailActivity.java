package co.edu.utb.androidgeneticsyndromecatalog;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.edu.utb.androidgeneticsyndromecatalog.entity.Feature;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Syndrome;
import co.edu.utb.androidgeneticsyndromecatalog.util.BitmapUtils;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        Syndrome s = bundle.getParcelable(MainActivity.SYNDROME);

        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText(s.getName());

        TextView synonym = (TextView)findViewById(R.id.synonymTextView);
        synonym.setText(s.getSynonym());

        Resources resources = getResources();
        int imageId = resources.getIdentifier("img_"+s.getId(), "drawable", this.getPackageName());

        ImageView illustration = (ImageView) findViewById(R.id.illustrationImageView);
        illustration.setImageBitmap(
                BitmapUtils.decodeSampledBitmapFromResource(getResources(), imageId, 250, 150));

        TextView evolution = (TextView)findViewById(R.id.evolutionTextView);
        evolution.setText(s.getEvolution());

        TextView inheritance = (TextView)findViewById(R.id.inheritanceTextView);
        inheritance.setText(s.getInheritance());

        TextView retardation = (TextView)findViewById(R.id.retardationTextView);
        String notesText =  !"".equals(s.getRetardationNotes())?" ("+s.getRetardationNotes()+")":"";
        retardation.setText(s.getRetardation() + notesText);

        TextView clinicalExams = (TextView)findViewById(R.id.examsTextView);
        clinicalExams.setText(s.getClinicalExams());

        LinearLayout bib = (LinearLayout) findViewById(R.id.bibliography);
        for(String b : s.getBibliography()) {
            TextView entry = new TextView(this);
            entry.setPadding(0,0,0,5);
            entry.setTextColor(ContextCompat.getColor(this, R.color.name));
            entry.setText(b);
            bib.addView(entry);
        }

        LinearLayout feats = (LinearLayout) findViewById(R.id.features);
        for(Feature f : s.getFeatures()) {
            TextView entry = new TextView(this);
            entry.setPadding(0,0,0,5);
            entry.setTextColor(ContextCompat.getColor(this, R.color.name));
            entry.setText(f.getName());
            feats.addView(entry);
        }

    }



}

package co.edu.utb.androidgeneticsyndromecatalog.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import co.edu.utb.androidgeneticsyndromecatalog.DetailActivityLauncher;
import co.edu.utb.androidgeneticsyndromecatalog.FeaturesCompletionView;
import co.edu.utb.androidgeneticsyndromecatalog.MainActivity;
import co.edu.utb.androidgeneticsyndromecatalog.R;
import co.edu.utb.androidgeneticsyndromecatalog.data.FeatureAdapter;
import co.edu.utb.androidgeneticsyndromecatalog.data.SyndromesAdapter;
import co.edu.utb.androidgeneticsyndromecatalog.data.filter.SyndromeFeatureFilter;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Feature;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeatureSearchFragment extends AbstractFragment {



    private DetailActivityLauncher launcher;

    public FeatureSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feature_search, container, false);



    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setUpRecyclerView(R.id.feature_search_syndrome_recycler_view, new SyndromeFeatureFilter());
        setUpFeatureSearchBox();

    }

    private void setUpFeatureSearchBox() {
        /*final MultiAutoCompleteTextView featureSearchBox =
                (MultiAutoCompleteTextView)getActivity().findViewById(R.id.feature_search_box);
        featureSearchBox.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        ArrayAdapter<Feature> adapter =
                new FeatureAdapter(getActivity(), R.layout.feature_list_row, MainActivity.featureData);
        featureSearchBox.setAdapter(adapter);

        featureSearchBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sAdapter.getFilter().filter(featureSearchBox.getText());
                //Feature f = (Feature)parent.getItemAtPosition(position);
                //Toast.makeText(getActivity(), featureSearchBox.getText(), Toast.LENGTH_SHORT).show();
            }
        });*/

        FilteredArrayAdapter<Feature> adapter =
                new FilteredArrayAdapter<Feature>(getActivity(), R.layout.feature_list_row, MainActivity.featureData) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(R.layout.feature_list_row, parent, false);
                        }
                        TextView featureName = (TextView)convertView.findViewById(R.id.feature_row);
                        Feature f = getItem (position);
                        featureName.setText(f.getId() + ". " + f.getName());
                        return convertView;
                    }

                    @Override
                    protected boolean keepObject(Feature f, String mask) {
                        return f.getName().toLowerCase().contains(mask.toLowerCase());
                    }
                };
        final FeaturesCompletionView searchView = (FeaturesCompletionView)getActivity().findViewById(R.id.feature_search_box);
        searchView.setAdapter(adapter);
        searchView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        searchView.setTokenListener(new TokenCompleteTextView.TokenListener<Feature>() {

            private void invokeFilter() {
                if (searchView.getObjects().size() > 0) {
                    StringBuilder filter = new StringBuilder();
                    for (Feature f : searchView.getObjects()) {
                        filter.append(f.toString());
                        filter.append(";");
                    }
                    String filterString = filter.toString();
                    sAdapter.getFilter().filter(filter.substring(0, filter.length() - 1));
                } else  {
                    sAdapter.getFilter().filter("");
                }
            }
            @Override
            public void onTokenAdded(Feature token) {
                 invokeFilter();
            }

            @Override
            public void onTokenRemoved(Feature token) {
                invokeFilter();
            }
        });



    }

}

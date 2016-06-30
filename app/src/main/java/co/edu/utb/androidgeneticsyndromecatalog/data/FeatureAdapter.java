package co.edu.utb.androidgeneticsyndromecatalog.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.utb.androidgeneticsyndromecatalog.R;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Feature;

/**
 * Created by william on 22/06/16.
 */
public class FeatureAdapter extends ArrayAdapter<Feature>{

    private List<Feature> allFeatures;
    private Filter featureFilter;

    public FeatureAdapter(Context context, int resource, List<Feature> objects) {
        super(context, resource, objects);
        allFeatures = new ArrayList<>();
        allFeatures.addAll(objects);
        featureFilter = new FeatureAdapter.CustomFilter(this);
    }

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
    public Filter getFilter() {
        return this.featureFilter;
    }

    private class CustomFilter extends Filter {

        private final FeatureAdapter fAdapter;

        public CustomFilter(FeatureAdapter fAdapter) {
            super();
            this.fAdapter = fAdapter;
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Feature f = (Feature)resultValue;
            return f.getId() + ". " + f.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Feature> resultList = new ArrayList<>();

            //clear();
            String filterPattern = (constraint == null)? null : constraint.toString()
                    .toLowerCase().trim();
            if (filterPattern == null || filterPattern.length() == 0) {
                resultList.addAll(allFeatures);
            } else {
                for (Feature f : allFeatures) {
                    if (f.getName().toLowerCase().contains(filterPattern)) {
                        resultList.add(f);
                    }
                }

            }
            //addAll(resultList);
            FilterResults filterResults = new FilterResults();

            filterResults.values = resultList;
            filterResults.count = resultList.size();


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List<Feature>)results.values);
            this.fAdapter.notifyDataSetChanged();
        }
    }
}

package co.edu.utb.androidgeneticsyndromecatalog.data.filter;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.edu.utb.androidgeneticsyndromecatalog.data.SyndromesAdapter;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Feature;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Syndrome;

/**
 * Created by william on 28/06/16.
 */
public class SyndromeFeatureFilter extends CustomFilter {
    private SyndromesAdapter sAdapter;

    public SyndromeFeatureFilter() {
        super();
    }

    public SyndromesAdapter getAdapter() {
        return sAdapter;
    }

    public void setAdapter(SyndromesAdapter sAdapter) {
        this.sAdapter = sAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        List<Syndrome> resultList = new ArrayList<>();

        //filterPattern = filterPattern.subList(1,filterPattern.size());
        String filterString = constraint.toString().toLowerCase().trim();

        sAdapter.setSyndromeList(new ArrayList<Syndrome>());
        if (filterString.length() == 0) {
            resultList.addAll(sAdapter.getAllSyndromes());
        } else {
            List<String> filterPatterns = Arrays.asList(filterString.split(";"));
            List<Feature> requestedFeatures = new ArrayList<>();
            for (String fs : filterPatterns) {
                String[] parts = fs.split("\\. ");
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1];
                requestedFeatures.add(new Feature(id, name));
            }
            for (Syndrome s : sAdapter.getAllSyndromes()) {
                if (s.getFeatures().containsAll(requestedFeatures)) {
                    resultList.add(s);
                }
            }
        }
        sAdapter.getSyndromeList().addAll(resultList);
        FilterResults filterResults = new FilterResults();

        filterResults.values = resultList;
        filterResults.count = resultList.size();


        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        sAdapter.notifyDataSetChanged();
    }
}

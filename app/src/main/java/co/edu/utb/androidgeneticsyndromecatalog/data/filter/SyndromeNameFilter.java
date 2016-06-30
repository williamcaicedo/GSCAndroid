package co.edu.utb.androidgeneticsyndromecatalog.data.filter;

import java.util.ArrayList;
import java.util.List;

import co.edu.utb.androidgeneticsyndromecatalog.data.SyndromesAdapter;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Syndrome;

/**
 * Created by william on 28/06/16.
 */
public class SyndromeNameFilter extends CustomFilter {

    private SyndromesAdapter sAdapter;

    public SyndromeNameFilter() {
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
        String filterPattern = constraint.toString().toLowerCase().trim();

        sAdapter.setSyndromeList(new ArrayList<Syndrome>());
        if (filterPattern.length() == 0) {
            resultList.addAll(sAdapter.getAllSyndromes());
        } else {
            for (Syndrome s : sAdapter.getAllSyndromes()) {
                if (s.getName().toLowerCase().contains(filterPattern)) {
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

package co.edu.utb.androidgeneticsyndromecatalog.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.utb.androidgeneticsyndromecatalog.CustomListItemClickListener;
import co.edu.utb.androidgeneticsyndromecatalog.R;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Syndrome;

/**
 * Created by william on 14/06/16.
 */
public class SyndromesAdapter extends RecyclerView.Adapter<SyndromesAdapter.MyViewHolder> implements Filterable {

    private final CustomListItemClickListener clickListener;
    private List<Syndrome> allSyndromes;
    private List<Syndrome> syndromeList;
    private Filter sFilter;


    public SyndromesAdapter(List<Syndrome> syndromeList, CustomListItemClickListener clickListener) {
        this.allSyndromes = syndromeList;
        this.syndromeList = syndromeList;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.syndrome_list_row, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v, viewHolder.syndromeId);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Syndrome syndrome = syndromeList.get(position);
        holder.syndromeId = syndrome.getId();
        holder.name.setText(syndrome.getName());
        holder.synonym.setText(syndrome.getSynonym());
    }

    @Override
    public int getItemCount() {
        return syndromeList.size();
    }

    @Override
    public Filter getFilter() {
        return sFilter;
    }

    public void setFilter(Filter filter) { sFilter = filter;}

    public List<Syndrome> getSyndromeList() {
        return syndromeList;
    }

    public void setSyndromeList(List<Syndrome> syndromeList) {
        this.syndromeList = syndromeList;
    }

    public List<Syndrome> getAllSyndromes() {
        return allSyndromes;
    }

    public void setAllSyndromes(List<Syndrome> allSyndromes) {
        this.allSyndromes = allSyndromes;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public int syndromeId;
        public TextView name;
        public TextView synonym;
        public MyViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.name);
            this.synonym = (TextView) view.findViewById(R.id.synonym);
        }
    }

    private class CustomFilter extends Filter {

        private final SyndromesAdapter sAdapter;

        public CustomFilter(SyndromesAdapter sAdapter) {
            super();
            this.sAdapter = sAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Syndrome> resultList = new ArrayList<>();
            String filterPattern = constraint.toString().toLowerCase().trim();

            syndromeList = new ArrayList<>();
            if (filterPattern.length() == 0) {
                resultList.addAll(allSyndromes);
            } else {
                for (Syndrome s : allSyndromes) {
                    if (s.getName().toLowerCase().contains(filterPattern)) {
                        resultList.add(s);
                    }
                }

            }
            syndromeList.addAll(resultList);
            FilterResults filterResults = new FilterResults();

            filterResults.values = resultList;
            filterResults.count = resultList.size();


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.sAdapter.notifyDataSetChanged();
        }
    }
}

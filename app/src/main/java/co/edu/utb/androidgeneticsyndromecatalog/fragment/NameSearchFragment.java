package co.edu.utb.androidgeneticsyndromecatalog.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.edu.utb.androidgeneticsyndromecatalog.R;
import co.edu.utb.androidgeneticsyndromecatalog.data.filter.SyndromeNameFilter;


public class NameSearchFragment extends AbstractFragment {





    public NameSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_search, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setUpRecyclerView(R.id.syndrome_recycler_view, new SyndromeNameFilter());
        setUpSearchBox();

    }

   /* private void setUpRecyclerView() {
        syndromeRecyclerView = (RecyclerView) getActivity().findViewById(R.id.syndrome_recycler_view);
        sAdapter  = new SyndromesAdapter(MainActivity.syndromeData, new CustomListItemClickListener() {
            @Override
            public void onItemClick(View v, int syndromeId) {
                Syndrome s = new Syndrome();
                s.setId(syndromeId);
                s = MainActivity.syndromeData.get(MainActivity.syndromeData.indexOf(s));
                launcher.launchActivity(s);

            }
        });
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        syndromeRecyclerView.setLayoutManager(sLayoutManager);
        syndromeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        syndromeRecyclerView.setAdapter(sAdapter);
    }*/

    private void setUpSearchBox() {
        TextView searchBox = (TextView)getActivity().findViewById(R.id.search_box);
        searchBox.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}

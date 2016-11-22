package co.edu.utb.androidgeneticsyndromecatalog.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import co.edu.utb.androidgeneticsyndromecatalog.R;
import co.edu.utb.androidgeneticsyndromecatalog.CustomListItemClickListener;
import co.edu.utb.androidgeneticsyndromecatalog.DetailActivityLauncher;
import co.edu.utb.androidgeneticsyndromecatalog.MainActivity;
import co.edu.utb.androidgeneticsyndromecatalog.data.SyndromesAdapter;
import co.edu.utb.androidgeneticsyndromecatalog.data.filter.CustomFilter;
import co.edu.utb.androidgeneticsyndromecatalog.entity.Syndrome;

/**
 * Created by william on 27/06/16.
 */
public abstract class AbstractFragment extends Fragment{

    protected SyndromesAdapter sAdapter;
    protected RecyclerView syndromeRecyclerView;
    protected DetailActivityLauncher launcher;

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bindLauncher(activity);
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bindLauncher(context);
    }

    private void bindLauncher(Context context) {
        launcher = (DetailActivityLauncher)context;
    }

    protected RecyclerView setUpRecyclerView(int id, int scrollerId, CustomFilter filter) {
        syndromeRecyclerView = (RecyclerView) getActivity().findViewById(id);
        FastScroller fastScroller = (FastScroller) getActivity().findViewById(scrollerId);
        sAdapter  = new SyndromesAdapter(MainActivity.syndromeData, new CustomListItemClickListener() {
            @Override
            public void onItemClick(View v, int syndromeId) {
                Syndrome s = new Syndrome();
                s.setId(syndromeId);
                s = MainActivity.syndromeData.get(MainActivity.syndromeData.indexOf(s));
                launcher.launchActivity(s);

            }
        });
        filter.setAdapter(sAdapter);
        sAdapter.setFilter(filter);
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        syndromeRecyclerView.setLayoutManager(sLayoutManager);
        syndromeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        syndromeRecyclerView.setAdapter(sAdapter);
        fastScroller.setRecyclerView(syndromeRecyclerView);
        return syndromeRecyclerView;
    }
}

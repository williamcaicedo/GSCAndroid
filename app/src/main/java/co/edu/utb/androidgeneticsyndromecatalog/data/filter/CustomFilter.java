package co.edu.utb.androidgeneticsyndromecatalog.data.filter;

import android.widget.Filter;

import co.edu.utb.androidgeneticsyndromecatalog.data.SyndromesAdapter;

/**
 * Created by william on 28/06/16.
 */
public abstract class CustomFilter extends Filter {


    public abstract SyndromesAdapter getAdapter();

    public abstract void setAdapter(SyndromesAdapter sAdapter);

}

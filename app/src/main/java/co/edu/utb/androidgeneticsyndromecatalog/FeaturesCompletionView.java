package co.edu.utb.androidgeneticsyndromecatalog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import co.edu.utb.androidgeneticsyndromecatalog.entity.Feature;

/**
 * Created by william on 29/06/16.
 */
public class FeaturesCompletionView extends TokenCompleteTextView<Feature> {

    public FeaturesCompletionView(Context context) {
        super(context);
    }

    public FeaturesCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeaturesCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(Feature f) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.feature_token, (ViewGroup) this.getParent(), false);

        TextView featureName = (TextView)view.findViewById(R.id.feature_token);
        featureName.setText(f.getId() + ". " + f.getName());
        return featureName;
    }

    @Override
    protected Feature defaultObject(String completionText) {
        return new Feature(0, completionText);
    }
}

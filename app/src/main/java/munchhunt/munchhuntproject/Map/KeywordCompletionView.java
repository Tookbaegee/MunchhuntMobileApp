package munchhunt.munchhuntproject.Map;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import org.w3c.dom.Text;

import munchhunt.munchhuntproject.R;

public class KeywordCompletionView extends TokenCompleteTextView<String> {
    public KeywordCompletionView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    @Override
    protected View getViewForObject(String keyword){

        LayoutInflater lf = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) lf.inflate(R.layout.maplist_autocompletetag_token, (ViewGroup) getParent(), false);
        view.setText(keyword);

        return view;
    }

    @Override
    protected String defaultObject(String completionText){
        return completionText;
    }
}

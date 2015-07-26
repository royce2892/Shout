package com.hack.start.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.TelecomManager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hack.start.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by RRaju on 7/22/2015.
 */
public class HomeFragment extends Fragment {

    private TextView mSummary;
    private String location;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String location) {
        final HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("location",location);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        location = getArguments().getString("location");
        mSummary =(TextView) view.findViewById(R.id.summary);
        mSummary.setMovementMethod(new ScrollingMovementMethod());
        getSummary(location);
    }

    private void getSummary(String location) {

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait");
        dialog.setTitle("Loading");
        dialog.setCancelable(false);
        dialog.show();

        ParseQuery query = ParseQuery.getQuery("location");
        query.whereEqualTo("Name", location.toLowerCase());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null)
                    mSummary.setText(parseObject.getString("wiki"));
                dialog.cancel();
            }

        });
    }

}

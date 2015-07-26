package com.hack.start.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hack.start.R;
import com.hack.start.adapter.SafetyAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Rraju on 7/25/2015.
 */
public class SafetyFragment extends Fragment {

    private ListView mSafeList;
    private String location;

    public SafetyFragment() {
    }

    public static SafetyFragment newInstance(String location) {
        final SafetyFragment safetyFragment = new SafetyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("location", location);
        safetyFragment.setArguments(bundle);
        return safetyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        location = getArguments().getString("location");
        mSafeList = (ListView) view.findViewById(R.id.list_safe);
        getSafety(location);
    }

    private void getSafety(String location) {

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
                if (e == null) {
                    SafetyAdapter adapter = new SafetyAdapter(parseObject.getString("safety"),getActivity());
                    mSafeList.setAdapter(adapter);
                }
                dialog.cancel();
            }

        });
    }
}

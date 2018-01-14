package com.example.praiyon.sentimentanalysis;

/**
 * Created by praiyon on 10/01/18.
 */

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class OneFragment extends Fragment{
    TextView textView;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Log.d("test",strtext);

    }

    @Override
    @TargetApi(24)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        FragmentActivity activity = (FragmentActivity) getActivity();
        TextView text = view.findViewById(R.id.text);
        ListView listView = view.findViewById(R.id.listView);
        List<Sentiment> firstList = activity.getFirstList();
        List<String> tweets = firstList.stream()
                .map(Sentiment::getTweet)
                .collect(Collectors.toList());
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                tweets);
        text.setText(activity.getFirstMessage());
        listView.setAdapter(listViewAdapter);

        return view;
        // Inflate the layout for this fragment
//
    }

}

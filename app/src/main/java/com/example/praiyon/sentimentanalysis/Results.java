package com.example.praiyon.sentimentanalysis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.List;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        String firstTopic = getIntent().getStringExtra("firstTopic");
        String secondTopic = getIntent().getStringExtra("secondTopic");
        String payLoadOne =  getIntent().getStringExtra("payLoadOne");
        String payLoadTwo =  getIntent().getStringExtra("payLoadTwo");
        SentimentFactory sentimentFactory = new SentimentFactory(payLoadOne);
        try {
            List<Sentiment> firstList = sentimentFactory
                    .convert(firstTopic);
            sentimentFactory.setResult(payLoadTwo);
            List<Sentiment> secondList = sentimentFactory
                    .convert(secondTopic);

            TextView firstView = findViewById(R.id.firstTopicResult);
            TextView secondView = findViewById(R.id.secondTopicResult);
            TextView winner = findViewById(R.id.winner);
            String firstResult = sentimentFactory.average(firstList);
            String secondResult = sentimentFactory.average(secondList);
            firstView.setText(firstTopic+": " +firstResult);
            secondView.setText(secondTopic+": " +secondResult);
            winner.setText((Double.valueOf(firstResult) >
                    Double.valueOf(secondResult) ? firstTopic : secondTopic) + " is more favoured" );
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}

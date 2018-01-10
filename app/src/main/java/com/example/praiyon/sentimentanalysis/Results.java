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
        SentimentConverter sentimentConverter = new SentimentConverter(payLoadOne);
        SentimentConverter sentimentConverter1 = new SentimentConverter(payLoadTwo);
        try {
            List<Sentiment> firstList = sentimentConverter
                    .convert(firstTopic);
            List<Sentiment> secondList = sentimentConverter1
                    .convert(secondTopic);

            TextView firstView = findViewById(R.id.firstTopicResult);
            TextView secondView = findViewById(R.id.secondTopicResult);
            TextView winner = findViewById(R.id.winner);
            String firstResult = average(firstList);
            String secondResult = average(secondList);
            firstView.setText(firstTopic+": " +firstResult);
            secondView.setText(secondTopic+": " +secondResult);
            winner.setText("Winner is " + (Double.valueOf(firstResult) >
                    Double.valueOf(secondResult) ? firstTopic : secondTopic) );
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String average(List<Sentiment> sentimentList){
        Double total = 0.0;
        for (Sentiment element : sentimentList){
            total += element.getPolarity();
        }
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern( "#,#0.00#");
        return decimalFormat.format(total/sentimentList.size());
    }
}

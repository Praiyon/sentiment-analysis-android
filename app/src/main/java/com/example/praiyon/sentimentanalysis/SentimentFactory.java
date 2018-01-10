package com.example.praiyon.sentimentanalysis;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praiyon on 09/01/18.
 */

public class SentimentFactory {
    private String result;


    SentimentFactory(String result){
        this.result = result;
    }
    List<Sentiment> convert(String topic) throws JSONException {
        JSONObject object = new JSONObject(this.result)
                .getJSONObject("data")
                .getJSONObject(topic);
        return toSentimentList(object);
    }
    private List<Sentiment> toSentimentList(JSONObject obj) throws JSONException {

        List<Sentiment> sentimentList = new ArrayList<>();
        for(int i = 0; i<obj.length(); i++){
            sentimentList.add(toSentimentObject(obj.getJSONObject(Integer.toString(i))));
        }
        return sentimentList;
    }

    public Sentiment toSentimentObject(JSONObject obj) throws JSONException {
        Sentiment sentiment = new Sentiment();
        sentiment.setPolarity(obj.getDouble("polarity"));
        sentiment.setSubjectivity(obj.getDouble("subjectivity"));
        sentiment.setTweet(obj.getString("tweet"));
        System.out.print(sentiment);
        return sentiment;
    }

    public boolean topicExists(String topic) throws JSONException {
            JSONObject object = new JSONObject(this.result)
                    .getJSONObject("data")
                    .getJSONObject(topic);
            int x = object.length();
            return (object.length() > 0 && object.length()<=15);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}

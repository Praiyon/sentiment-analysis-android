package com.example.praiyon.sentimentanalysis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static String firstTopicText = "";
    static String secondTopicText = "";
    static int counter = 0;
    static String payload;
    static String payLoadOne = "";
    static String payLoadTwo = "";
    SharedPreferences sharedPreferences;
    RequestQueue queue;
    static Map<String,Object> maps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText firstTopic = findViewById(R.id.topicOne);
        final EditText secondTopic = findViewById(R.id.topicTwo);


        final Button CREATE = findViewById(R.id.create);

        CREATE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    firstTopicText = firstTopic.getText().toString();
                    secondTopicText = secondTopic.getText().toString();
                    restCall(firstTopic.getText().toString());
                    restCall(secondTopic.getText().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Code here executes on main thread after user presses button
            }
        });
    }
    public void restCall(final String topic) throws JSONException {

        String url = "https://senttwianalsis.herokuapp.com/sentiment";
        getResponse(Request.Method.POST, url, topic,
                new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        counter++;
                        if(counter == 1){
                            payLoadOne = result;
                        }
                        if(counter == 2){
                            counter = 0;
                            payLoadTwo = result;
                            initiateIntent();
                        }
                    }
                });
    }

    public void getResponse(final int method, final String url, final String topic, final VolleyCallback callback) {

        queue = MySingleton.getInstance(MainActivity.this).getRequestQueue();

        StringRequest strreq = new StringRequest(method, url, new Response.Listener < String > () {

            @Override
            public void onResponse(String Response) {
                try{
                    SentimentFactory sentimentFactory = new SentimentFactory(Response);
                    boolean exists = sentimentFactory.topicExists(topic);
                    if (!exists){
                        try {
                            Thread.sleep(5000);
                            restCall(topic);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        callback.onSuccessResponse(Response);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                try {
                    restCall(topic);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
//                e.printStackTrace();
//                Toast.makeText(MainActivity.this, e + "error", Toast.LENGTH_LONG).show();
            }
        })
        {
            // set headers
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("sentiment", topic);
                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(strreq);
    }

    public void initiateIntent(){
        Intent intent = new Intent(getBaseContext(), Results.class);
        intent.putExtra("payLoadOne", payLoadOne);
        intent.putExtra("payLoadTwo", payLoadTwo);
        intent.putExtra("firstTopic", firstTopicText);
        intent.putExtra("secondTopic", secondTopicText);
        startActivity(intent);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}

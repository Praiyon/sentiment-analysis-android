package com.example.praiyon.sentimentanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;




public class FragmentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String firstMessage;
    private String secondMessage;
    private List<Sentiment> firstList;
    private List<Sentiment> secondList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        try {
            getResults();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getResults() throws JSONException {
        String firstTopic = getIntent().getStringExtra("firstTopic");
        String secondTopic = getIntent().getStringExtra("secondTopic");
        String payLoadOne =  getIntent().getStringExtra("payLoadOne");
        String payLoadTwo =  getIntent().getStringExtra("payLoadTwo");
        SentimentFactory sentimentFactory = new SentimentFactory(payLoadOne);


        //set Fragmentclass Arguments

        this.firstList = sentimentFactory
                .convert(firstTopic);
        sentimentFactory.setResult(payLoadTwo);
        this.secondList = sentimentFactory
                .convert(secondTopic);

       this.firstMessage = sentimentFactory.average(firstList);
       this.secondMessage = sentimentFactory.average(secondList);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            return true;
        }
        return false;
    }

    public String getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(String firstMessage) {
        this.firstMessage = firstMessage;
    }

    public String getSecondMessage() {
        return secondMessage;
    }

    public void setSecondMessage(String secondMessage) {
        this.secondMessage = secondMessage;
    }

    public List<Sentiment> getFirstList() {
        return firstList;
    }

    public void setFirstList(List<Sentiment> firstList) {
        this.firstList = firstList;
    }

    public List<Sentiment> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<Sentiment> secondList) {
        this.secondList = secondList;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), MainActivity.firstTopicText);
        adapter.addFragment(new TwoFragment(), MainActivity.secondTopicText);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
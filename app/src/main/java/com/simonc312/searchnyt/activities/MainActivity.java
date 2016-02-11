package com.simonc312.searchnyt.activities;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.simonc312.searchnyt.fragments.TrendingFragment;
import com.simonc312.searchnyt.R;


import butterknife.Bind;

import butterknife.BindString;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements TrendingFragment.InteractionListener{
    @BindString(R.string.action_back_pressed)String ACTION_BACK_PRESSED;
    @BindString(R.string.app_name_title) String APP_NAME_TITLE;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private LayoutChangeBroadcastReciever broadcastReciever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupSupportActionBar();
        setTitle();
        broadcastReciever = new LayoutChangeBroadcastReciever();
        swapFragment(TrendingFragment.newInstance(true, null,TrendingFragment.TRENDING_TYPE));

    }

    private void setupSupportActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setTitle(){
        TextView tx = (TextView)findViewById(R.id.app_name);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/EnglishTowne.ttf");
        tx.setTypeface(custom_font);
        tx.setText(APP_NAME_TITLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("ACTION_LAYOUT_CHANGE");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReciever, intentFilter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReciever);
    }

    @Override
    public void onBackPressed(){
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_BACK_PRESSED));
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_search:
                startActivity(new Intent(this, SearchQueryActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void swapFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLayoutChange(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    @Override
    public void onBackPress(boolean pop){
        if(pop){
            finish();
        }
    }

    private class LayoutChangeBroadcastReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
          //inflate fragment but pass adapter data to fragment
            Toast.makeText(MainActivity.this, "intent received", Toast.LENGTH_SHORT).show();

        }
    }
}

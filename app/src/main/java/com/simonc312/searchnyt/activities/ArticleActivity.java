package com.simonc312.searchnyt.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.helpers.ImageLoaderHelper;
import com.simonc312.searchnyt.models.Article;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * For displaying individual articles
 * Created by Simon on 2/20/2016.
 */
public class ArticleActivity extends AppCompatActivity{
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_background)
    ImageView background;
    @Bind(R.id.tv_headline)
    TextView tv_headline;
    @Bind(R.id.tv_byline)
    TextView tv_byline;
    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.tv_body)
    TextView tv_body;
    @Bind(R.id.tv_url)
    TextView tv_url;
    //used for custom tabs
    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        handleIntent(getIntent());
        setUpActionBar(toolbar);
    }

    private void setUpActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(article.getSection());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_as_up_selector);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int threshold = 60;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() - threshold){
                    appBarLayout.setActivated(true);
                } else appBarLayout.setActivated(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // not using share menu for now
        //getMenuInflater().inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                startShareIntent();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.share_fab)
    public void startShareIntent() {
        String shareString = String.format("%s\n%s\n%s",
                article.getTitle(),
                article.getUrl(),
                getString(R.string.shared_with_app));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, article.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, shareString);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.share_chooser_title)));
    }

    private void handleIntent(Intent intent){
        if(intent != null){
            article = intent.getParcelableExtra("article");
            String backgroundUrl = intent.getStringExtra("background");
            loadBackgroundImage(backgroundUrl);
            tv_headline.setText(article.getDisplayTitle());
            tv_byline.setText(article.getByline());
            tv_date.setText(article.getRelativeTimePosted());
            tv_body.setText(article.getSummary());
            linkifyUrl(tv_url,article.getUrl());
        }

    }

    private void loadBackgroundImage(String url) {
        if(url == null)
            return;
        ImageLoaderHelper.loadWithPlaceholder(
                this,
                url,
                background,
                R.drawable.image_placeholder_jumbo);
    }

    private void linkifyUrl(TextView textView, String url){
        final String actualUrl = url;

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.enableUrlBarHiding();
        builder.addDefaultShareMenuItem();
        builder.setShowTitle(true);
        builder.setToolbarColor(colorPrimary);
        final CustomTabsIntent intent = builder.build();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.launchUrl(ArticleActivity.this, Uri.parse(actualUrl));
            }
        });
    }
}

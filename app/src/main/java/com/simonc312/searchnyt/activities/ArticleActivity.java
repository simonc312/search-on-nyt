package com.simonc312.searchnyt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.helpers.ImageLoaderHelper;
import com.simonc312.searchnyt.models.Article;
import com.simonc312.searchnyt.models.MediaMetaData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * For displaying individual articles
 * Created by Simon on 2/20/2016.
 */
public class ArticleActivity extends AppCompatActivity{

    @Bind(R.id.iv_background)
    ImageView background;
    @Bind(R.id.tv_section)
    TextView tv_section;
    @Bind(R.id.tv_headline)
    TextView tv_headline;
    @Bind(R.id.tv_byline)
    TextView tv_byline;
    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.tv_body)
    TextView tv_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent){
        if(intent != null){
            Article article = intent.getParcelableExtra("article");
            loadBackgroundImage(article.getJumboMetaData());
            tv_section.setText(article.getSection());
            tv_headline.setText(article.getDisplayTitle());
            tv_byline.setText(article.getByline());
            tv_date.setText(article.getRelativeTimePosted());
            tv_body.setText(article.getSummary());
        }

    }

    private void loadBackgroundImage(MediaMetaData jumboMetaData) {
        if(jumboMetaData == null)
            return;
        ImageLoaderHelper.loadWithPlaceholder(
                this,
                jumboMetaData.getUrl(),
                background,
                R.drawable.image_placeholder_jumbo);
    }
}

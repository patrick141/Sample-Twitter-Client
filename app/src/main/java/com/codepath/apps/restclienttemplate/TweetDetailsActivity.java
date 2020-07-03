package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.databinding.ActivityTweetDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {

    Tweet tweet;
    public static final String TAG = "TweetDetailsActivity";
    public static final String TD = "TWEETSDETAIL";
    ImageView PP1;
    ImageView PP2;
    TextView likeCD;
    TextView retweetCD;
    TextView tweetBodyD;
    TextView tvnameD;
    TextView tvUsernameD;
    TextView datecreatedD;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTweetDetailsBinding binding = ActivityTweetDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(TimelineActivity.colorDrawable);

        PP1 = binding.profilePICC;
        tweetBodyD = binding.tweetText;
        tvnameD = binding.name1;
        tvUsernameD = binding.name2;
        datecreatedD = binding.dateposted;
        likeCD = binding.likeView;
        retweetCD = binding.rtView;
        PP2 = binding.imagePIC;


        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(TD));
        Log.d(TAG, String.format("Showing tweet details for '%s'", tweet.user.name));

        tweetBodyD.setText(tweet.body);
        tvnameD.setText(tweet.user.name);
        tvUsernameD.setText("@" + tweet.user.screenName);
        datecreatedD.setText(TweetsAdapter.getRelativeTimeAgo(tweet.createdAt));
        likeCD.setText(TweetsAdapter.checker(tweet.likes)); //checker(tweet.likes)
        retweetCD.setText(TweetsAdapter.checker(tweet.retweets));
        Glide.with(this).load(tweet.user.profileImageURL).transform(new CircleCrop()).into(PP1);

        if(tweet.photoURL != null){
            Glide.with(this).load(tweet.photoURL).into(PP2);
            PP2.setVisibility(View.VISIBLE);
        }
        else{
            Glide.with(this).load("https://pbs.twimg.com/media/DOhM30VVwAEpIHq.jpg").into(PP2);
            PP2.setVisibility(View.GONE);
        }
    }
}
package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;
    //TwitterClient client;
    RecyclerView container;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
        //client = TwitterApp.getRestClient(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean elements of recycler

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    //Add list of items in our dataset.

    public void addAll(List<Tweet> tweetlist) {
        tweets.addAll(tweetlist);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvname;
        TextView tvUsername;
        TextView datecreated;
        TextView likeC;
        TextView retweetC;
        TextView commentC;
        ImageView photoURLC;
        RecyclerView myTweet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvname = itemView.findViewById(R.id.tvName);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            datecreated = itemView.findViewById(R.id.createDate);
            likeC = itemView.findViewById(R.id.likeCount);
            retweetC = itemView.findViewById(R.id.retweetCount);
            commentC = itemView.findViewById(R.id.comment);
            photoURLC = itemView.findViewById(R.id.imageTweet);

            itemView.setOnClickListener(this);
            tvBody.setOnClickListener(this);
        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvname.setText(tweet.user.name);
            tvUsername.setText("@" + tweet.user.screenName);
            datecreated.setText(getRelativeTimeAgo(tweet.createdAt));
            likeC.setText(checker(tweet.likes)); //checker(tweet.likes)
            retweetC.setText(checker(tweet.retweets));
            commentC.setText("");
            //Glide.with(context).load(tweet.user.profileImageURL).into(ivProfileImage);
            Glide.with(context).load(tweet.user.profileImageURL).transform(new CircleCrop()).into(ivProfileImage);
            if(tweet.photoURL != null){
                Glide.with(context).load(tweet.photoURL).into(photoURLC);
                photoURLC.setVisibility(View.VISIBLE);
            }
            else{
                Glide.with(context).load("https://pbs.twimg.com/media/DOhM30VVwAEpIHq.jpg").into(photoURLC);
                photoURLC.setVisibility(View.GONE);
            }
            //Log.d("PhotoURL", tweet.user.name + tweet.body + " 's picURL is: " +  tweet.photoURL);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Tweet tweet = tweets.get(position);
            Log.d("Load tweet details for", tweet.body);
            Intent i = new Intent(context, TweetDetailsActivity.class);
            i.putExtra(TweetDetailsActivity.TD, Parcels.wrap(tweet));
            context.startActivity(i);
        }
    }

    public static String getRelativeTimeAgo(String rawJsonDate){
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] check = relativeDate.split(" ");
        return check[0] + check[1].charAt(0);
    }

    public static String checker(int mynum){
        String temp = NumberFormat.getNumberInstance(Locale.US).format(mynum);
        String[] check = temp.split(",");
        if(mynum <= 9999) {
            return temp;
        }
        else if(mynum <= 99999){
            if(check[1].charAt(0) != 0) {
                return check[0] + "." + check[1].charAt(0) + "K";
            }
            else {
                return check[0] + "K";
            }
        }
        else if(mynum <= 999999){
            return check[0] + "K";
        }
        else if(mynum <= 99999999){
            if(check[1].charAt(0) !=0 ) {
                return check[0] + "." + check[1].charAt(0) + "M";
            }
            else {
                return check[0] + "M";
            }
        }
        else{
            return temp;
        }
    }
}

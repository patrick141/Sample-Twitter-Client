package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity=User.class, parentColumns="id", childColumns="userId"))
public class Tweet {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public int likes;

    @ColumnInfo
    public int retweets;

    @ColumnInfo
    public long userId;

    @ColumnInfo
    public String photoURL;

    @Ignore
    public boolean favorited;

    @Ignore
    public boolean isTruncated;

    @Ignore
    public User user;

    public Tweet(){}

    //The attributes from the JsonObject that we want a Tweet to have.
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.likes = jsonObject.getInt("favorite_count");
        tweet.retweets = jsonObject.getInt("retweet_count");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.isTruncated = jsonObject.getBoolean("truncated");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;
        try {
            //JSONObject jsonObject1 = jsonObject.getJSONObject("entities");
            //JSONArray jsonArray = jsonObject1.getJSONArray("media");
            //JSONObject jsonObject2 = jsonArray.getJSONObject(0);
            //tweet.photoURL = jsonObject2.getString("media_url_https");
            tweet.photoURL = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
        }catch(JSONException e){
         //   tweet.photoURL = null;
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }


}

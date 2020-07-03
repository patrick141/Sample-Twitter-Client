package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface TweetDao {

    @Query("SELECT Tweet.body AS tweet_body, Tweet.createdAt AS tweet_createdAt, Tweet.likes AS tweet_likes,    " +
            " Tweet.retweets AS tweet_retweets, Tweet.id AS tweet_id, Tweet.photoURL AS tweet_photoURL, User.* " + //, Tweet.photoURL as tweet_photoURL,
            "FROM Tweet INNER JOIN User ON Tweet.userId = User.id ORDER BY Tweet.createdAt DESC LIMIT 5")
    List<TweetWithUser> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);
}

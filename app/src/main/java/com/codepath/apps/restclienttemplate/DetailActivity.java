package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.text.NumberFormat;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private TextView tvName, tvHandle, tvDescription, tvLocation, tvFollowing, tvFollowers, tvBody, tvCreated;
    private ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvName);
        tvHandle = findViewById(R.id.tvHandle);
        tvDescription = findViewById(R.id.tvDescription);
        tvLocation = findViewById(R.id.tvLocation);
        tvFollowing = findViewById(R.id.tvFollowing);
        tvFollowers = findViewById(R.id.tvFollowers);
        tvBody = findViewById(R.id.tvBody);
        tvCreated = findViewById(R.id.tvCreated);
        ivProfileImage = findViewById(R.id.ivProfileImage);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        User user = tweet.getUser();
        tvName.setTypeface(tvName.getTypeface(), Typeface.BOLD);
        tvName.setText(user.getName());
        tvHandle.setText( "@" + user.getHandle());
        tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
        tvDescription.setText(Html.fromHtml(user.getDescription()));

        if (user.getLocation() != null && !user.getLocation().equals(""))
            tvLocation.setText(Html.fromHtml("<b> Location</b>: " + user.getLocation()));
        else
            tvLocation.setText("");

        NumberFormat numberFormat = NumberFormat.getInstance();
        tvFollowing.setText(Html.fromHtml("<b>" + numberFormat.format(user.getFollowing()) + "</b> Following"));
        tvFollowers.setText(Html.fromHtml("<b>" + numberFormat.format(user.getFollowers()) + "</b> Followers"));

        tvBody.setMovementMethod(LinkMovementMethod.getInstance());
        tvBody.setText(Html.fromHtml(tweet.getBody()));
        tvCreated.setText(TimeFormatter.getTimeDifference(tweet.getCreatedAt()));

        Glide.with(this).load(tweet.getUser().getProfileImageURL()).into(ivProfileImage);
    }
}
package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    private final String TAG = "ComposeActivity";
    private final int MAX_TWEET_LENGTH = 140;
    private TwitterClient client;
    private EditText etCompose;
    private Button btnTweet;
    private TextView charCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApplication.getRestClient(this);
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        charCounter = findViewById(R.id.charCounter);

        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 || charSequence.length() > MAX_TWEET_LENGTH)
                    charCounter.setTextColor(Color.RED);
                else
                    charCounter.setTextColor(Color.BLUE);

                charCounter.setText(charSequence.length() + "/" + MAX_TWEET_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etCompose.getText().toString();

                if (content.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Sorry, tweet cannot be empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (content.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(ComposeActivity.this, "Sorry, tweet is too long!", Toast.LENGTH_LONG).show();
                    return;
                }

                client.publishTweet(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "The tweet was successfully published.");
                        try {
                            Tweet tweet = Tweet.fromJSON(json.jsonObject);
                            Log.i(TAG, "The published tweet's contents: '" + tweet.getBody() + "'");
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, intent);
                            finish();
                        } catch (JSONException e) {
                            Log.e(TAG, "Failed to obtain published tweet contents. (JSON Exception).");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "There was an error publishing the tweet.", throwable);
                    }
                }, content);

            }
        });
    }
}
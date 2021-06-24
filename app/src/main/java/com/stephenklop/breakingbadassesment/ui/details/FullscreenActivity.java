package com.stephenklop.breakingbadassesment.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.stephenklop.breakingbadassesment.R;

public class FullscreenActivity extends AppCompatActivity {
    private String characterPosterLink, previousActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        // Set the back button
        setBackButton();

        // Set image
        setPosterImage();
    }

    private void setBackButton() {
        characterPosterLink = getIntent().getStringExtra("posterImage");
        previousActivity = getIntent().getStringExtra("prevActivity");

        ImageButton backButton = findViewById(R.id.activity_detail_image_imgbtn_backicon);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(v -> {
            try {
                Intent backIntent = new Intent(getApplicationContext(), Class.forName(previousActivity));
                backIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(backIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setPosterImage() {
        Glide.with(getApplicationContext()).load(characterPosterLink).into((ImageView) findViewById(R.id.activity_detail_image_img));
    }
}

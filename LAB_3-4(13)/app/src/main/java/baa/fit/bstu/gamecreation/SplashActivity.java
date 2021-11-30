package baa.fit.bstu.gamecreation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(this::routeToAppropriatePage, 3000L);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.in_fade);
        Animation topIn = AnimationUtils.loadAnimation(this, R.anim.in_top);

        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.xbox);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();

        findViewById(R.id.titleTextField).startAnimation(fadeIn);
        findViewById(R.id.bottomTextField).startAnimation(fadeIn);
        findViewById(R.id.appImageField).startAnimation(topIn);
    }

    private void routeToAppropriatePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
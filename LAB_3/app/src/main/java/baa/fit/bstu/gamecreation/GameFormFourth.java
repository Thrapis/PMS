package baa.fit.bstu.gamecreation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import baa.fit.bstu.gamecreation.util.ActivityCode;
import baa.fit.bstu.gamecreation.util.GameLiteral;

public class GameFormFourth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form_fourth);

        byte[] byteArray = getIntent().getByteArrayExtra(GameLiteral.IMAGE);
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        String name = getIntent().getStringExtra(GameLiteral.NAME);
        String genre = getIntent().getStringExtra(GameLiteral.GENRE);
        Date date = (Date)getIntent().getSerializableExtra(GameLiteral.DATE);


        ((TextView)findViewById(R.id.game_name)).setText(name);
        ((TextView)findViewById(R.id.game_genre)).setText(genre);
        String dateFormatted = date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
        ((TextView)findViewById(R.id.game_date)).setText(dateFormatted);
        ((ImageView)findViewById(R.id.game_image)).setImageBitmap(image);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.in_left, R.anim.out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }

    public void done(View view) {
        Intent intent = new Intent();
        intent.putExtras(getIntent().getExtras());
        setResult(ActivityCode.GAME_RESULT, intent);
        finish();
    }
}
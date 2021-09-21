package baa.fit.bstu.gamecreation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;

import baa.fit.bstu.gamecreation.entities.GameDescription;
import baa.fit.bstu.gamecreation.util.ActivityCode;
import baa.fit.bstu.gamecreation.util.GameLiteral;

public class GameFormSecond extends AppCompatActivity {

    private final String[] Genres = {"RPG", "Hack and slash", "MMORPG", "MOBA", "Shooter", "Horror", "Strategy", "Arcade"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form_second);

        prepareSpinner();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void prepareSpinner() {
         Spinner spinner = findViewById(R.id.game_genre);
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, Genres);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(adapter);
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

    public void toNext(View view) {
        Spinner genreField = findViewById(R.id.game_genre);
        EditText developerField = findViewById(R.id.game_developer);
        EditText publisherField = findViewById(R.id.game_publisher);

        Intent intent = new Intent(this, GameFormThird.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra(GameLiteral.GENRE, genreField.getSelectedItem().toString());
        intent.putExtra(GameLiteral.DEVELOPER, developerField.getText().toString());
        intent.putExtra(GameLiteral.PUBLISHER, publisherField.getText().toString());

        startActivityForResult(intent, ActivityCode.GAME_RESULT);
        overridePendingTransition(R.anim.in_right, R.anim.out_left);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case ActivityCode.GAME_RESULT: {
                setResult(ActivityCode.GAME_RESULT, data);
                finish();
                break;
            }
        }
    }
}
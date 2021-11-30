package baa.fit.bstu.gamecreation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import baa.fit.bstu.gamecreation.entities.GameDescription;
import baa.fit.bstu.gamecreation.util.GameLiteral;

public class GameInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        GameDescription game = (GameDescription) getIntent().getParcelableExtra(GameLiteral.GAME);

        ((TextView)findViewById(R.id.game_name)).setText(game.getName());
        ((TextView)findViewById(R.id.game_genre)).setText(game.getGenre());
        String dateFormatted = new SimpleDateFormat("dd MMMM").format(game.getDate()) + " of " + game.getDate().getYear();
        ((TextView)findViewById(R.id.game_date)).setText(dateFormatted);
        ((ImageView)findViewById(R.id.game_image)).setImageBitmap(game.getImageAsBitmap());
        ((TextView)findViewById(R.id.game_review)).setText(game.getReview());
        ((TextView)findViewById(R.id.game_version)).setText(game.getVersion());
        ((TextView)findViewById(R.id.game_developer)).setText(game.getDeveloper());
        ((TextView)findViewById(R.id.game_publisher)).setText(game.getPublisher());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(0, R.anim.out_zoom);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.out_zoom);
    }
}
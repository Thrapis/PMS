package baa.fit.bstu.gamecreation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import baa.fit.bstu.gamecreation.entities.GameDescription;
import baa.fit.bstu.gamecreation.util.ActivityCode;
import baa.fit.bstu.gamecreation.util.GameContainer;
import baa.fit.bstu.gamecreation.util.GameLiteral;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameContainer.importFromJson(this);
        updateGameList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Remove");
        //menu.add(0, v.getId(), 0, "Update");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Remove") {
            int id = item.getItemId();
            GameContainer.games.remove(id);
        }
        else {
            return  false;
        }
        GameContainer.exportToJson(this);
        updateGameList();
        return true;
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, GameFormFirst.class);
        startActivityForResult(intent, ActivityCode.GAME_RESULT);
        overridePendingTransition(R.anim.in_unzoom, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case ActivityCode.GAME_RESULT: {
                byte[] image = data.getByteArrayExtra(GameLiteral.IMAGE);
                String name = data.getStringExtra(GameLiteral.NAME);
                String genre = data.getStringExtra(GameLiteral.GENRE);
                Date date = (Date)data.getSerializableExtra(GameLiteral.DATE);
                String version = data.getStringExtra(GameLiteral.VERSION);
                String review = data.getStringExtra(GameLiteral.REVIEW);
                String developer = data.getStringExtra(GameLiteral.DEVELOPER);
                String publisher = data.getStringExtra(GameLiteral.PUBLISHER);

                GameDescription game = new GameDescription(name, image, genre, date, version, review, developer, publisher);
                GameContainer.games.add(game);
                GameContainer.exportToJson(this);
                updateGameList();
                break;
            }
        }
    }

    public void updateGameList() {
        TableLayout placeLayout = findViewById(R.id.games_list);
        placeLayout.removeAllViews();

        for (int i = 0; i < GameContainer.games.size(); i++) {
            GameDescription game = GameContainer.games.get(i);

            final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
            registerForContextMenu(tableRow);
            tableRow.setId(i);
            TextView tv;
            ImageView iv;

            iv = (ImageView) tableRow.findViewById(R.id.rowImage);
            iv.setImageBitmap(game.getImageAsBitmap());

            tv = (TextView) tableRow.findViewById(R.id.rowName);
            tv.setText(String.format("Name: %s", game.getName()));

            tv = (TextView) tableRow.findViewById(R.id.rowGenre);
            tv.setText(String.format("Genre: %s", game.getGenre()));

            tv = (TextView) tableRow.findViewById(R.id.rowDate);
            String dateFormatted = new SimpleDateFormat("dd MMMM").format(game.getDate())
                    + " of " + game.getDate().getYear();
            tv.setText(String.format("Date: %s", dateFormatted));


            placeLayout.addView(tableRow);
        }
    }

    public void save(View view) {
        if (GameContainer.exportToJson(this)) {
            Toast.makeText(this, "Saving games success", Toast.LENGTH_LONG);
        }
        else {
            Toast.makeText(this, "Saving games failed", Toast.LENGTH_LONG);
        }
    }

    public void load(View view) {
        if (GameContainer.importFromJson(this)) {
            Toast.makeText(this, "Loading games success", Toast.LENGTH_LONG);
        }
        else {
            Toast.makeText(this, "Loading games failed", Toast.LENGTH_LONG);
        }
        updateGameList();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        GameContainer.importFromJson(this);
        updateGameList();
    }
}
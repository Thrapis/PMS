package baa.fit.bstu.gamecreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import baa.fit.bstu.gamecreation.entities.GameDescription;
import baa.fit.bstu.gamecreation.util.ActivityCode;
import baa.fit.bstu.gamecreation.util.GameLiteral;

public class MainActivity extends AppCompatActivity {

    private List<GameDescription> _games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doSomething(View view) {
        Intent intent = new Intent(this, GameFormFirst.class);
        startActivityForResult(intent, ActivityCode.GAME_RESULT);
        overridePendingTransition(R.anim.in_unzoom, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case ActivityCode.GAME_RESULT: {
                byte[] byteArray = data.getByteArrayExtra(GameLiteral.IMAGE);
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                String name = data.getStringExtra(GameLiteral.NAME);
                String genre = data.getStringExtra(GameLiteral.GENRE);
                Date date = (Date)data.getSerializableExtra(GameLiteral.DATE);

                GameDescription game = new GameDescription(name, image, genre, date, "1.0");
                _games.add(game);
                updateGameList();
                break;
            }
        }
    }

    public void updateGameList() {
        TableLayout placeLayout = findViewById(R.id.games_list);
        placeLayout.removeAllViews();

        for (int i = 0; i < _games.size(); i++) {
            GameDescription game = _games.get(i);

            final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
            TextView tv;
            ImageView iv;

            iv = (ImageView) tableRow.findViewById(R.id.rowImage);
            iv.setImageBitmap(game.getImage());

            tv = (TextView) tableRow.findViewById(R.id.rowName);
            tv.setText("Name: " + game.getName());

            tv = (TextView) tableRow.findViewById(R.id.rowGenre);
            tv.setText("Genre: " + game.getGenre());

            tv = (TextView) tableRow.findViewById(R.id.rowDate);
            String dateFormatted = game.getDate().getDay() + "-" + game.getDate().getMonth() + "-" + game.getDate().getYear();
            tv.setText("Date: " + dateFormatted);

            placeLayout.addView(tableRow);
        }
    }
}
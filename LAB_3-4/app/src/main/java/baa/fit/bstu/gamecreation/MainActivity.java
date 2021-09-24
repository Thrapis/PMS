package baa.fit.bstu.gamecreation;

import static baa.fit.bstu.gamecreation.util.ActivityCode.GAME_RESULT;
import static baa.fit.bstu.gamecreation.util.ActivityCode.TAKE_PHOTO;
import static baa.fit.bstu.gamecreation.util.ApplicationLiteral.FACEBOOK_APP_PACKAGE_ID;
import static baa.fit.bstu.gamecreation.util.ApplicationLiteral.VK_APP_PACKAGE_ID;
import static baa.fit.bstu.gamecreation.util.ApplicationLiteral.TWITTER_APP_PACKAGE_ID;
import static baa.fit.bstu.gamecreation.util.GameLiteral.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;

import baa.fit.bstu.gamecreation.entities.GameDescription;
import baa.fit.bstu.gamecreation.entities.Social;
import baa.fit.bstu.gamecreation.entities.UserInfo;
import baa.fit.bstu.gamecreation.util.ActivityCode;
import baa.fit.bstu.gamecreation.util.GameContainer;

public class MainActivity extends AppCompatActivity {

    private static UserInfo userInfo =
            new UserInfo("artbelski1@mail.ru", "+375447464364",
                    new Social[] {
                            new Social("VKontakte","http://vk.com/artbelski", VK_APP_PACKAGE_ID),
                            new Social("Facebook","https://www.facebook.com/profile.php?id=100009319098847", FACEBOOK_APP_PACKAGE_ID),
                            new Social("Twitter","https://twitter.com/thrapis", TWITTER_APP_PACKAGE_ID)
                    },
                    new byte[0]);
    private Dialog infoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameContainer.importFromJson(this);
        findViewById(R.id.info_button).setOnClickListener(view -> showInfo());
        updateGameList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Info");
        menu.add(0, v.getId(), 0, "Remove");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Info") {
            int id = item.getItemId();
            Intent infoIntent = new Intent(this, GameInfo.class);
            GameDescription putGame = GameContainer.games.get(id);
            infoIntent.putExtra(GAME, (Parcelable) putGame);
            startActivity(infoIntent);
            overridePendingTransition(R.anim.in_unzoom, 0);
        }
        else if (item.getTitle() == "Remove") {
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
        startActivityForResult(intent, GAME_RESULT);
        overridePendingTransition(R.anim.in_unzoom, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO: {
                if (data == null || data.getExtras() == null)
                    break;
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                userInfo.setImageFromBitmap(bmp);
                showInfo();
                break;
            }
        }

        switch (resultCode) {
            case GAME_RESULT: {
                byte[] image = data.getByteArrayExtra(IMAGE);
                String name = data.getStringExtra(NAME);
                String genre = data.getStringExtra(GENRE);
                Date date = (Date)data.getSerializableExtra(DATE);
                String version = data.getStringExtra(VERSION);
                String review = data.getStringExtra(REVIEW);
                String developer = data.getStringExtra(DEVELOPER);
                String publisher = data.getStringExtra(PUBLISHER);

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
            tableRow.setClickable(true);
            tableRow.setOnClickListener(view -> {
                Intent infoIntent = new Intent(this, GameInfo.class);
                int id = view.getId();
                GameDescription putGame = GameContainer.games.get(id);
                infoIntent.putExtra(GAME, (Parcelable) putGame);
                startActivity(infoIntent);
                overridePendingTransition(R.anim.in_unzoom, 0);
            });
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

    public void showInfo() {
        if (infoDialog == null) {
            infoDialog = new Dialog(this);
            infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            infoDialog.setContentView(R.layout.info);
        }
        else {
            infoDialog.dismiss();
        }

        int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.9);
        int height = (int)(getResources().getDisplayMetrics().heightPixels * 0.9);
        infoDialog.getWindow().setLayout(width, height);
        infoDialog.show();

        LinearLayout socialLayout = infoDialog.findViewById(R.id.social_layout);
        TextView emailField = infoDialog.findViewById(R.id.emailValue);
        TextView phoneField = infoDialog.findViewById(R.id.phoneValue);
        ImageView imageField = infoDialog.findViewById(R.id.imageValue);
        Button closeButton = infoDialog.findViewById(R.id.close_button);

        emailField.setText(userInfo.getEmail());
        phoneField.setText(userInfo.getPhone());
        imageField.setImageBitmap(userInfo.getImageAsBitmap());

        socialLayout.removeAllViews();
        for (int i = 0 ; i < userInfo.getSocials().length; i++) {
            TextView socialField = (TextView) getLayoutInflater().inflate(R.layout.info_social_row, null);
            socialField.setText(userInfo.getSocials()[i].getName());
            socialField.setTag(userInfo.getSocials()[i]);
            socialField.setOnClickListener(view -> openLink(view));
            socialLayout.addView(socialField);
        }

        emailField.setOnClickListener(view -> toEmail(view));
        phoneField.setOnClickListener(view -> toPhone(view));
        imageField.setOnClickListener(view -> takePhoto(view));
        closeButton.setOnClickListener(view -> {
            infoDialog.dismiss();
        });
    }

    public void toEmail(View view) {
        String[] addresses = { "artbelski1@mail.ru" };
        String subject = "Just to talk";
        Uri attachment = null;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //intent.putExtra(Intent.EXTRA_STREAM, attachment);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void toPhone(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "+375447464364"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, ActivityCode.TAKE_PHOTO);
        }
    }

    public void openLink(View view) {
        Social social = (Social) view.getTag();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(social.getLink()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
package baa.fit.bstu.gamecreation.util;

import android.content.Context;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import baa.fit.bstu.gamecreation.entities.GameDescription;

public class GameContainer {

    private final static String FILE_NAME = "game_data.json";

    public static List<GameDescription> games = new ArrayList<>();

    public static boolean exportToJson(Context context) {

        Gson gson = new Gson();
        GameDescriptionsShell shell = new GameDescriptionsShell(games);
        String jsonString = gson.toJson(shell);

        FileOutputStream outputStream = null;

        try {
            outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try { 
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return false;
    }

    public static boolean importFromJson(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream inputStream = null;

        try {
            inputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            games = gson.fromJson(streamReader, GameDescriptionsShell.class).getGames();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private static class GameDescriptionsShell {

        private List<GameDescription> games;

        public GameDescriptionsShell(List<GameDescription> games) {
            this.games = games;
        }

        List<GameDescription> getGames() {
            return games;
        }

        void setGames(List<GameDescription> games) {
            this.games = games;
        }
    }
}

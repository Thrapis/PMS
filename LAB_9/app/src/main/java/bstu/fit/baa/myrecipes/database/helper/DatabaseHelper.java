package bstu.fit.baa.myrecipes.database.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import bstu.fit.baa.myrecipes.R;
import bstu.fit.baa.myrecipes.database.dao.RecipeDao;
import bstu.fit.baa.myrecipes.entity.Recipe;
import bstu.fit.baa.myrecipes.util.ImageManager;

@Database(entities = {Recipe.class}, version = 2)
public abstract class DatabaseHelper extends RoomDatabase {
    private static DatabaseHelper instance;
    private static Context context;
    public abstract RecipeDao recipeDao();

    public static synchronized DatabaseHelper getInstance(Context context) {
        instance.context = context;
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseHelper.class,
                    "database").fallbackToDestructiveMigration()
                    .addCallback(callback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecipeDao recipeDao;
        public InitialDataAsyncTask(DatabaseHelper database) {
            recipeDao = database.recipeDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            /*Recipe recipe = new Recipe();
            recipe.name = "Test";
            recipe.formula = "Some formula";
            recipe.type = "Balanced";

            Drawable defaultDrawable = context.getResources().getDrawable(R.drawable.dish);
            Bitmap defaultBitmap = ((BitmapDrawable)defaultDrawable).getBitmap();
            recipe.imageUri = ImageManager.saveToInternalStorage(context, defaultBitmap);

            recipeDao.insert(recipe);*/

            return null;
        }
    }

}

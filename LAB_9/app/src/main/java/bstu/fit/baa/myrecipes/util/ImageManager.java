package bstu.fit.baa.myrecipes.util;

import static android.provider.MediaStore.Images.Media.getBitmap;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ImageManager {

    public static void clearUnused(Context context, List<String> imageUriStrings) {

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("images", Context.MODE_PRIVATE);

        String[] pathNames = directory.list();
        Boolean[] used = new Boolean[pathNames.length];
        for (int i = 0; i < pathNames.length; i++) {
            String selectedPath = directory.getAbsolutePath() + "/" + pathNames[i];
            if (imageUriStrings.contains(selectedPath)) used[i] = true;
            else used[i] = false;
        }

        for (int i = 0; i < pathNames.length; i++) {
            if (!used[i]) {
                String selectedPath = directory.getAbsolutePath() + "/" + pathNames[i];
                File fileToDelete = new File(selectedPath);
                boolean res = fileToDelete.delete();
            }
        }
    }

    public static String saveToInternalStorage(Context context, Uri selectedImageUri){

        File file = new File(selectedImageUri.getEncodedPath());
        String name = file.getName() + ".png";

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        File newPath = new File(directory, name);

        FileOutputStream fos = null;
        try {
            Bitmap bitmapImage = null;
            bitmapImage = getBitmap(context.getContentResolver(), selectedImageUri);

            fos = new FileOutputStream(newPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newPath.getAbsolutePath();
    }

    public static String saveToInternalStorage(Context context, Bitmap bitmapImage){

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        File newPath = null;

        FileOutputStream fos = null;
        try {
            String nameAndExt = "";
            do {
                nameAndExt = String.format("%s.%s", RandomStringUtils
                        .randomAlphanumeric(8), "png");

            } while(new File(directory, nameAndExt).exists());
            newPath = new File(directory, nameAndExt);
            fos = new FileOutputStream(newPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newPath.getAbsolutePath();
    }
}

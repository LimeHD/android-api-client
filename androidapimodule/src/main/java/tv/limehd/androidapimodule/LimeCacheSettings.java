package tv.limehd.androidapimodule;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class LimeCacheSettings {


    private static String DOWNLOADING_CACHE = "DOWNLOADING_CACHE";

    public static int getMaxAge(@NonNull Context context, @NonNull Class type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DOWNLOADING_CACHE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(type.getName(), 0);
    }

    public static void setMaxAge(@NonNull Context context, @NonNull Class type, int maxAge) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DOWNLOADING_CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(type.getName(), maxAge);
        editor.apply();
        editor.commit();
    }
}

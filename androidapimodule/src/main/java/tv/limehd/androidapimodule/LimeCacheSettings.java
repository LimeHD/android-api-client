package tv.limehd.androidapimodule;

import android.content.Context;
import android.content.SharedPreferences;

public class LimeCacheSettings {


    private static String DOWNLOADING_CACHE = "DOWNLOADING_CACHE";

    public static String DOWNLOADER_PING = "DOWNLOADER_PING";
    public static String DOWNLOADER_SESSION = "DOWNLOADER_SESSION";
    public static String DOWNLOADER_CHANNEL_LIST = "DOWNLOADER_CHANNEL_LIST";
    public static String DOWNLOADER_BROADCAST = "DOWNLOADER_BROADCAST";
    public static String SENDER_DEEPCLICKS = "SENDER_DEEPCLICKS";

    public static int getMaxAge(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DOWNLOADING_CACHE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static void setMaxAge(Context context, String key, int maxAge) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DOWNLOADING_CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, maxAge);
        editor.apply();
        editor.commit();
    }


}

package tv.limehd.androidapiclient;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private static String MY_SETTINGS = "MY_SETTINGS";
    private static String DATA_REQUESTS = "DATA_REQUESTS";

    public static void setDataRequests(Context context, String dataRequests) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(DATA_REQUESTS, dataRequests);
        editor.apply();
        editor.commit();
    }

    public static String getDataRequests(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        return mSettings.getString(DATA_REQUESTS, "");
    }

}

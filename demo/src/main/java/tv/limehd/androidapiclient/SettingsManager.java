package tv.limehd.androidapiclient;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private static String MY_SETTINGS = "MY_SETTINGS";

    private static String API_ROOT = "API_ROOT";
    private static String X_ACCESS_TOKEN = "X_ACCESS_TOKEN";
    private static String APPLICATION_ID = "APPLICATION_ID";

    public static void setApiRoot(Context context, String api_root) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(API_ROOT, api_root);
        editor.apply();
        editor.commit();
    }

    public static String getApiRoot(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        return mSettings.getString(API_ROOT, "");
    }

    public static void setXAccessToken(Context context, String x_access_token) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(X_ACCESS_TOKEN, x_access_token);
        editor.apply();
        editor.commit();
    }

    public static String getXAccessToken(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        return mSettings.getString(X_ACCESS_TOKEN, "");
    }

    public static void setApplicationId(Context context, String application_id) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APPLICATION_ID, application_id);
        editor.apply();
        editor.commit();
    }

    public static String getApplicationId(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        return mSettings.getString(APPLICATION_ID, "");
    }

}

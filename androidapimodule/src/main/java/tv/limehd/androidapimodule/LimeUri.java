package tv.limehd.androidapimodule;

import android.net.Uri;

public class LimeUri {


    private static String CHANNEL_ID = "channel_id";
    private static String TIME_ZONE = "time_zone";
    private static String START_AT = "start_at";
    private static String FINISH_AT = "finish_at";

    public static String getUriChannelList(String scheme, String api_root, String endpoint_channels, String channel_group_id) {
        return new Uri.Builder()
                .scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_channels)
                .appendEncodedPath(channel_group_id).build().toString();
    }

    public static String getUriBroadcast(String scheme, String api_root, String endpoint_broadcast
            , String channel_id, String before_date, String after_date, String time_zone) {
        return new Uri.Builder().scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_broadcast)
                .appendQueryParameter(CHANNEL_ID, channel_id)
                .appendQueryParameter(TIME_ZONE, time_zone)
                .appendQueryParameter(START_AT, before_date)
                .appendQueryParameter(FINISH_AT, after_date).build().toString();
    }

    public static String getUriSession(String scheme, String api_root, String endpoint_session){
        return new Uri.Builder()
                .scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_session).build().toString();
    }

    public static String getUriPing(String scheme, String api_root, String endpoint_ping) {
        return new Uri.Builder()
                .scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_ping).build().toString();
    }

    public static String getUriTranslation(String scheme, String api_root, String url_streaming
            , String translation_id, String redirect_path) {
        return new Uri.Builder()
                .scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(url_streaming)
                .appendEncodedPath(translation_id)
                .appendEncodedPath(redirect_path).build().toString();
    }

}

package tv.limehd.androidapimodule;

import android.net.Uri;

import androidx.annotation.NonNull;

import tv.limehd.androidapimodule.Download.Data.Component;

public class LimeUri {


    private static String CHANNEL_ID = "channel_id";
    private static String TIME_ZONE = "time_zone";
    private static String START_AT = "start_at";
    private static String FINISH_AT = "finish_at";
    private static String LOCALE = "locale";
    private static String DURATION = "duration";
    private static String TIME_ZONE_PICKER = "time_zone_picker";

    private static String STREAM_URL = "${stream_id}";

    @Deprecated
    public static String getUriChannelList(String scheme, String api_root, String endpoint_channels, String channel_group_id, String time_zone, String locale) {
        return new Uri.Builder()
                .scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_channels)
                .appendQueryParameter(LOCALE, locale)
                .appendQueryParameter(TIME_ZONE, time_zone)
                .appendEncodedPath(channel_group_id)
                .appendQueryParameter(TIME_ZONE_PICKER, "previous")
                .build()
                .toString();
    }

    public static String getUriChannelList(@NonNull Component.DataBasic dataBasic, @NonNull Component dataSpecific) {
        Component.DataChannelList dataChannelList = (Component.DataChannelList) dataSpecific;
        return new Uri.Builder()
                .scheme(dataBasic.getScheme())
                .authority(dataBasic.getApiRoot())
                .appendEncodedPath(dataBasic.getEndpoint())
                .appendQueryParameter(LOCALE, dataChannelList.getLocale())
                .appendQueryParameter(TIME_ZONE, dataChannelList.getTimeZone())
                .appendEncodedPath(dataChannelList.getChannelGroupId())
                .appendQueryParameter(TIME_ZONE_PICKER, "previous")
                .build()
                .toString();
    }

    @Deprecated
    public static String getUriBroadcast(String scheme, String api_root, String endpoint_broadcast
            , String channel_id, String before_date, String after_date, String time_zone, String locale) {
        return new Uri.Builder().scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_broadcast)
                .appendQueryParameter(CHANNEL_ID, channel_id)
                .appendQueryParameter(TIME_ZONE, time_zone)
                .appendQueryParameter(START_AT, before_date)
                .appendQueryParameter(LOCALE, locale)
                .appendQueryParameter(FINISH_AT, after_date)
                .build()
                .toString();
    }

    public static String getUriBroadcast(@NonNull Component.DataBasic dataBasic, @NonNull Component dataSpecific) {
        Component.DataBroadcast dataBroadcast = (Component.DataBroadcast) dataSpecific;
        return new Uri.Builder()
                .scheme(dataBasic.getScheme())
                .authority(dataBasic.getApiRoot())
                .appendEncodedPath(dataBasic.getEndpoint())
                .appendQueryParameter(CHANNEL_ID, dataBroadcast.getChannelId())
                .appendQueryParameter(TIME_ZONE, dataBroadcast.getTimeZone())
                .appendQueryParameter(START_AT, dataBroadcast.getBeforeDate())
                .appendQueryParameter(LOCALE, dataBroadcast.getLocale())
                .appendQueryParameter(FINISH_AT, dataBroadcast.getAfterDate())
                .build()
                .toString();

    }

    @Deprecated
    public static String getUriSession(String scheme, String api_root, String endpoint_session) {
        return new Uri.Builder()
                .scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_session)
                .build()
                .toString();
    }

    public static String getUriSession(@NonNull Component.DataBasic dataBasic, @NonNull Component dataSpecific) {
        Component.DataSession dataSession = (Component.DataSession) dataSpecific;
        return new Uri.Builder()
                .scheme(dataBasic.getScheme())
                .authority(dataBasic.getApiRoot())
                .appendEncodedPath(dataBasic.getEndpoint())
                .build()
                .toString();
    }

    @Deprecated
    public static String getUriPing(String scheme, String api_root, String endpoint_ping) {
        return new Uri.Builder()
                .scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_ping)
                .build()
                .toString();
    }

    public static String getUriPing(@NonNull Component.DataBasic dataBasic, @NonNull Component dataSpecific) {
        Component.DataPing dataPing = (Component.DataPing) dataSpecific;
        return new Uri.Builder()
                .scheme(dataBasic.getScheme())
                .authority(dataBasic.getApiRoot())
                .appendEncodedPath(dataBasic.getEndpoint())
                .build()
                .toString();
    }

    @Deprecated
    public static String getUriDeepClicks(String scheme, String api_root, String endpoint_deepclicks){
        return new Uri.Builder()
                .scheme(scheme)
                .authority(api_root)
                .appendEncodedPath(endpoint_deepclicks)
                .build()
                .toString();
    }

    public static String getUriDeepClicks(@NonNull Component.DataBasic dataBasic, @NonNull Component dataSpecific){
        Component.DataDeepClick dataDeepClick = (Component.DataDeepClick) dataSpecific;
        return new Uri.Builder()
                .scheme(dataBasic.getScheme())
                .authority(dataBasic.getApiRoot())
                .appendEncodedPath(dataBasic.getApiRoot())
                .build()
                .toString();
    }

    public static String getUriTranslation(String stream_url, String stream_id) {
        return stream_url.replace(STREAM_URL, stream_id);
    }

    public static String getArchiveUriTranslation(String archive_url, String stream_id, String start_at, String duration){
        String archive_hls_url = archive_url.replace(STREAM_URL, stream_id);
        String params = new Uri.Builder()
                .appendQueryParameter(START_AT, start_at)
                .appendQueryParameter(DURATION, duration)
                .build()
                .toString();
        return archive_hls_url + params;
    }

}

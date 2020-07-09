package tv.limehd.androidapimodule;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import tv.limehd.androidapimodule.Download.Client.ClientDownloading;
import tv.limehd.androidapimodule.Values.ApiValues;

public class LimeApiClient {

    private String api_root;
    private String application_id;
    private String scheme;
    private ApiValues apiValues;
    private String x_access_token;
    private String locale;
    private String x_test_ip;
    private boolean use_cache;
    private static File cacheDir;
    private static final int defaultMaxAge = 600;
    private Context context;

    public LimeApiClient(Context context, String api_root, String scheme, String application_id, String x_access_token, String locale, File cacheDir, boolean use_cache) {
        initialization(context, api_root, scheme, application_id, x_access_token, locale, cacheDir, use_cache);
    }

    public LimeApiClient(Context context, String api_root, String scheme, String application_id, String x_access_token, String locale, File cacheDir) {
        initialization(context, api_root, scheme, application_id, x_access_token, locale, cacheDir, true);
    }

    private void initialization(Context context, String api_root, String scheme, String application_id, String x_access_token, String locale, File cacheDir, boolean use_cache) {
        apiValues = new ApiValues();
        this.api_root = api_root;
        this.scheme = scheme;
        this.application_id = application_id;
        this.x_access_token = x_access_token;
        this.locale = locale;
        x_test_ip = null;
        this.use_cache = use_cache;
        this.cacheDir = cacheDir;
        this.context = context;
    }

    public void updateLimeApiClientData(String api_root, String scheme, String application_id, String x_access_token, String locale) {
        this.api_root = api_root;
        this.scheme = scheme;
        this.application_id = application_id;
        this.x_access_token = x_access_token;
        this.locale = locale;
        x_test_ip = null;
    }

    public static OkHttpClient.Builder connectCacheInOkHttpClient(OkHttpClient.Builder okHttpClientBuilder) {
        Cache cache = new Cache(getCacheDir(), convertMegaByteToByte(2));
        okHttpClientBuilder.cache(cache);
        return okHttpClientBuilder;
    }

    public static int getMaxCacheFromCacheControl(Response response) {
        try {
            String cacheControlValue = response.networkResponse().header("Cache-Control", "0");
            String[] cacheArray = cacheControlValue.split("=");
            return Integer.parseInt(cacheArray[cacheArray.length - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultMaxAge;
    }

    public void setUse_cache(boolean use_cache) {
        this.use_cache = use_cache;
    }

    public boolean getUse_cache() {
        return use_cache;
    }

    public void setXTestIp(String x_test_ip) {
        this.x_test_ip = x_test_ip;
    }

    public void updateApiRoot(String api_root) {
        this.api_root = api_root;
    }

    public void upDateLocale(String locale) {
        this.locale = locale;
    }

    /*Download channel List*/
    //region Download channel List

    public void downloadChannelList(String channel_group_id) {
        if (api_root != null) {
            ClientDownloading clientDownloading = initializeDownloadChannelList();
            downloadChannelList(clientDownloading, channel_group_id, use_cache);
        }
    }

    public void downloadChannelList(String channel_group_id, boolean use_cache) {
        if (api_root != null) {
            ClientDownloading clientDownloading = initializeDownloadChannelList();
            downloadChannelList(clientDownloading, channel_group_id, use_cache);
        }
    }

    private ClientDownloading initializeDownloadChannelList() {
        ClientDownloading clientDownloading = new ClientDownloading();
        clientDownloading.setCallBackDownloadInterface(new ClientDownloading.CallBackDownloadInterface() {
            @Override
            public void callBackDownloadedSuccess(String response) {
                if (downloadChannelListCallBack != null)
                    downloadChannelListCallBack.downloadChannelListSuccess(response);
            }

            @Override
            public void callBackDownloadedError(String error_message) {
                if (downloadChannelListCallBack != null)
                    downloadChannelListCallBack.downloadChannelListError(error_message);
            }
        });
        clientDownloading.setCallBackRequestInterface(new ClientDownloading.CallBackRequestInterface() {
            @Override
            public void callBackUrlRequest(String request) {
                if (requestChannelList != null)
                    requestChannelList.callBackUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                if (requestChannelList != null)
                    requestChannelList.callBackCurlRequest(request);
            }
        });
        return clientDownloading;
    }

    private static File getCacheDir() {
        return cacheDir;
    }

    private static long convertMegaByteToByte(int megaByte) {
        return megaByte * 1024 * 1024;
    }

    private void downloadChannelList(ClientDownloading clientDownloading, String channel_group_id, boolean use_cache) {
        clientDownloading.downloadChannelList(context, scheme, api_root, apiValues.getURL_CHANNELS_BY_GROUP(), application_id, x_access_token, channel_group_id, locale, x_test_ip, use_cache);
    }

    public interface DownloadChannelListCallBack {
        void downloadChannelListSuccess(String response);

        void downloadChannelListError(String message);
    }

    private DownloadChannelListCallBack downloadChannelListCallBack;

    public void setDownloadChannelListCallBack(DownloadChannelListCallBack downloadChannelListCallBack) {
        this.downloadChannelListCallBack = downloadChannelListCallBack;
    }
    //endregion

    /*Download broadcast*/
    //region DownloadBroadcast

    public void downloadBroadcast(String channel_id, String before_date, String after_date, String time_zone) {
        if (api_root != null) {
            ClientDownloading clientDownloading = initializeDownloadBroadcast();
            downloadBroadcast(clientDownloading, channel_id, before_date, after_date, time_zone, use_cache);
        }
    }

    public void downloadBroadcast(String channel_id, String before_date, String after_date, String time_zone, boolean use_cache) {
        if (api_root != null) {
            ClientDownloading clientDownloading = initializeDownloadBroadcast();
            downloadBroadcast(clientDownloading, channel_id, before_date, after_date, time_zone, use_cache);
        }
    }

    private ClientDownloading initializeDownloadBroadcast() {
        ClientDownloading clientDownloading = new ClientDownloading();
        clientDownloading.setCallBackDownloadInterface(new ClientDownloading.CallBackDownloadInterface() {
            @Override
            public void callBackDownloadedSuccess(String response) {
                if (downloadBroadCastCallBack != null)
                    downloadBroadCastCallBack.downloadBroadCastSuccess(response);
            }

            @Override
            public void callBackDownloadedError(String error_message) {
                if (downloadBroadCastCallBack != null)
                    downloadBroadCastCallBack.downloadBroadCastError(error_message);
            }
        });
        clientDownloading.setCallBackRequestInterface(new ClientDownloading.CallBackRequestInterface() {
            @Override
            public void callBackUrlRequest(String request) {
                if (requestBroadCastCallBack != null)
                    requestBroadCastCallBack.callBackUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                if (requestBroadCastCallBack != null)
                    requestBroadCastCallBack.callBackCurlRequest(request);
            }
        });
        return clientDownloading;
    }

    private void downloadBroadcast(ClientDownloading clientDownloading, String channel_id, String before_date, String after_date, String time_zone, boolean use_cache) {
        clientDownloading.downloadBroadCast(context, scheme, api_root, apiValues.getURL_BROADCAST_PATH(), channel_id, before_date, after_date, time_zone, application_id
                , x_access_token, locale, x_test_ip, use_cache);
    }

    public interface DownloadBroadCastCallBack {
        void downloadBroadCastSuccess(String response);

        void downloadBroadCastError(String message);
    }

    private DownloadBroadCastCallBack downloadBroadCastCallBack;

    public void setDownloadBroadCastCallBack(DownloadBroadCastCallBack downloadBroadCastCallBack) {
        this.downloadBroadCastCallBack = downloadBroadCastCallBack;
    }
    //endregion

    //region Download ping

    public void downloadPing() {
        if (api_root != null) {
            ClientDownloading clientDownloading = initializeDownloadPing();
            downloadPing(clientDownloading, use_cache);
        }
    }

    public void downloadPing(boolean use_cache) {
        if (api_root != null) {
            ClientDownloading clientDownloading = initializeDownloadPing();
            downloadPing(clientDownloading, use_cache);
        }
    }

    private ClientDownloading initializeDownloadPing() {
        ClientDownloading clientDownloading = new ClientDownloading();
        clientDownloading.setCallBackDownloadInterface(new ClientDownloading.CallBackDownloadInterface() {
            @Override
            public void callBackDownloadedSuccess(String response) {
                if (downloadPingCallBack != null)
                    downloadPingCallBack.downloadPingSuccess(response);
            }

            @Override
            public void callBackDownloadedError(String error_message) {
                if (downloadPingCallBack != null)
                    downloadPingCallBack.downloadPingError(error_message);
            }
        });
        clientDownloading.setCallBackRequestInterface(new ClientDownloading.CallBackRequestInterface() {
            @Override
            public void callBackUrlRequest(String request) {
                if (requestPingCallBack != null)
                    requestPingCallBack.callBackUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                if (requestPingCallBack != null)
                    requestPingCallBack.callBackCurlRequest(request);
            }
        });
        return clientDownloading;
    }

    private void downloadPing(ClientDownloading clientDownloading, boolean use_cache) {
        clientDownloading.downloadPing(context, scheme, api_root, apiValues.getURL_PING_PATH(), application_id, x_access_token, x_test_ip, use_cache);
    }

    public interface DownloadPingCallBack {
        void downloadPingSuccess(String response);

        void downloadPingError(String message);
    }

    private DownloadPingCallBack downloadPingCallBack;

    public void setDownloadPingCallBack(DownloadPingCallBack downloadPingCallBack) {
        this.downloadPingCallBack = downloadPingCallBack;
    }
    //endregion

    //region Download session

    public void downloadSession() {
        if (api_root != null) {
            ClientDownloading clientDownloading = initializeDownloadSession();
            downloadSession(clientDownloading, use_cache);
        }
    }

    public void downloadSession(boolean use_cache) {
        if (api_root != null) {
            ClientDownloading clientDownloading = initializeDownloadSession();
            downloadSession(clientDownloading, use_cache);
        }
    }

    private ClientDownloading initializeDownloadSession() {
        ClientDownloading clientDownloading = new ClientDownloading();
        clientDownloading.setCallBackDownloadInterface(new ClientDownloading.CallBackDownloadInterface() {
            @Override
            public void callBackDownloadedSuccess(String response) {
                if (downloadSessionCallBack != null)
                    downloadSessionCallBack.downloadSessionSuccess(response);
            }

            @Override
            public void callBackDownloadedError(String error_message) {
                if (downloadSessionCallBack != null)
                    downloadSessionCallBack.downloadSessionError(error_message);
            }
        });
        clientDownloading.setCallBackRequestInterface(new ClientDownloading.CallBackRequestInterface() {
            @Override
            public void callBackUrlRequest(String request) {
                if (requestSession != null)
                    requestSession.callBackUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                if (requestSession != null)
                    requestSession.callBackCurlRequest(request);
            }
        });
        return clientDownloading;
    }

    private void downloadSession(ClientDownloading clientDownloading, boolean use_cache) {
        clientDownloading.downloadSession(context, scheme, api_root, apiValues.getURL_SESSION_PATH(), application_id, x_access_token, x_test_ip, use_cache);
    }

    public interface DownloadSessionCallBack {
        void downloadSessionSuccess(String response);

        void downloadSessionError(String message);
    }

    private DownloadSessionCallBack downloadSessionCallBack;

    public void setDownloadSessionCallBack(DownloadSessionCallBack downloadSessionCallBack) {
        this.downloadSessionCallBack = downloadSessionCallBack;
    }
    //endregion

    //region RequestCallBack
    public interface RequestCallBack {
        void callBackUrlRequest(String request);

        void callBackCurlRequest(String request);
    }

    private RequestCallBack requestBroadCastCallBack;
    private RequestCallBack requestPingCallBack;
    private RequestCallBack requestChannelList;
    private RequestCallBack requestSession;

    public void setRequestBroadCastCallBack(RequestCallBack requestBroadCastCallBack) {
        this.requestBroadCastCallBack = requestBroadCastCallBack;
    }

    public void setRequestPingCallBack(RequestCallBack requestPingCallBack) {
        this.requestPingCallBack = requestPingCallBack;
    }

    public void setRequestChannelList(RequestCallBack requestChannelList) {
        this.requestChannelList = requestChannelList;
    }

    public void setRequestSession(RequestCallBack requestSession) {
        this.requestSession = requestSession;
    }
    //endregion

    //get version name and code api client
    public static int getVersionCode(Context context) {
        return 14;
    }

    public static String getVersionName(Context context) {
        return "0.2.11";
    }
    //end region
}

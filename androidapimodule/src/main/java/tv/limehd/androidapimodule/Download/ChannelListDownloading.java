package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeCacheSettings;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.LimeUri;
import tv.limehd.androidapimodule.Values.ApiValues;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static tv.limehd.androidapimodule.LimeApiClient.convertMegaByteToByte;

public class ChannelListDownloading {

    private ApiValues apiValues;
    private Context context;
    private File cacheDir;

    public ChannelListDownloading() {
        initialization();
    }

    public ChannelListDownloading(Context context, File cacheDir) {
        initialization();
        this.context = context;
        this.cacheDir = cacheDir;
    }

    private void connectCacheInOkHttpClient(OkHttpClient.Builder okHttpClientBuilder) {
        if(cacheDir!=null) {
            Cache cache = new Cache(cacheDir, convertMegaByteToByte(2));
            okHttpClientBuilder.cache(cache);
        }
    }

    public void loadingRequestChannelList(final String scheme, final String api_root, final String endpoint_channels,
                                          String application_id, final String x_access_token, final String channel_group_id, final String locale, final String x_test_ip, final boolean use_cache) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LimeCurlBuilder.Builder limeCurlBuilder = new LimeCurlBuilder().setLogCurlInterface(new LimeCurlBuilder.LogCurlInterface() {
                    @Override
                    public void logCurl(String message) {
                        if (callBackRequestChannelListInterface != null) {
                            callBackRequestChannelListInterface.callBackCurlRequestChannelList(message);
                        }
                    }
                });
                connectCacheInOkHttpClient(limeCurlBuilder);

                OkHttpClient client = new OkHttpClient(limeCurlBuilder);
                Request.Builder builder = new Request.Builder()
                        .url(LimeUri.getUriChannelList(scheme, api_root, endpoint_channels, channel_group_id, locale))
                        .addHeader(apiValues.getACCEPT_KEY(), apiValues.getACCEPT_VALUE())
                        .addHeader(apiValues.getX_ACCESS_TOKEN_KEY(), x_access_token);
                if (x_test_ip != null)
                    builder.addHeader(apiValues.getX_TEXT_IP_KEY(), x_test_ip);
                if (use_cache) {
                    builder.cacheControl(new CacheControl.Builder().maxAge(tryGetMaxAge(), TimeUnit.SECONDS).build());
                } else {
                    builder.cacheControl(new CacheControl.Builder().noCache().build());
                }
                Request request = builder.build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        if (callBackDownloadChannelListInterface != null)
                            callBackDownloadChannelListInterface.callBackDownloadedChannelListError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            if (callBackDownloadChannelListInterface != null)
                                callBackDownloadChannelListInterface.callBackDownloadedChannelListError(("Unexpected code " + response));
                            throw new IOException("Unexpected code " + response);
                        }

                        if (isResponseFromNetwork(response)) {
                            int maxAge = LimeApiClient.getMaxCacheFromCacheControl(response);
                            trySaveMaxAge(maxAge);
                        }

                        if (callBackDownloadChannelListInterface != null)
                            callBackDownloadChannelListInterface.callBackDownloadedChannelListSuccess(response.body().string());
                    }
                });
            }
        }).start();
        if (callBackRequestChannelListInterface != null)
            callBackRequestChannelListInterface.callBackUrlRequestChannelList(LimeUri.getUriChannelList(scheme, api_root, endpoint_channels, channel_group_id, locale));
    }

    private void initialization() {
        apiValues = new ApiValues();
    }

    private boolean isResponseFromNetwork(Response response) {
        return response.networkResponse() != null;
    }

    private boolean trySaveMaxAge(int maxAge) {
        if (context != null) {
            LimeCacheSettings.setMaxAge(context, LimeCacheSettings.DOWNLOADER_CHANNEL_LIST, maxAge);
            return true;
        } else {
            return false;
        }
    }

    private int tryGetMaxAge() {
        if (context != null) {
            return LimeCacheSettings.getMaxAge(context, LimeCacheSettings.DOWNLOADER_CHANNEL_LIST);
        } else {
            return 0;
        }
    }

    public interface CallBackDownloadChannelListInterface {
        void callBackDownloadedChannelListSuccess(String response);

        void callBackDownloadedChannelListError(String error_message);
    }

    public interface CallBackRequestChannelListInterface {
        void callBackUrlRequestChannelList(String request);

        void callBackCurlRequestChannelList(String request);
    }

    private CallBackDownloadChannelListInterface callBackDownloadChannelListInterface;
    private CallBackRequestChannelListInterface callBackRequestChannelListInterface;

    public void setCallBackDownloadChannelListInterface(CallBackDownloadChannelListInterface callBackDownloadChannelListInterface) {
        this.callBackDownloadChannelListInterface = callBackDownloadChannelListInterface;
    }

    public void setCallBackRequestChannelListInterface(CallBackRequestChannelListInterface callBackRequestChannelListInterface) {
        this.callBackRequestChannelListInterface = callBackRequestChannelListInterface;
    }
}

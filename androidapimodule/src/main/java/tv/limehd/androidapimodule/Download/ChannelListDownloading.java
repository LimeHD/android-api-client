package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.limehd.androidapimodule.Interfaces.ListenerRequest;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.LimeUri;

import static tv.limehd.androidapimodule.LimeApiClient.convertMegaByteToByte;

public class ChannelListDownloading extends DownloadingBase {

    public ChannelListDownloading() {
        super();
    }

    public ChannelListDownloading(@NonNull Context context, File cacheDir) {
        super(context);
        this.cacheDir = cacheDir;
    }

    private void connectCacheInOkHttpClient(OkHttpClient.Builder okHttpClientBuilder) {
        if (cacheDir != null) {
            Cache cache = new Cache(cacheDir, convertMegaByteToByte(2));
            okHttpClientBuilder.cache(cache);
        }
    }

    public void loadingRequestChannelList(final String scheme, final String api_root, final String endpoint_channels,
                                          String application_id, final String x_access_token, final String channel_group_id, final String time_zone, final String locale, final String x_test_ip, final boolean use_cache) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LimeCurlBuilder.Builder limeCurlBuilder = createLimeCurlBuilder();
                connectCacheInOkHttpClient(limeCurlBuilder);

                OkHttpClient client = new OkHttpClient(limeCurlBuilder);
                Request.Builder builder = createRequestBuilder(x_access_token);
                try {
                    builder.url(LimeUri.getUriChannelList(scheme, api_root, endpoint_channels, channel_group_id, time_zone, locale));
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listenerRequest != null) {
                        listenerRequest.onError(e.getMessage());
                    }
                    return;
                }

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
                        if (listenerRequest != null)
                            listenerRequest.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            if (listenerRequest != null)
                                listenerRequest.onError(("Unexpected code " + response));
                            throw new IOException("Unexpected code " + response);
                        }

                        if (isResponseFromNetwork(response)) {
                            int maxAge = LimeApiClient.getMaxCacheFromCacheControl(response);
                            trySaveMaxAge(maxAge);
                        }

                        if (listenerRequest != null)
                            listenerRequest.onSuccess(response.body().string());
                    }
                });
            }
        }).start();
        if (callBackUrlCurlRequestInterface != null)
            callBackUrlCurlRequestInterface.callBackUrlRequest(LimeUri.getUriChannelList(scheme, api_root, endpoint_channels, channel_group_id, time_zone, locale));
    }

    private ListenerRequest listenerRequest;

    public void setListenerRequest(ListenerRequest listenerRequest) {
        this.listenerRequest = listenerRequest;
    }
}

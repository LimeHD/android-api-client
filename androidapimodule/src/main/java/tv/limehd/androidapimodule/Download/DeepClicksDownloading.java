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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.limehd.androidapimodule.Interfaces.CallBackUrlCurlRequestInterface;
import tv.limehd.androidapimodule.Interfaces.ListenerRequest;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeCacheSettings;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.LimeUri;
import tv.limehd.androidapimodule.Values.ApiValues;

import static tv.limehd.androidapimodule.LimeApiClient.convertMegaByteToByte;

public class DeepClicksDownloading extends DownloadingBase{

    public DeepClicksDownloading(@NonNull Context context, File cacheDir) {
        super(context);
        this.cacheDir = cacheDir;
    }

    private void connectCacheInOkHttpClient(OkHttpClient.Builder okHttpClientBuilder) {
        if (cacheDir != null) {
            Cache cache = new Cache(cacheDir, convertMegaByteToByte(2));
            okHttpClientBuilder.cache(cache);
        }
    }

    public void deepClicksSendRequest(final String scheme, final String api_root, final String endpoint_deepclicks,
                                      final String application_id, final String x_access_token, final String x_test_ip, final boolean use_cache, final String query, final String path, final String device_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LimeCurlBuilder.Builder limeCurlBuilder = new LimeCurlBuilder().setLogCurlInterface(new LimeCurlBuilder.LogCurlInterface() {
                    @Override
                    public void logCurl(String message) {
                        if (callBackUrlCurlRequestInterface != null)
                            callBackUrlCurlRequestInterface.callBackCurlRequest(message);
                    }
                });
                connectCacheInOkHttpClient(limeCurlBuilder);
                OkHttpClient client = new OkHttpClient(limeCurlBuilder);
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add(apiValues.getAPP_ID_KEY(), application_id);
                formBodyBuilder.add(apiValues.getQUERY_KEY(), query);
                formBodyBuilder.add(apiValues.getPATH_KEY(), path);
                formBodyBuilder.add(apiValues.getDEVICE_ID_KEY(), device_id);
                FormBody formBody = formBodyBuilder.build();
                Request.Builder builder = new Request.Builder()
                        .addHeader(apiValues.getACCEPT_KEY(), apiValues.getACCEPT_VALUE())
                        .addHeader(apiValues.getX_ACCESS_TOKEN_KEY(), x_access_token);
                try {
                    builder.url(LimeUri.getUriPing(scheme, api_root, endpoint_deepclicks));
                } catch (Exception e) {
                    e.printStackTrace();
                    if(listenerRequest != null) {
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
                builder.post(formBody);
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
                            if (listenerRequest != null) {
                                listenerRequest.onError(("Unexpected code " + response));
                            }
                            throw new IOException("Unexpected code " + response);
                        }

                        if (response.networkResponse() != null) {
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
            callBackUrlCurlRequestInterface.callBackUrlRequest(LimeUri.getUriDeepClicks(scheme, api_root, endpoint_deepclicks));
    }

    private boolean trySaveMaxAge(int maxAge) {
        if (context != null) {
            LimeCacheSettings.setMaxAge(context, LimeCacheSettings.SENDER_DEEPCLICKS, maxAge);
            return true;
        } else {
            return false;
        }
    }

    private int tryGetMaxAge() {
        if (context != null) {
            return LimeCacheSettings.getMaxAge(context, LimeCacheSettings.SENDER_DEEPCLICKS);
        } else {
            return 0;
        }
    }

    private ListenerRequest listenerRequest;
    private CallBackUrlCurlRequestInterface callBackUrlCurlRequestInterface;

    public void setListenerRequest(ListenerRequest listenerRequest) {
        this.listenerRequest = listenerRequest;
    }

    public void setCallBackUrlCurlRequestInterface(CallBackUrlCurlRequestInterface callBackUrlCurlRequestInterface) {
        this.callBackUrlCurlRequestInterface = callBackUrlCurlRequestInterface;
    }

}

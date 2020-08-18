package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.LimeUri;

public class BroadcastDownloading extends DownloadingBase {

    public BroadcastDownloading() {
        super();
    }

    public BroadcastDownloading(@NonNull Context context) {
        super(context, null);
    }

    public void loadingRequestBroadCast(final String scheme, final String api_root, final String endpoint_broadcast
            , final String channel_id, final String before_date, final String after_date, final String time_zone
            , String application_id, final String x_access_token, final String locale, final String x_test_ip, final boolean use_cache) {
        LimeCurlBuilder.Builder limeCurlBuilder = createLimeCurlBuilder();
        tryConnectCacheInOkHttpClient(limeCurlBuilder);
        OkHttpClient client = createOkHttpClient(limeCurlBuilder);

        Request.Builder builder = createRequestBuilder(x_access_token);

        try {
            builder.url(LimeUri.getUriBroadcast(scheme, api_root, endpoint_broadcast, channel_id
                    , before_date, after_date, time_zone, locale));
        } catch (Exception e) {
            e.printStackTrace();
            if (callBackDownloadBroadCastInterface != null) {
                callBackDownloadBroadCastInterface.callBackDownloadedBroadCastError(e.getMessage());
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
                if (callBackDownloadBroadCastInterface != null)
                    callBackDownloadBroadCastInterface.callBackDownloadedBroadCastError(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (callBackDownloadBroadCastInterface != null)
                        callBackDownloadBroadCastInterface.callBackDownloadedBroadCastError(("Unexpected code " + response));
                    throw new IOException("Unexpected code " + response);
                }

                if (isResponseFromNetwork(response)) {
                    int maxAge = LimeApiClient.getMaxCacheFromCacheControl(response);
                    trySaveMaxAge(maxAge);
                }

                if (callBackDownloadBroadCastInterface != null)
                    callBackDownloadBroadCastInterface.callBackDownloadedBroadCastSucces(response.body().string(), channel_id);
            }
        });

        if (callBackUrlCurlRequestInterface != null)
            callBackUrlCurlRequestInterface.callBackUrlRequest(LimeUri.getUriBroadcast(scheme, api_root, endpoint_broadcast, channel_id
                    , before_date, after_date, time_zone, locale));
    }

    public interface CallBackDownloadBroadCastInterface {
        void callBackDownloadedBroadCastSucces(String response, String channel_id);

        void callBackDownloadedBroadCastError(String error_message);
    }

    private CallBackDownloadBroadCastInterface callBackDownloadBroadCastInterface;

    public void setCallBackDownloadBroadCastInterface(CallBackDownloadBroadCastInterface callBackDownloadBroadCastInterface) {
        this.callBackDownloadBroadCastInterface = callBackDownloadBroadCastInterface;
    }
}

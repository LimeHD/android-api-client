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
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.LimeUri;

public class BroadcastDownloading extends DownloadingBase {

    private Component.DataBroadcast specificData;

    public BroadcastDownloading() {
        super();
    }

    @Override
    protected String getUriFromLimeUri() {
        return null;
    }

    private <T> T castObject(Class<T> clazz, Object object) {
        return (T) object;
    }

    public BroadcastDownloading(@NonNull Context context) {
        super(context, null);
    }

    public void loadingRequestBroadCast(DataForRequest dataForRequest) {
        dataBasic = dataForRequest.getComponent(Component.DataBasic.class);
        specificData = dataForRequest.getComponent(Component.DataBroadcast.class);

        LimeCurlBuilder.Builder limeCurlBuilder = createLimeCurlBuilder();
        tryConnectCacheInOkHttpClient(limeCurlBuilder);
        OkHttpClient client = createOkHttpClient(limeCurlBuilder);
        Request.Builder builder = createRequestBuilder(dataBasic.getxAccessToken());
        try {
            builder.url(LimeUri.getUriBroadcast(
                    dataBasic.getScheme(),
                    dataBasic.getApiRoot(),
                    dataBasic.getEndpoint(),
                    specificData.getChannelId(),
                    specificData.getBeforeDate(),
                    specificData.getAfterDate(),
                    specificData.getTimeZone(),
                    specificData.getLocale()));
        } catch (Exception e) {
            e.printStackTrace();
            if (callBackDownloadBroadCastInterface != null) {
                callBackDownloadBroadCastInterface.callBackDownloadedBroadCastError(e.getMessage());
            }
            return;
        }
        if (dataBasic.getxTestIp() != null)
            builder.addHeader(apiValues.getX_TEXT_IP_KEY(), dataBasic.getxTestIp());
        if (dataBasic.isUseCache()) {
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
                    callBackDownloadBroadCastInterface.callBackDownloadedBroadCastSucces(response.body().string(), specificData.getChannelId());
            }
        });

        if (callBackUrlCurlRequestInterface != null) {
            callBackUrlCurlRequestInterface.callBackUrlRequest(
                    LimeUri.getUriBroadcast(
                            dataBasic.getScheme(),
                            dataBasic.getApiRoot(),
                            dataBasic.getEndpoint(),
                            specificData.getChannelId(),
                            specificData.getBeforeDate(),
                            specificData.getAfterDate(),
                            specificData.getTimeZone(),
                            specificData.getLocale()));
        }
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

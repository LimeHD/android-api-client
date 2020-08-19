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
import tv.limehd.androidapimodule.Download.Data.ComplexResponse;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.Interfaces.CallBackUrlCurlRequestInterface;
import tv.limehd.androidapimodule.Interfaces.ListenerRequest;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeCacheSettings;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.Values.ApiValues;

import static tv.limehd.androidapimodule.LimeApiClient.convertMegaByteToByte;

public abstract class DownloadingBase<TComponent extends Component> {

    protected ApiValues apiValues;
    protected Context context;
    protected File cacheDir;
    protected CallBackUrlCurlRequestInterface callBackUrlCurlRequestInterface;
    protected Component.DataBasic dataBasic;
    protected ListenerRequest listenerRequest;
    private TComponent dataSpecific;
    private Class<? extends Component> typeDataSpecific;

    protected DownloadingBase() {
        initialization();
    }

    protected DownloadingBase(@NonNull Context context, File cacheDir) {
        this.context = context;
        this.cacheDir = cacheDir;
        initialization();
    }

    public void setCallBackUrlCurlRequestInterface(CallBackUrlCurlRequestInterface callBackRequestBroadCastInterface) {
        this.callBackUrlCurlRequestInterface = callBackRequestBroadCastInterface;
    }

    public void setListenerRequest(ListenerRequest listenerRequest) {
        this.listenerRequest = listenerRequest;
    }

    protected void sendRequest(DataForRequest dataForRequest, Class<? extends Component> typeDataSpecific) {
        this.typeDataSpecific = typeDataSpecific;
        dataBasic = initDataBasic(dataForRequest);
        dataSpecific = initDataSpecific(dataForRequest);
        LimeCurlBuilder.Builder limeCurlBuilder = createLimeCurlBuilder();
        tryConnectCacheInOkHttpClient(limeCurlBuilder);
        OkHttpClient client = createOkHttpClient(limeCurlBuilder);
        Request.Builder builder = createRequestBuilder(dataBasic.getxAccessToken());
        try {
            builder.url(getUriFromLimeUri(dataBasic, dataSpecific));
        } catch (Exception e) {
            e.printStackTrace();
            sendCallBackError(e.getMessage());
            return;
        }

        if (dataBasic.getxTestIp() != null)
            builder.addHeader(apiValues.getX_TEXT_IP_KEY(), dataBasic.getxTestIp());
        tryConnectCacheInRequestBuilder(builder);
        builder = connectFormBodyForPost(builder);
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                sendCallBackError(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    sendCallBackError(("Unexpected code " + response));
                    throw new IOException("Unexpected code " + response);
                }
                if (isResponseFromNetwork(response)) {
                    int maxAge = LimeApiClient.getMaxCacheFromCacheControl(response);
                    trySaveMaxAge(maxAge);
                }
                sendCallBackSuccess(response.body().string());
            }
        });
        sendCallBackUrlRequest(getUriFromLimeUri(dataBasic, dataSpecific));
    }

    private void initialization() {
        apiValues = new ApiValues();
    }

    private boolean isResponseFromNetwork(Response response) {
        return response.networkResponse() != null;
    }

    private int tryGetMaxAge() {
        if (context != null) {
            return LimeCacheSettings.getMaxAge(context, this.getClass());
        } else {
            return 0;
        }
    }

    private boolean trySaveMaxAge(int maxAge) {
        if (context != null) {
            LimeCacheSettings.setMaxAge(context, this.getClass(), maxAge);
            return true;
        } else {
            return false;
        }
    }

    private LimeCurlBuilder.Builder createLimeCurlBuilder() {
        return new LimeCurlBuilder().setLogCurlInterface(new LimeCurlBuilder.LogCurlInterface() {
            @Override
            public void logCurl(String message) {
                if (callBackUrlCurlRequestInterface != null)
                    callBackUrlCurlRequestInterface.callBackCurlRequest(message);
            }
        });
    }

    private Request.Builder createRequestBuilder(final String x_access_token) {
        return new Request.Builder()
                .addHeader(apiValues.getACCEPT_KEY(), apiValues.getACCEPT_VALUE())
                .addHeader(apiValues.getX_ACCESS_TOKEN_KEY(), x_access_token);
    }

    private <T extends OkHttpClient.Builder> OkHttpClient createOkHttpClient(@NonNull T builder) {
        return new OkHttpClient(builder);
    }

    private void tryConnectCacheInOkHttpClient(@NonNull OkHttpClient.Builder okHttpClientBuilder) {
        if (cacheDir != null) {
            Cache cache = new Cache(cacheDir, convertMegaByteToByte(2));
            okHttpClientBuilder.cache(cache);
        }
    }


    private void tryConnectCacheInRequestBuilder(Request.Builder builder) {
        if (dataBasic.isUseCache()) {
            builder.cacheControl(new CacheControl.Builder().maxAge(tryGetMaxAge(), TimeUnit.SECONDS).build());
        } else {
            builder.cacheControl(new CacheControl.Builder().noCache().build());
        }
    }

    private void sendCallBackUrlRequest(String request) {
        if (callBackUrlCurlRequestInterface != null) {
            callBackUrlCurlRequestInterface.callBackUrlRequest(request);
        }
    }

    private void sendCallBackCurlRequest(String request) {
        if (callBackUrlCurlRequestInterface != null) {
            callBackUrlCurlRequestInterface.callBackCurlRequest(request);
        }
    }

    private Component.DataBasic initDataBasic(DataForRequest dataForRequest) {
        return dataForRequest.getComponent(Component.DataBasic.class);
    }

    protected void sendCallBackError(@NonNull String error) {
        if (listenerRequest != null) {
            listenerRequest.onError(error);
        }
    }

    protected void sendCallBackSuccess(@NonNull String response) {
        if (listenerRequest != null)
            listenerRequest.onSuccess(new ComplexResponse(response));
    }

    protected TComponent getDataSpecific() {
        return dataSpecific;
    }

    protected Component.DataBasic getDataBasic() {
        return dataBasic;
    }

    protected TComponent initDataSpecific(DataForRequest dataForRequest) {
        return dataForRequest.getComponent(typeDataSpecific);
    }

    protected abstract String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific);

    protected abstract Request.Builder connectFormBodyForPost(Request.Builder builder);


}

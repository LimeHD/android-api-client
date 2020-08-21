package tv.limehd.androidapimodule.Download;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import tv.limehd.androidapimodule.Download.Data.ComplexResponse;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.Download.Modules.ControllerTrust;
import tv.limehd.androidapimodule.Interfaces.CallBackUrlCurlRequestInterface;
import tv.limehd.androidapimodule.Interfaces.ListenerRequest;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.Values.ApiValues;

import static tv.limehd.androidapimodule.LimeApiClient.convertMegaByteToByte;

public abstract class DownloadingBase<TComponent extends Component> {

    private ApiValues apiValues;
    private CallBackUrlCurlRequestInterface callBackUrlCurlRequestInterface;
    private Component.DataBasic dataBasic;
    private ListenerRequest listenerRequest;
    private TComponent dataSpecific;
    private Component.DataCache dataCache;

    protected DownloadingBase() {
        apiValues = new ApiValues();
    }

    public void setCallBackUrlCurlRequestInterface(CallBackUrlCurlRequestInterface callBackRequestBroadCastInterface) {
        this.callBackUrlCurlRequestInterface = callBackRequestBroadCastInterface;
    }

    public void setListenerRequest(ListenerRequest listenerRequest) {
        this.listenerRequest = listenerRequest;
    }

    public boolean isUseCache() {
        if (dataCache == null) return false;
        else
            return dataCache.isUseCache() && dataCache.getCacheDir() != null && dataCache.getContext() != null;
    }

    public ListenerRequest getListenerRequest() {
        return listenerRequest;
    }

    public ApiValues getApiValues() {
        return apiValues;
    }

    protected void sendRequest(DataForRequest dataForRequest, Class<? extends Component> typeDataSpecific) {
        dataBasic = dataForRequest.getComponent(Component.DataBasic.class);
        dataCache = dataForRequest.getComponent(Component.DataCache.class);
        dataSpecific = dataForRequest.getComponent(typeDataSpecific);

        Request.Builder builder = createRequestBuilder(dataBasic.getxAccessToken());
        try {
            builder.url(getUriFromLimeUri(dataBasic, dataSpecific));
        } catch (Exception e) {
            e.printStackTrace();
            sendCallBackError(getMessageExceptionFrom(e));
            return;
        }

        if (dataBasic.getxTestIp() != null)
            builder.addHeader(apiValues.getX_TEXT_IP_KEY(), dataBasic.getxTestIp());
        tryConnectCacheInRequestBuilder(builder);
        builder = connectFormBodyForPost(builder);
        Request request = builder.build();


        LimeCurlBuilder.Builder limeCurlBuilder = createLimeCurlBuilder();
        tryConnectCacheInOkHttpClient(limeCurlBuilder);
        limeCurlBuilder = new ControllerTrust(dataForRequest).connectSSLSocket(limeCurlBuilder);
        OkHttpClient client = createOkHttpClient(limeCurlBuilder);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                sendCallBackError(getMessageExceptionFrom(e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    sendCallBackError(("Unexpected code " + response));
                    throw new IOException("Unexpected code " + response);
                }
                if (isResponseFromNetwork(response)) {
                    if (isUseCache()) {
                        dataCache.saveMaxAgeCache(LimeApiClient.getMaxCacheFromCacheControl(response), DownloadingBase.this.getClass());
                    }
                }
                sendCallBackSuccess(getBodyTextFromResponse(response));
            }
        });
        sendCallBackUrlRequest(getUriFromLimeUri(dataBasic, dataSpecific));
    }

    private String getMessageExceptionFrom(@NonNull Exception e) {
        if (e.getMessage() != null)
            return e.getMessage();
        return "Exception: not Message";
    }

    private String getBodyTextFromResponse(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if(responseBody != null) {
            return responseBody.string();
        }
        else return "";
    }

    private boolean isResponseFromNetwork(Response response) {
        return response.networkResponse() != null;
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

    private void tryConnectCacheInOkHttpClient(@NonNull LimeCurlBuilder.Builder okHttpClientBuilder) {
        if (isUseCache()) {
            Cache cache = new Cache(dataCache.getCacheDir(), convertMegaByteToByte(2));
            okHttpClientBuilder.cache(cache);
        }
    }

    private void tryConnectCacheInRequestBuilder(Request.Builder builder) {
        if (isUseCache()) {
            builder.cacheControl(new CacheControl.Builder().maxAge(dataCache.getMaxAgeCache(this.getClass()), TimeUnit.SECONDS).build());
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

    protected abstract String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific);

    protected abstract Request.Builder connectFormBodyForPost(Request.Builder builder);


}

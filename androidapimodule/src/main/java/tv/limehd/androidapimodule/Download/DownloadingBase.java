package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Interfaces.CallBackUrlCurlRequestInterface;
import tv.limehd.androidapimodule.LimeCacheSettings;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.Values.ApiValues;

import static tv.limehd.androidapimodule.LimeApiClient.convertMegaByteToByte;

public abstract class DownloadingBase {

    protected ApiValues apiValues;
    protected Context context;
    protected File cacheDir;
    protected CallBackUrlCurlRequestInterface callBackUrlCurlRequestInterface;
    protected Component.DataBasic dataBasic;

    protected DownloadingBase(@NonNull Context context, File cacheDir) {
        this.context = context;
        this.cacheDir = cacheDir;
        initialization();
    }

    protected DownloadingBase() {
        initialization();
    }

    public void setCallBackUrlCurlRequestInterface(CallBackUrlCurlRequestInterface callBackRequestBroadCastInterface) {
        this.callBackUrlCurlRequestInterface = callBackRequestBroadCastInterface;
    }

    private void initialization() {
        apiValues = new ApiValues();
    }

    protected boolean isResponseFromNetwork(Response response) {
        return response.networkResponse() != null;
    }

    protected int tryGetMaxAge() {
        if (context != null) {
            return LimeCacheSettings.getMaxAge(context, this.getClass());
        } else {
            return 0;
        }
    }

    protected boolean trySaveMaxAge(int maxAge) {
        if (context != null) {
            LimeCacheSettings.setMaxAge(context, this.getClass(), maxAge);
            return true;
        } else {
            return false;
        }
    }

    protected LimeCurlBuilder.Builder createLimeCurlBuilder() {
        return new LimeCurlBuilder().setLogCurlInterface(new LimeCurlBuilder.LogCurlInterface() {
            @Override
            public void logCurl(String message) {
                if (callBackUrlCurlRequestInterface != null)
                    callBackUrlCurlRequestInterface.callBackCurlRequest(message);
            }
        });
    }

    protected Request.Builder createRequestBuilder(final String x_access_token) {
        return new Request.Builder()
                .addHeader(apiValues.getACCEPT_KEY(), apiValues.getACCEPT_VALUE())
                .addHeader(apiValues.getX_ACCESS_TOKEN_KEY(), x_access_token);
    }

    protected <T extends OkHttpClient.Builder> OkHttpClient createOkHttpClient(@NonNull T builder) {
        return new OkHttpClient(builder);
    }

    protected void tryConnectCacheInOkHttpClient(@NonNull OkHttpClient.Builder okHttpClientBuilder) {
        if (cacheDir != null) {
            Cache cache = new Cache(cacheDir, convertMegaByteToByte(2));
            okHttpClientBuilder.cache(cache);
        }
    }

    protected abstract String getUriFromLimeUri();
}

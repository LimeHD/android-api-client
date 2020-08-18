package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.Request;
import okhttp3.Response;
import tv.limehd.androidapimodule.Interfaces.CallBackUrlCurlRequestInterface;
import tv.limehd.androidapimodule.LimeCacheSettings;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.Values.ApiValues;

public class DownloadingBase {

    protected ApiValues apiValues;
    protected Context context;
    protected File cacheDir;
    protected CallBackUrlCurlRequestInterface callBackUrlCurlRequestInterface;

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
}

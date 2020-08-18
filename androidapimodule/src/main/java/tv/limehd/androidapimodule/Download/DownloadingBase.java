package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.Response;
import tv.limehd.androidapimodule.Interfaces.CallBackUrlCurlRequestInterface;
import tv.limehd.androidapimodule.LimeCacheSettings;
import tv.limehd.androidapimodule.Values.ApiValues;

public class DownloadingBase {

    protected ApiValues apiValues;
    protected Context context;
    protected File cacheDir;
    protected CallBackUrlCurlRequestInterface callBackUrlCurlRequestInterface;

    protected DownloadingBase(@NonNull Context context) {
        this.context = context;
        initialization();
    }

    protected DownloadingBase() {
        initialization();
    }

    private void setCallBackUrlRequestBroadCastInterface(CallBackUrlCurlRequestInterface callBackRequestBroadCastInterface) {
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
}

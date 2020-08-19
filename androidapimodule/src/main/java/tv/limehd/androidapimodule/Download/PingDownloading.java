package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.Interfaces.ListenerRequest;
import tv.limehd.androidapimodule.LimeUri;

public class PingDownloading extends DownloadingBase {

    private Component.DataPing dataSpecific;
    private ListenerRequest listenerRequest;

    public PingDownloading() {
        super();
    }

    public PingDownloading(@NonNull Context context, @NonNull File cacheDir) {
        super(context, cacheDir);
    }

    public void sendRequestPing(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest);
    }

    public void setListenerRequest(ListenerRequest listenerRequest) {
        this.listenerRequest = listenerRequest;
    }

    @Override
    protected String getUriFromLimeUri() {
        return LimeUri.getUriPing(
                dataBasic.getScheme(),
                dataBasic.getApiRoot(),
                dataBasic.getEndpoint());
    }

    @Override
    protected void initDataSpecific(DataForRequest dataForRequest) {
        dataSpecific = dataForRequest.getComponent(Component.DataPing.class);
    }

    @Override
    protected void sendCallBackError(@NonNull String error) {
        if (listenerRequest != null) {
            listenerRequest.onError(error);
        }
    }

    @Override
    protected void sendCallBackSuccess(@NonNull String response) {
        if (listenerRequest != null) {
            listenerRequest.onSuccess(response);
        }
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }
}

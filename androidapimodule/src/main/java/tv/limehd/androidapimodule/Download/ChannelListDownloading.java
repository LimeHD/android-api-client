package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.ComplexResponse;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class ChannelListDownloading extends DownloadingBase {

    private Component.DataChannelList dataSpecific;

    public ChannelListDownloading() {
        super();
    }

    public ChannelListDownloading(@NonNull Context context, @NonNull File cacheDir) {
        super(context, cacheDir);
    }

    public void sendRequestChannelList(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest);
    }

    @Override
    protected Component initDataSpecific(DataForRequest dataForRequest) {
        dataSpecific = dataForRequest.getComponent(Component.DataChannelList.class);
        return dataSpecific;
    }

    @Override
    protected void sendCallBackError(@NonNull String error) {
        if (listenerRequest != null)
            listenerRequest.onError(error);
    }

    @Override
    protected void sendCallBackSuccess(@NonNull String response) {
        if (listenerRequest != null)
            listenerRequest.onSuccess(new ComplexResponse(response));
    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriChannelList(dataBasic, (Component.DataChannelList) dataSpecific);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }
}

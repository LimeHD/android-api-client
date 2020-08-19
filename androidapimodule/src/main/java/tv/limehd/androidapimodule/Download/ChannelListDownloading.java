package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.Interfaces.ListenerRequest;
import tv.limehd.androidapimodule.LimeUri;

public class ChannelListDownloading extends DownloadingBase {

    private Component.DataChannelList dataSpecific;
    private ListenerRequest listenerRequest;

    public void setListenerRequest(ListenerRequest listenerRequest) {
        this.listenerRequest = listenerRequest;
    }

    public ChannelListDownloading() {
        super();
    }

    @Override
    protected void sendCallBackError(@NonNull String error) {
        if (listenerRequest != null)
            listenerRequest.onError(error);
    }

    @Override
    protected void sendCallBackSuccess(@NonNull String response) {
        if (listenerRequest != null)
            listenerRequest.onSuccess(response);
    }

    @Override
    protected String getUriFromLimeUri() {
        return LimeUri.getUriChannelList(
                dataBasic.getScheme(),
                dataBasic.getApiRoot(),
                dataBasic.getEndpoint(),
                dataSpecific.getChannelGroupId(),
                dataSpecific.getTimeZone(),
                dataSpecific.getLocale());
    }

    @Override
    protected void initDataSpecific(DataForRequest dataForRequest) {
        dataSpecific = dataForRequest.getComponent(Component.DataChannelList.class);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }

    public ChannelListDownloading(@NonNull Context context, @NonNull File cacheDir) {
        super(context, cacheDir);
    }

    public void loadingRequestChannelList(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest);
    }
}

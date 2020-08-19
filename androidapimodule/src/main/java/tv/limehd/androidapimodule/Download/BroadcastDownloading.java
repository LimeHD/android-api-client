package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.ComplexResponse;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class BroadcastDownloading extends DownloadingBase<Component.DataBroadcast> {

    public BroadcastDownloading() {
        super();
    }

    public BroadcastDownloading(@NonNull Context context) {
        super(context, null);
    }

    public void sendRequestBroadCast(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest, Component.DataBroadcast.class);
    }

    @Override
    protected void sendCallBackSuccess(@NonNull String response) {
        if (listenerRequest != null && getDataSpecific() != null)
            listenerRequest.onSuccess(new ComplexResponse(response).setChannelId(getDataSpecific().getChannelId()));
    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriBroadcast(dataBasic, dataSpecific);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }
}
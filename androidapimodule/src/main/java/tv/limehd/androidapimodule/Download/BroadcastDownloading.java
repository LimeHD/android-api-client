package tv.limehd.androidapimodule.Download;

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

    public void sendRequestBroadCast(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest, Component.DataBroadcast.class);
    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriBroadcast(dataBasic, dataSpecific);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }

    @Override
    protected void sendCallBackSuccess(@NonNull String response) {
        if (getListenerRequest() != null && getDataSpecific() != null)
            getListenerRequest().onSuccess(new ComplexResponse(response).setChannelId(getDataSpecific().getChannelId()));
    }
}
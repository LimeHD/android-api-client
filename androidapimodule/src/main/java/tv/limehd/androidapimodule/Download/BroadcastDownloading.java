package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class BroadcastDownloading extends DownloadingBase {

    private Component.DataBroadcast dataSpecific;
    private ListenerRequestBroadcast listenerRequestBroadcast;

    public BroadcastDownloading() {
        super();
    }

    public BroadcastDownloading(@NonNull Context context) {
        super(context, null);
    }

    public void sendRequestBroadCast(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest);
    }

    public void setListenerRequestBroadcast(ListenerRequestBroadcast listenerRequestBroadcast) {
        this.listenerRequestBroadcast = listenerRequestBroadcast;
    }

    @Override
    protected Component initDataSpecific(DataForRequest dataForRequest) {
        dataSpecific = dataForRequest.getComponent(Component.DataBroadcast.class);
        return dataSpecific;
    }

    @Override
    protected void sendCallBackError(String error) {
        if (listenerRequestBroadcast != null) {
            listenerRequestBroadcast.onError(error);
        }
    }

    @Override
    protected void sendCallBackSuccess(@NonNull String response) {
        if (listenerRequestBroadcast != null && dataSpecific != null)
            listenerRequestBroadcast.onSuccess(response, dataSpecific.getChannelId());
    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriBroadcast(dataBasic, (Component.DataBroadcast) dataSpecific);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }

    public interface ListenerRequestBroadcast {
        void onSuccess(String response, String channel_id);

        void onError(String error_message);
    }
}



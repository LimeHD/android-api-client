package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.Interfaces.ListenerRequest;
import tv.limehd.androidapimodule.LimeUri;

public class SessionDownload extends DownloadingBase {

    private Component.DataSession dataSpecific;
    private ListenerRequest listenerRequest;

    public void setListenerRequest(ListenerRequest listenerRequest) {
        this.listenerRequest = listenerRequest;
    }

    public SessionDownload() {
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
        return LimeUri.getUriSession(
                dataBasic.getScheme(),
                dataBasic.getApiRoot(),
                dataBasic.getEndpoint());
    }

    @Override
    protected void initDataSpecific(DataForRequest dataForRequest) {
        dataSpecific = dataForRequest.getComponent(Component.DataSession.class);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(apiValues.getAPP_ID_KEY(), dataBasic.getApplicationId());
        FormBody formBody = formBodyBuilder.build();
        builder.post(formBody);
        return builder;
    }

    public SessionDownload(@NonNull Context context, @NonNull File cacheDir) {
        super(context, cacheDir);
    }

    public void sessionDownloadRequest(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest);
    }
}

package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class SessionDownload extends DownloadingBase<Component.DataSession> {

    public SessionDownload() {
        super();
    }

    public SessionDownload(@NonNull Context context, @NonNull File cacheDir) {
        super(context, cacheDir);
    }

    public void sendRequestSession(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest, Component.DataSession.class);
    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriSession(dataBasic, dataSpecific);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(apiValues.getAPP_ID_KEY(), getDataBasic().getApplicationId());
        FormBody formBody = formBodyBuilder.build();
        builder.post(formBody);
        return builder;
    }
}
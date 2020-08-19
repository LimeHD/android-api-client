package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class DeepClicksDownloading extends DownloadingBase<Component.DataDeepClick> {

    public DeepClicksDownloading(@NonNull Context context, @NonNull File cacheDir) {
        super(context, cacheDir);
    }

    public void sendRequestDeepClicks(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest, Component.DataDeepClick.class);
    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriDeepClicks(dataBasic, dataSpecific);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(apiValues.getAPP_ID_KEY(), getDataBasic().getApplicationId());
        formBodyBuilder.add(apiValues.getQUERY_KEY(), getDataSpecific().getQuery());
        formBodyBuilder.add(apiValues.getPATH_KEY(), getDataSpecific().getPath());
        formBodyBuilder.add(apiValues.getDEVICE_ID_KEY(), getDataSpecific().getDeviceId());
        FormBody formBody = formBodyBuilder.build();
        return builder.post(formBody);
    }
}
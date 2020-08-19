package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.ComplexResponse;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class DeepClicksDownloading extends DownloadingBase {

    private Component.DataDeepClick dataSpecific;

    public DeepClicksDownloading(@NonNull Context context, @NonNull File cacheDir) {
        super(context, cacheDir);
    }

    public void sendRequestDeepClicks(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest);
    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriDeepClicks(dataBasic, dataSpecific);
    }

    @Override
    protected Component initDataSpecific(DataForRequest dataForRequest) {
        dataSpecific = dataForRequest.getComponent(Component.DataDeepClick.class);
        return dataSpecific;
    }

    @Override
    protected void sendCallBackSuccess(@NonNull String response) {
        if (listenerRequest != null)
            listenerRequest.onSuccess(new ComplexResponse(response));
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add(apiValues.getAPP_ID_KEY(), dataBasic.getApplicationId());
        formBodyBuilder.add(apiValues.getQUERY_KEY(), dataSpecific.getQuery());
        formBodyBuilder.add(apiValues.getPATH_KEY(), dataSpecific.getPath());
        formBodyBuilder.add(apiValues.getDEVICE_ID_KEY(), dataSpecific.getDeviceId());
        FormBody formBody = formBodyBuilder.build();
        return builder.post(formBody);
    }
}

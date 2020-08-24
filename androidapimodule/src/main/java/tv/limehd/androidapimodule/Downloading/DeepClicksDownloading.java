package tv.limehd.androidapimodule.Downloading;

import okhttp3.FormBody;
import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.Download.DownloadingBase;
import tv.limehd.androidapimodule.LimeUri;

public class DeepClicksDownloading extends DownloadingBase<Component.DataDeepClick> {

    public DeepClicksDownloading() {
        super();
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
        formBodyBuilder.add(getApiValues().getAPP_ID_KEY(), getDataBasic().getApplicationId());
        formBodyBuilder.add(getApiValues().getQUERY_KEY(), getDataSpecific().getQuery());
        formBodyBuilder.add(getApiValues().getPATH_KEY(), getDataSpecific().getPath());
        formBodyBuilder.add(getApiValues().getDEVICE_ID_KEY(), getDataSpecific().getDeviceId());
        FormBody formBody = formBodyBuilder.build();
        return builder.post(formBody);
    }
}
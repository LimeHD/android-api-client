package tv.limehd.androidapimodule.Downloading;

import okhttp3.FormBody;
import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.Download.DownloadingBase;
import tv.limehd.androidapimodule.LimeUri;

public class SessionDownloading extends DownloadingBase<Component.DataSession> {

    public SessionDownloading() {
        super();
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
        formBodyBuilder.add(getApiValues().getAPP_ID_KEY(), getDataBasic().getApplicationId());
        FormBody formBody = formBodyBuilder.build();
        builder.post(formBody);
        return builder;
    }
}
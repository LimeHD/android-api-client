package tv.limehd.androidapimodule.Download;

import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class PingDownloading extends DownloadingBase<Component.DataPing> {

    public PingDownloading() {
        super();
    }

    public void sendRequestPing(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest, Component.DataPing.class);
    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriPing(dataBasic, dataSpecific);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }
}
package tv.limehd.androidapimodule.Download;

import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class ChannelListDownloading extends DownloadingBase<Component.DataChannelList> {

    public ChannelListDownloading() {
        super();
    }

    public void sendRequestChannelList(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest, Component.DataChannelList.class);

    }

    @Override
    protected String getUriFromLimeUri(Component.DataBasic dataBasic, Component dataSpecific) {
        return LimeUri.getUriChannelList(dataBasic, dataSpecific);
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }
}
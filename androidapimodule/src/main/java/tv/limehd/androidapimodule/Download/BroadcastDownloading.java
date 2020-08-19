package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import okhttp3.Request;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.LimeUri;

public class BroadcastDownloading extends DownloadingBase {

    private Component.DataBroadcast dataSpecific;
    private CallBackDownloadBroadCastInterface callBackDownloadBroadCastInterface;

    public void setCallBackDownloadBroadCastInterface(CallBackDownloadBroadCastInterface callBackDownloadBroadCastInterface) {
        this.callBackDownloadBroadCastInterface = callBackDownloadBroadCastInterface;
    }

    public BroadcastDownloading() {
        super();
    }

    @Override
    protected void initDataSpecific(DataForRequest dataForRequest) {
        dataSpecific = dataForRequest.getComponent(Component.DataBroadcast.class);
    }

    @Override
    protected void sendCallBackError(String error) {
        if (callBackDownloadBroadCastInterface != null) {
            callBackDownloadBroadCastInterface.callBackDownloadedBroadCastError(error);
        }
    }

    @Override
    protected void sendCallBackSuccess(@NonNull String response) {
        if (callBackDownloadBroadCastInterface != null && dataSpecific != null)
            callBackDownloadBroadCastInterface.callBackDownloadedBroadCastSucces(response, dataSpecific.getChannelId());
    }

    @Override
    protected String getUriFromLimeUri() {
        return LimeUri.getUriBroadcast(
                dataBasic.getScheme(),
                dataBasic.getApiRoot(),
                dataBasic.getEndpoint(),
                dataSpecific.getChannelId(),
                dataSpecific.getBeforeDate(),
                dataSpecific.getAfterDate(),
                dataSpecific.getTimeZone(),
                dataSpecific.getLocale());
    }

    @Override
    protected Request.Builder connectFormBodyForPost(Request.Builder builder) {
        return builder;
    }

    private <T> T castObject(Class<T> clazz, Object object) {
        return (T) object;
    }

    public BroadcastDownloading(@NonNull Context context) {
        super(context, null);
    }

    public void loadingRequestBroadCast(DataForRequest dataForRequest) {
        super.sendRequest(dataForRequest);
    }

    public interface CallBackDownloadBroadCastInterface {
        void callBackDownloadedBroadCastSucces(String response, String channel_id);

        void callBackDownloadedBroadCastError(String error_message);
    }
}



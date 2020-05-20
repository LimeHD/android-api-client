package tv.limehd.androidapimodule.Download;

import java.io.IOException;

import androidx.annotation.NonNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.LimeUri;
import tv.limehd.androidapimodule.Values.ApiValues;

public class BroadcastDownloading {

    private ApiValues apiValues;

    public BroadcastDownloading() {
        apiValues = new ApiValues();
    }

    public void loadingRequestBroadCast(final String scheme, final String api_root, final String endpoint_broadcast
            , final String channel_id, final String before_date, final String after_date, final String time_zone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LimeCurlBuilder.Builder limeCurlBuilder = new LimeCurlBuilder().setLogCurlInterface(new LimeCurlBuilder.LogCurlInterface() {
                    @Override
                    public void logCurl(String message) {
                        if (callBackRequestBroadCastInterface != null)
                            callBackRequestBroadCastInterface.callBackCUrlRequestBroadCast(message);
                    }
                });
                OkHttpClient client = new OkHttpClient(limeCurlBuilder);
                Request request = new Request.Builder()
                        .url(LimeUri.getUriBroadcast(scheme, api_root, endpoint_broadcast, channel_id
                                , before_date, after_date, time_zone))
                        .addHeader(apiValues.getACCEPT_KEY(), apiValues.getACCEPT_VALUE()).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        if (callBackDownloadBroadCastInterface != null)
                            callBackDownloadBroadCastInterface.callBackDownloadedBroadCastError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        if (callBackDownloadBroadCastInterface != null)
                            callBackDownloadBroadCastInterface.callBackDownloadedBroadCastSucces(response.body().string());
                    }
                });
            }
        }).start();
        if (callBackRequestBroadCastInterface != null)
            callBackRequestBroadCastInterface.callBackUrlRequestBroadCast(LimeUri.getUriBroadcast(scheme, api_root, endpoint_broadcast, channel_id
                    , before_date, after_date, time_zone));
    }


    public interface CallBackDownloadBroadCastInterface {
        void callBackDownloadedBroadCastSucces(String response);

        void callBackDownloadedBroadCastError(String error_message);
    }

    public interface CallBackRequestBroadCastInterface {
        void callBackUrlRequestBroadCast(String request);

        void callBackCUrlRequestBroadCast(String request);
    }

    private CallBackDownloadBroadCastInterface callBackDownloadBroadCastInterface;
    private CallBackRequestBroadCastInterface callBackRequestBroadCastInterface;

    public void setCallBackDownloadBroadCastInterface(CallBackDownloadBroadCastInterface callBackDownloadBroadCastInterface) {
        this.callBackDownloadBroadCastInterface = callBackDownloadBroadCastInterface;
    }

    public void setCallBackUrlRequestBroadCastInterface(CallBackRequestBroadCastInterface callBackRequestBroadCastInterface) {
        this.callBackRequestBroadCastInterface = callBackRequestBroadCastInterface;
    }
}

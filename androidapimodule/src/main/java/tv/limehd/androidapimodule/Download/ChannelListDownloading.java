package tv.limehd.androidapimodule.Download;

import androidx.annotation.NonNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.limehd.androidapimodule.LimeCurlBuilder;
import tv.limehd.androidapimodule.LimeUri;
import tv.limehd.androidapimodule.Values.ApiValues;

import java.io.IOException;

public class ChannelListDownloading {

    private ApiValues apiValues;

    public ChannelListDownloading() {
        apiValues = new ApiValues();
    }

    public void loadingRequestChannelList(final String scheme, final String api_root, final String endpoint_channels) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LimeCurlBuilder.Builder limeCurlBuilder = new LimeCurlBuilder().setLogCurlInterface(new LimeCurlBuilder.LogCurlInterface() {
                    @Override
                    public void logCurl(String message) {
                        if (callBackRequestChannelListInterface != null)
                            callBackRequestChannelListInterface.callBackCurlRequestChannelList(message);
                    }
                });
                OkHttpClient client = new OkHttpClient(limeCurlBuilder);
                Request request = new Request.Builder()
                        .url(LimeUri.getUriChannelList(scheme, api_root, endpoint_channels))
                        .addHeader(apiValues.getACCEPT_KEY(), apiValues.getACCEPT_VALUE()).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        if (callBackDownloadChannelListInterface != null)
                            callBackDownloadChannelListInterface.callBackDownloadedChannelListError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        if (callBackDownloadChannelListInterface != null)
                            callBackDownloadChannelListInterface.callBackDownloadedChannelListSuccess(response.body().string());
                    }
                });
            }
        }).start();
        if (callBackRequestChannelListInterface != null)
            callBackRequestChannelListInterface.callBackUrlRequestChannelList(LimeUri.getUriChannelList(scheme, api_root, endpoint_channels));
    }

    public interface CallBackDownloadChannelListInterface {
        void callBackDownloadedChannelListSuccess(String response);

        void callBackDownloadedChannelListError(String error_message);
    }

    public interface CallBackRequestChannelListInterface {
        void callBackUrlRequestChannelList(String request);

        void callBackCurlRequestChannelList(String request);
    }

    private CallBackDownloadChannelListInterface callBackDownloadChannelListInterface;
    private CallBackRequestChannelListInterface callBackRequestChannelListInterface;

    public void setCallBackDownloadChannelListInterface(CallBackDownloadChannelListInterface callBackDownloadChannelListInterface) {
        this.callBackDownloadChannelListInterface = callBackDownloadChannelListInterface;
    }

    public void setCallBackRequestChannelListInterface(CallBackRequestChannelListInterface callBackRequestChannelListInterface) {
        this.callBackRequestChannelListInterface = callBackRequestChannelListInterface;
    }
}

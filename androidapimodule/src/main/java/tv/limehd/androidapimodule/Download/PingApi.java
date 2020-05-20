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

public class PingApi {
    private ApiValues apiValues;

    public PingApi() {
        apiValues = new ApiValues();
    }

    public void pingApiRequest(final String scheme, final String api_root, final String endpoint_ping) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LimeCurlBuilder.Builder limeCurlBuilder = new LimeCurlBuilder().setLogCurlInterface(new LimeCurlBuilder.LogCurlInterface() {
                    @Override
                    public void logCurl(String message) {
                        if (callBackPingRequestInterface != null)
                            callBackPingRequestInterface.callBackCurlRequest(message);
                    }
                });
                OkHttpClient client = new OkHttpClient(limeCurlBuilder);
                Request request = new Request.Builder()
                        .url(LimeUri.getUriChannelList(scheme, api_root, endpoint_ping))
                        .addHeader(apiValues.getACCEPT_KEY(), apiValues.getACCEPT_VALUE()).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        if (callBackPingInterface != null)
                            callBackPingInterface.callBackError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        if (callBackPingInterface != null)
                            callBackPingInterface.callBackSuccess(response.body().string());
                    }
                });
            }
        }).start();
        if (callBackPingRequestInterface != null)
            callBackPingRequestInterface.callBackUrlRequest(LimeUri.getUriChannelList(scheme, api_root, endpoint_ping));
    }

    public interface CallBackPingInterface {
        void callBackSuccess(String response);

        void callBackError(String message);
    }

    public interface CallBackPingRequestInterface {
        void callBackUrlRequest(String request);
        void callBackCurlRequest(String request);
    }

    private CallBackPingInterface callBackPingInterface;
    private CallBackPingRequestInterface callBackPingRequestInterface;

    public void setCallBackPingInterface(CallBackPingInterface callBackPingInterface) {
        this.callBackPingInterface = callBackPingInterface;
    }

    public void setCallBackPingRequestInterface(CallBackPingRequestInterface callBackPingRequestInterface) {
        this.callBackPingRequestInterface = callBackPingRequestInterface;
    }
}

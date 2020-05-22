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

public class SessionDownload {
    private ApiValues apiValues;

    public SessionDownload() {
        apiValues = new ApiValues();
    }

    public void sessionDownloadRequest(final String scheme, final String api_root, final String endpoint_session, String application_id, final String x_access_token) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LimeCurlBuilder.Builder limeCurlBuilder = new LimeCurlBuilder().setLogCurlInterface(new LimeCurlBuilder.LogCurlInterface() {
                    @Override
                    public void logCurl(String message) {
                        if (callBackSessionRequestInterface != null)
                            callBackSessionRequestInterface.callBackCurlRequest(message);
                    }
                });
                OkHttpClient client = new OkHttpClient(limeCurlBuilder);
                Request request = new Request.Builder()
                        .url(LimeUri.getUriSession(scheme, api_root, endpoint_session))
                        .addHeader(apiValues.getACCEPT_KEY(), apiValues.getACCEPT_VALUE()).addHeader(apiValues.getX_ACCESS_TOKEN_KEY(), x_access_token).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        if (callBackSessionInterface != null)
                            callBackSessionInterface.callBackError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        if (callBackSessionInterface != null)
                            callBackSessionInterface.callBackSuccess(response.body().string());
                    }
                });
            }
        }).start();
        if (callBackSessionRequestInterface != null)
            callBackSessionRequestInterface.callBackUrlRequest(LimeUri.getUriSession(scheme, api_root, endpoint_session));
    }

    public interface CallBackSessionInterface {
        void callBackSuccess(String response);

        void callBackError(String message);
    }

    public interface CallBackSessionRequestInterface {
        void callBackUrlRequest(String request);
        void callBackCurlRequest(String request);
    }

    private CallBackSessionInterface callBackSessionInterface;
    private CallBackSessionRequestInterface callBackSessionRequestInterface;

    public void setCallBackSessionInterface(CallBackSessionInterface callBackSessionInterface) {
        this.callBackSessionInterface = callBackSessionInterface;
    }

    public void setCallBackSessionRequestInterface(CallBackSessionRequestInterface callBackSessionRequestInterface) {
        this.callBackSessionRequestInterface = callBackSessionRequestInterface;
    }
}

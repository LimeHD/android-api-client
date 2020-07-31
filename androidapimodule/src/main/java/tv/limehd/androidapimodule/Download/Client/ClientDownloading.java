package tv.limehd.androidapimodule.Download.Client;

import android.content.Context;

import java.io.File;

import tv.limehd.androidapimodule.Download.BroadcastDownloading;
import tv.limehd.androidapimodule.Download.ChannelListDownloading;
import tv.limehd.androidapimodule.Download.DeepClicksDownloading;
import tv.limehd.androidapimodule.Download.PingDownloading;
import tv.limehd.androidapimodule.Download.SessionDownload;

public class ClientDownloading {

    public ClientDownloading() {
    }

    public void downloadChannelList(Context context, File cacheDir, String scheme, String api_root, String endpoint_channels, String application_id
            , String x_access_token, String channel_group_id, String locale, String x_test_ip, boolean use_cache) {
        ChannelListDownloading channelListDownloading = new ChannelListDownloading(context, cacheDir);
        channelListDownloading.setCallBackDownloadChannelListInterface(new ChannelListDownloading.CallBackDownloadChannelListInterface() {
            @Override
            public void callBackDownloadedChannelListSuccess(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void callBackDownloadedChannelListError(String error_message) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedError(error_message);
            }
        });
        channelListDownloading.setCallBackRequestChannelListInterface(new ChannelListDownloading.CallBackRequestChannelListInterface() {
            @Override
            public void callBackUrlRequestChannelList(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackUrlRequest(request);
            }

            @Override
            public void callBackCurlRequestChannelList(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackCurlRequest(request);
            }
        });
        channelListDownloading.loadingRequestChannelList(scheme, api_root, endpoint_channels, application_id
                , x_access_token, channel_group_id, locale, x_test_ip, use_cache);
    }

    public void downloadBroadCast(Context context, String scheme, String api_root, String endpoint_broadcast
            , String channel_id, String before_date, String after_date, String time_zone
            , String application_id, String x_access_token, String locale, String x_test_ip, boolean use_cache) {
        BroadcastDownloading broadcastDownloading = new BroadcastDownloading(context);
        broadcastDownloading.setCallBackDownloadBroadCastInterface(new BroadcastDownloading.CallBackDownloadBroadCastInterface() {
            @Override
            public void callBackDownloadedBroadCastSucces(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void callBackDownloadedBroadCastError(String error_message) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedError(error_message);
            }
        });
        broadcastDownloading.setCallBackUrlRequestBroadCastInterface(new BroadcastDownloading.CallBackRequestBroadCastInterface() {
            @Override
            public void callBackUrlRequestBroadCast(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackUrlRequest(request);
            }

            @Override
            public void callBackCUrlRequestBroadCast(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackCurlRequest(request);
            }
        });
        broadcastDownloading.loadingRequestBroadCast(scheme, api_root, endpoint_broadcast, channel_id, before_date, after_date, time_zone
                , application_id, x_access_token, locale, x_test_ip, use_cache);
    }

    public void downloadPing(Context context, File cacheDir, String scheme, String api_root, String endpoint_ping, String application_id, String x_access_token, String x_test_ip, boolean use_cache) {
        PingDownloading pingDownloading = new PingDownloading(context, cacheDir);
        pingDownloading.setCallBackPingInterface(new PingDownloading.CallBackPingInterface() {
            @Override
            public void callBackSuccess(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void callBackError(String message) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedError(message);
            }
        });
        pingDownloading.setCallBackPingRequestInterface(new PingDownloading.CallBackPingRequestInterface() {
            @Override
            public void callBackUrlRequest(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackCurlRequest(request);
            }
        });
        pingDownloading.pingDownloadRequest(scheme, api_root, endpoint_ping, application_id, x_access_token, x_test_ip, use_cache);
    }

    public void downloadSession(Context context, File cacheDir, String scheme, String api_root, String endpoint_session
            , String application_id, String x_access_token, String x_test_ip, boolean use_cache) {
        SessionDownload sessionDownload = new SessionDownload(context, cacheDir);
        sessionDownload.setCallBackSessionInterface(new SessionDownload.CallBackSessionInterface() {
            @Override
            public void callBackSuccess(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void callBackError(String message) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedError(message);
            }
        });
        sessionDownload.setCallBackSessionRequestInterface(new SessionDownload.CallBackSessionRequestInterface() {
            @Override
            public void callBackUrlRequest(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackCurlRequest(request);
            }
        });
        sessionDownload.sessionDownloadRequest(scheme, api_root, endpoint_session, application_id, x_access_token, x_test_ip, use_cache);
    }

    public void sendingDeepClicks(Context context, File cacheDir, String scheme, String api_root, String endpoint_deepclicks,
                                  String application_id, String x_access_token, String x_test_ip, boolean use_cache, String query, String path, String device_id) {
        DeepClicksDownloading deepClicksDownloading = new DeepClicksDownloading(context, cacheDir);
        deepClicksDownloading.setCallBackDeepClicksInterface(new DeepClicksDownloading.CallBackDeepClicksInterface() {
            @Override
            public void callBackSuccess(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void callBackError(String message) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(message);
            }
        });
        deepClicksDownloading.setCallBackDeepClicksRequestInterface(new DeepClicksDownloading.CallBackDeepClicksRequestInterface() {
            @Override
            public void callBackUrlRequest(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                if (callBackRequestInterface != null)
                    callBackRequestInterface.callBackCurlRequest(request);
            }
        });
        deepClicksDownloading.deepClicksSendRequest(scheme, api_root, endpoint_deepclicks,
                application_id, x_access_token, x_test_ip, use_cache, query, path, device_id);
    }

    public interface CallBackDownloadInterface {
        void callBackDownloadedSuccess(String response);

        void callBackDownloadedError(String error_message);
    }

    public interface CallBackRequestInterface {
        void callBackUrlRequest(String request);

        void callBackCurlRequest(String request);
    }


    private CallBackDownloadInterface callBackDownloadInterface;
    private CallBackRequestInterface callBackRequestInterface;

    public void setCallBackDownloadInterface(CallBackDownloadInterface callBackDownloadInterface) {
        this.callBackDownloadInterface = callBackDownloadInterface;
    }

    public void setCallBackRequestInterface(CallBackRequestInterface callBackRequestInterface) {
        this.callBackRequestInterface = callBackRequestInterface;
    }
}

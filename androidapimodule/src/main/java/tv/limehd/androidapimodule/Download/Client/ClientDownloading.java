package tv.limehd.androidapimodule.Download.Client;

import android.content.Context;

import java.io.File;

import tv.limehd.androidapimodule.Download.BroadcastDownloading;
import tv.limehd.androidapimodule.Download.ChannelListDownloading;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;
import tv.limehd.androidapimodule.Download.DeepClicksDownloading;
import tv.limehd.androidapimodule.Download.PingDownloading;
import tv.limehd.androidapimodule.Download.SessionDownload;
import tv.limehd.androidapimodule.Interfaces.CallBackUrlCurlRequestInterface;
import tv.limehd.androidapimodule.Interfaces.ListenerRequest;

public class ClientDownloading {

    public ClientDownloading() {
    }

    public void downloadChannelList(Context context, File cacheDir, String scheme, String api_root, String endpoint_channels, String application_id
            , String x_access_token, String channel_group_id, String time_zone, String locale, String x_test_ip, boolean use_cache) {
        ChannelListDownloading channelListDownloading = new ChannelListDownloading(context, cacheDir);
        channelListDownloading.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void onError(String error) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedError(error);
            }
        });
        channelListDownloading.setCallBackUrlCurlRequestInterface(new CallBackUrlCurlRequestInterface() {
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

        DataForRequest dataForRequestChannelList = new DataForRequest();
        dataForRequestChannelList.addComponent(new Component.DataBasic(scheme, api_root, endpoint_channels, application_id, x_access_token, x_test_ip, use_cache));
        dataForRequestChannelList.addComponent(new Component.DataChannelList(time_zone, locale, channel_group_id));

        channelListDownloading.loadingRequestChannelList(dataForRequestChannelList);
    }

    public void downloadBroadCast(Context context, String scheme, String api_root, String endpoint_broadcast
            , String channel_id, String before_date, String after_date, String time_zone
            , String application_id, String x_access_token, String locale, String x_test_ip, boolean use_cache) {
        BroadcastDownloading broadcastDownloading = new BroadcastDownloading(context);
        broadcastDownloading.setCallBackDownloadBroadCastInterface(new BroadcastDownloading.CallBackDownloadBroadCastInterface() {
            @Override
            public void callBackDownloadedBroadCastSucces(String response, String channel_id) {
                if (callBackDownloadInterfaceBroadcast != null)
                    callBackDownloadInterfaceBroadcast.callBackDownloadedSuccess(response, channel_id);
            }

            @Override
            public void callBackDownloadedBroadCastError(String error_message) {
                if (callBackDownloadInterfaceBroadcast != null)
                    callBackDownloadInterfaceBroadcast.callBackDownloadedError(error_message);
            }
        });
        broadcastDownloading.setCallBackUrlCurlRequestInterface(new CallBackUrlCurlRequestInterface() {
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
        DataForRequest dataForRequestBroadcast = new DataForRequest();
        dataForRequestBroadcast.addComponent(new Component.DataBasic(scheme, api_root, endpoint_broadcast, application_id, x_access_token, x_test_ip, use_cache));
        dataForRequestBroadcast.addComponent(new Component.DataBroadcast(time_zone, locale, channel_id, before_date, after_date));

        broadcastDownloading.loadingRequestBroadCast(dataForRequestBroadcast);
    }


    public void downloadPing(Context context, File cacheDir, String scheme, String api_root, String endpoint_ping, String application_id, String x_access_token, String x_test_ip, boolean use_cache) {
        PingDownloading pingDownloading = new PingDownloading(context, cacheDir);
        pingDownloading.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void onError(String error) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedError(error);
            }
        });
        pingDownloading.setCallBackUrlCurlRequestInterface(new CallBackUrlCurlRequestInterface() {
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
        DataForRequest dataForRequestPing = new DataForRequest();
        dataForRequestPing.addComponent(new Component.DataBasic(scheme, api_root, endpoint_ping, application_id, x_access_token, x_test_ip, use_cache));
        dataForRequestPing.addComponent(new Component.DataPing());
        pingDownloading.pingDownloadRequest(dataForRequestPing);
    }

    public void downloadSession(Context context, File cacheDir, String scheme, String api_root, String endpoint_session
            , String application_id, String x_access_token, String x_test_ip, boolean use_cache) {
        SessionDownload sessionDownload = new SessionDownload(context, cacheDir);
        sessionDownload.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void onError(String error) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedError(error);
            }
        });
        sessionDownload.setCallBackUrlCurlRequestInterface(new CallBackUrlCurlRequestInterface() {
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
        DataForRequest dataForRequestSession = new DataForRequest();
        dataForRequestSession.addComponent(new Component.DataBasic(scheme, api_root, endpoint_session, application_id, x_access_token, x_test_ip, use_cache));
        dataForRequestSession.addComponent(new Component.DataSession());
        sessionDownload.sessionDownloadRequest(dataForRequestSession);
    }

    public void sendingDeepClicks(Context context, File cacheDir, String scheme, String api_root, String endpoint_deepclicks,
                                  String application_id, String x_access_token, String x_test_ip, boolean use_cache, String query, String path, String device_id) {
        DeepClicksDownloading deepClicksDownloading = new DeepClicksDownloading(context, cacheDir);
        deepClicksDownloading.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(String response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response);
            }

            @Override
            public void onError(String error) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(error);
            }
        });
        deepClicksDownloading.setCallBackUrlCurlRequestInterface(new CallBackUrlCurlRequestInterface() {
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
        DataForRequest dataForRequestDeepClicks = new DataForRequest();
        dataForRequestDeepClicks.addComponent(new Component.DataBasic(scheme, api_root, endpoint_deepclicks, application_id, x_access_token, x_test_ip, use_cache));
        dataForRequestDeepClicks.addComponent(new Component.DataDeepClick(query, path, device_id));
        deepClicksDownloading.deepClicksSendRequest(dataForRequestDeepClicks);
    }

    public interface CallBackDownloadInterfaceBroadcast{
        void callBackDownloadedSuccess(String response, String channel_id);
        void callBackDownloadedError(String error_message);
    }

    public interface CallBackDownloadInterface {
        void callBackDownloadedSuccess(String response);
        void callBackDownloadedError(String error_message);
    }

    public interface CallBackRequestInterface {
        void callBackUrlRequest(String request);
        void callBackCurlRequest(String request);
    }

    private CallBackDownloadInterfaceBroadcast callBackDownloadInterfaceBroadcast;
    private CallBackDownloadInterface callBackDownloadInterface;
    private CallBackRequestInterface callBackRequestInterface;

    public void setCallBackDownloadInterfaceBroadcast(CallBackDownloadInterfaceBroadcast callBackDownloadInterfaceBroadcast){
        this.callBackDownloadInterfaceBroadcast = callBackDownloadInterfaceBroadcast;
    }

    public void setCallBackDownloadInterface(CallBackDownloadInterface callBackDownloadInterface) {
        this.callBackDownloadInterface = callBackDownloadInterface;
    }

    public void setCallBackRequestInterface(CallBackRequestInterface callBackRequestInterface) {
        this.callBackRequestInterface = callBackRequestInterface;
    }
}

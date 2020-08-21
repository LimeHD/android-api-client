package tv.limehd.androidapimodule.Download.Client;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import tv.limehd.androidapimodule.Download.BroadcastDownloading;
import tv.limehd.androidapimodule.Download.ChannelListDownloading;
import tv.limehd.androidapimodule.Download.Data.ComplexResponse;
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

    private ChannelListDownloading createDownloadingChannelList() {
        ChannelListDownloading channelListDownloading = new ChannelListDownloading();
        channelListDownloading.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(ComplexResponse response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response.getTextBodyResponse());
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
        return channelListDownloading;
    }

    public void downloadChannelList(@NonNull DataForRequest dataForRequestChannelList) {
        ChannelListDownloading channelListDownloading = createDownloadingChannelList();
        channelListDownloading.sendRequestChannelList(dataForRequestChannelList);
    }

    @Deprecated
    public void downloadChannelList(Context context, File cacheDir, String scheme, String api_root, String endpoint_channels, String application_id
            , String x_access_token, String channel_group_id, String time_zone, String locale, String x_test_ip, boolean use_cache) {
        ChannelListDownloading channelListDownloading = createDownloadingChannelList();
        DataForRequest dataForRequestChannelList = new DataForRequest();
        dataForRequestChannelList.addComponent(new Component.DataBasic(scheme, api_root, endpoint_channels, application_id, x_access_token, x_test_ip));
        dataForRequestChannelList.addComponent(new Component.DataChannelList(time_zone, locale, channel_group_id));
        dataForRequestChannelList.addComponent(new Component.DataCache(context, use_cache, cacheDir));
        channelListDownloading.sendRequestChannelList(dataForRequestChannelList);
    }

    private BroadcastDownloading createDownloadingBroadCast() {
        BroadcastDownloading broadcastDownloading = new BroadcastDownloading();
        broadcastDownloading.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(ComplexResponse response) {
                if (callBackDownloadInterfaceBroadcast != null)
                    callBackDownloadInterfaceBroadcast.callBackDownloadedSuccess(response.getTextBodyResponse(), response.getChannelId());
            }

            @Override
            public void onError(String error_message) {
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
        return broadcastDownloading;
    }

    public void downloadBroadCast(@NonNull DataForRequest dataForRequest) {
        BroadcastDownloading broadcastDownloading = createDownloadingBroadCast();
        broadcastDownloading.sendRequestBroadCast(dataForRequest);
    }

    @Deprecated
    public void downloadBroadCast(Context context, String scheme, String api_root, String endpoint_broadcast
            , String channel_id, String before_date, String after_date, String time_zone
            , String application_id, String x_access_token, String locale, String x_test_ip, boolean use_cache) {

        BroadcastDownloading broadcastDownloading = createDownloadingBroadCast();
        DataForRequest dataForRequestBroadcast = new DataForRequest();
        dataForRequestBroadcast.addComponent(new Component.DataBasic(scheme, api_root, endpoint_broadcast, application_id, x_access_token, x_test_ip));
        dataForRequestBroadcast.addComponent(new Component.DataBroadcast(time_zone, locale, channel_id, before_date, after_date));
        dataForRequestBroadcast.addComponent(new Component.DataCache(context, use_cache, null));
        broadcastDownloading.sendRequestBroadCast(dataForRequestBroadcast);
    }

    private PingDownloading createDownloadingPing() {
        PingDownloading pingDownloading = new PingDownloading();
        pingDownloading.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(ComplexResponse response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response.getTextBodyResponse());
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
        return pingDownloading;
    }

    public void downloadPing(DataForRequest dataForRequestPing) {
        PingDownloading pingDownloading = createDownloadingPing();
        pingDownloading.sendRequestPing(dataForRequestPing);
    }

    @Deprecated
    public void downloadPing(Context context, File cacheDir, String scheme, String api_root, String endpoint_ping, String application_id, String x_access_token, String x_test_ip, boolean use_cache) {
        PingDownloading pingDownloading = createDownloadingPing();
        DataForRequest dataForRequestPing = new DataForRequest();
        dataForRequestPing.addComponent(new Component.DataBasic(scheme, api_root, endpoint_ping, application_id, x_access_token, x_test_ip));
        dataForRequestPing.addComponent(new Component.DataPing());
        dataForRequestPing.addComponent(new Component.DataCache(context, use_cache, cacheDir));
        pingDownloading.sendRequestPing(dataForRequestPing);
    }

    private SessionDownload createDownloadSession() {
        SessionDownload sessionDownload = new SessionDownload();
        sessionDownload.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(ComplexResponse response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response.getTextBodyResponse());
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
        return sessionDownload;
    }

    public void downloadSession(DataForRequest dataForRequestSession) {
        SessionDownload sessionDownload = createDownloadSession();
        sessionDownload.sendRequestSession(dataForRequestSession);
    }

    @Deprecated
    public void downloadSession(Context context, File cacheDir, String scheme, String api_root, String endpoint_session
            , String application_id, String x_access_token, String x_test_ip, boolean use_cache) {
        SessionDownload sessionDownload = createDownloadSession();
        DataForRequest dataForRequestSession = new DataForRequest();
        dataForRequestSession.addComponent(new Component.DataBasic(scheme, api_root, endpoint_session, application_id, x_access_token, x_test_ip));
        dataForRequestSession.addComponent(new Component.DataSession());
        dataForRequestSession.addComponent(new Component.DataCache(context, use_cache, cacheDir));
        sessionDownload.sendRequestSession(dataForRequestSession);
    }

    private DeepClicksDownloading createDownloadingDeepClicks() {
        DeepClicksDownloading deepClicksDownloading = new DeepClicksDownloading();
        deepClicksDownloading.setListenerRequest(new ListenerRequest() {
            @Override
            public void onSuccess(ComplexResponse response) {
                if (callBackDownloadInterface != null)
                    callBackDownloadInterface.callBackDownloadedSuccess(response.getTextBodyResponse());
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
        return deepClicksDownloading;
    }

    public void downloadDeepClicks(DataForRequest dataForRequestDeepClicks) {
        DeepClicksDownloading deepClicksDownloading = createDownloadingDeepClicks();
        deepClicksDownloading.sendRequestDeepClicks(dataForRequestDeepClicks);
    }

    @Deprecated
    public void sendingDeepClicks(Context context, File cacheDir, String scheme, String api_root, String endpoint_deepclicks,
                                  String application_id, String x_access_token, String x_test_ip, boolean use_cache, String query, String path, String device_id) {
        DeepClicksDownloading deepClicksDownloading = createDownloadingDeepClicks();
        DataForRequest dataForRequestDeepClicks = new DataForRequest();
        dataForRequestDeepClicks.addComponent(new Component.DataBasic(scheme, api_root, endpoint_deepclicks, application_id, x_access_token, x_test_ip));
        dataForRequestDeepClicks.addComponent(new Component.DataDeepClick(query, path, device_id));
        dataForRequestDeepClicks.addComponent(new Component.DataCache(context, use_cache, cacheDir));
        deepClicksDownloading.sendRequestDeepClicks(dataForRequestDeepClicks);
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

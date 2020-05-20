package tv.limehd.androidapimodule.Download;

import tv.limehd.androidapimodule.Values.ApiValues;

public class ClientDownloading {

    private ApiValues apiValues;

    public ClientDownloading() {
        apiValues = new ApiValues();
    }

    public void downloadChannelList(String scheme, String api_root, String endpoint_channels) {
        ChannelListDownloading channelListDownloading = new ChannelListDownloading();
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
        channelListDownloading.loadingRequestChannelList(scheme, api_root, endpoint_channels);
    }

    public void downloadBroadCast(String scheme, String api_root, String endpoint_broadcast
            , String channel_id, String before_date, String after_date, String time_zone) {
        BroadcastDownloading broadcastDownloading = new BroadcastDownloading();
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
        broadcastDownloading.loadingRequestBroadCast(scheme, api_root, endpoint_broadcast, channel_id, before_date, after_date, time_zone);
    }

    public void dowloadPing(String scheme, String api_root, String endpoint_ping) {
        PingApi pingApi = new PingApi();
        pingApi.setCallBackPingInterface(new PingApi.CallBackPingInterface() {
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
        pingApi.setCallBackPingRequestInterface(new PingApi.CallBackPingRequestInterface() {
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
        pingApi.pingApiRequest(scheme, api_root, endpoint_ping);
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

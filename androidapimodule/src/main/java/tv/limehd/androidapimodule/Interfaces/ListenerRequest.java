package tv.limehd.androidapimodule.Interfaces;

import tv.limehd.androidapimodule.Download.Data.ComplexResponse;

public interface ListenerRequest {
    void onSuccess(ComplexResponse response);

    void onError(String error);
}

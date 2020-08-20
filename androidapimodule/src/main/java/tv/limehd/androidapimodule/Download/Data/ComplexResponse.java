package tv.limehd.androidapimodule.Download.Data;

import androidx.annotation.NonNull;

public class ComplexResponse {
    private String textBodyResponse;
    private String channelId;

    public ComplexResponse(@NonNull String textBodyResponse) {
        this.textBodyResponse = textBodyResponse;
    }

    public ComplexResponse setChannelId(@NonNull String channelId) {
        this.channelId = channelId;
        return this;
    }

    public String getTextBodyResponse() {
        return textBodyResponse;
    }

    public String getChannelId() {
        return channelId;
    }
}

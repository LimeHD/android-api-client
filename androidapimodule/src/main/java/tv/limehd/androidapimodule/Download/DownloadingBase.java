package tv.limehd.androidapimodule.Download;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import tv.limehd.androidapimodule.Values.ApiValues;

public class DownloadingBase {

    protected ApiValues apiValues;
    protected Context context;
    protected File cacheDir;

    protected DownloadingBase(@NonNull Context context) {
        this.context = context;
        initialization();
    }

    protected DownloadingBase() {
        initialization();
    }

    private void initialization() {
        apiValues = new ApiValues();
    }
}

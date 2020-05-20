package tv.limehd.androidapimodule;

import com.moczul.ok2curl.CurlInterceptor;
import com.moczul.ok2curl.logger.Loggable;

import okhttp3.OkHttpClient;

public class LimeCurlBuilder extends OkHttpClient {

    public static OkHttpClient.Builder builder;

    public interface LogCurlInterface {
        void logCurl(String message);
    }

    public OkHttpClient.Builder setLogCurlInterface(final LogCurlInterface logCurlInterface) {
        builder = new OkHttpClient.Builder().addInterceptor(new CurlInterceptor(new Loggable() {
            @Override
            public void log(String message) {
                logCurlInterface.logCurl(message);
            }
        }));
        return builder;
    }
}

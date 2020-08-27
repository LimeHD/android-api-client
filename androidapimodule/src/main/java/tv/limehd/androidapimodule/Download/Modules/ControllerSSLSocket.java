package tv.limehd.androidapimodule.Download.Modules;

import android.os.Build;

import androidx.annotation.NonNull;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;

public class ControllerSSLSocket {

    private TrustManager[] trustAllCertificates;
    private Component.DataBasic dataBasic;

    public ControllerSSLSocket(@NonNull DataForRequest dataForRequest) {
        dataBasic = dataForRequest.getComponent(Component.DataBasic.class);
        if(dataBasic == null) {
            throw new NullPointerException();
        } else {
            trustAllCertificates = createTrustManager();
        }
    }

    private boolean isUseSSL() {
        return trustAllCertificates != null
                && dataBasic != null
                && dataBasic.isUseSSL();
    }
    
    public OkHttpClient.Builder connectSSLSocket(OkHttpClient.Builder okHttpClientBuilder) {
        if(okHttpClientBuilder == null)
            throw new NullPointerException();

        if (isUseSSL() && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                okHttpClientBuilder.sslSocketFactory(createSSLSocketFactory(), (X509TrustManager) trustAllCertificates[0]).build();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
        return okHttpClientBuilder;
    }

    private TrustManager[] createTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
    }

    private SSLSocketFactory createSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        return new TSLSocketFactory();
    }
}
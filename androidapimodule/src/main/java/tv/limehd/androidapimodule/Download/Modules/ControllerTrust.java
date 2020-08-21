package tv.limehd.androidapimodule.Download.Modules;

import android.os.Build;

import androidx.annotation.NonNull;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import tv.limehd.androidapimodule.Download.Data.Component;
import tv.limehd.androidapimodule.Download.Data.DataForRequest;

public class ControllerTrust {

    private TrustManager[] trustAllCertificates;
    private SSLContext sslContext;
    private SSLSocketFactory sslSocketFactory;
    private Component.DataBasic dataBasic;

    public ControllerTrust(@NonNull DataForRequest dataForRequest) {
        dataBasic = dataForRequest.getComponent(Component.DataBasic.class);
        if(dataBasic == null) {
            throw new NullPointerException();
        } else {
            trustAllCertificates = createTrustManager();
            sslContext = tryCreateSSLContextByTrustManagers();
            sslSocketFactory = createSSLSocketFactory(sslContext);
        }
    }

    private boolean isUseSSL() {
        return trustAllCertificates != null
                && sslContext != null
                && sslSocketFactory != null
                && dataBasic != null
                && dataBasic.isUseSSL();
    }
    
    public OkHttpClient.Builder connectSSLSocket(OkHttpClient.Builder okHttpClientBuilder) {
        if(okHttpClientBuilder == null)
            throw new NullPointerException();

        if (isUseSSL() && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCertificates[0]).build();
        }
        return okHttpClientBuilder;
    }

    private SSLContext tryCreateSSLContextByTrustManagers() {
        SSLContext sslContext = null;
        try {
            sslContext = createSSLContextByTrustManagers(trustAllCertificates);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private TrustManager[] createTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
    }

    private SSLContext createSSLContextByTrustManagers(@NonNull TrustManager[] trustManagers) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagers, new SecureRandom());
        return sslContext;
    }

    private SSLSocketFactory createSSLSocketFactory(@NonNull SSLContext sslContext) {
        return sslContext.getSocketFactory();
    }
}

package tv.limehd.androidapiclient;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import tv.limehd.androidapiclient.ApiManager.ApiManager;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeLocale;
import tv.limehd.androidapimodule.LimeRFC;
import tv.limehd.androidapimodule.LimeUTC;
import tv.limehd.androidapimodule.Values.ApiValues;

public class DemoActivity extends Activity implements LimeApiClient.DownloadChannelListCallBack, LimeApiClient.DownloadBroadCastCallBack, LimeApiClient.DownloadPingCallBack,
        LimeApiClient.DownloadSessionCallBack, LimeApiClient.DownloadDeepClicksCallBack {

    private String LIME_LOG = "limeapilog";
    //экземпляр апи клиента для запросов
    private LimeApiClient limeApiClient;
    //рут апи
    private String api_root = "";
    private String x_access_token = "";
    private String application_id = "";
    private String device_id = "";
    //экземпляр апи значений
    private ApiValues apiValues;
    //для примера ид телеканала с телепрограммой
    private String example_channel_id = "10505";
    //для примера timestamp в пять дней для разницы при запросе ЕПГ на +- 5 дней.
    private long five_days_stamp = 432000000;
    //для примера тайм зона в формате UTC offset
    private String example_time_zone = "UTC+03:00";


    private ApiManager apiManager;


    private void setCallBackRequests() {
        //Интерфейс для получения отправленного запроса у пинга
        limeApiClient.setRequestPingCallBack(new LimeApiClient.RequestCallBack() {
            @Override
            public void callBackUrlRequest(String request) {
                Log.e(LIME_LOG, request);
                printUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                Log.e(LIME_LOG, request);
                printCurl(request);
                Log.e(LIME_LOG, "1");
            }
        });
        //Интерфейс для получения отправленного запроса у телепередачи
        limeApiClient.setRequestBroadCastCallBack(new LimeApiClient.RequestCallBack() {
            @Override
            public void callBackUrlRequest(String request) {
                Log.e(LIME_LOG, request);
                printUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                Log.e(LIME_LOG, request);
                printCurl(request);
            }
        });
        //Интерфейс для получения отправленного запроса у списка каналов
        limeApiClient.setRequestChannelList(new LimeApiClient.RequestCallBack() {
            @Override
            public void callBackUrlRequest(String request) {
                Log.e(LIME_LOG, request);
                printUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                Log.e(LIME_LOG, request);
                printCurl(request);
            }
        });
        limeApiClient.setRequestSession(new LimeApiClient.RequestCallBack() {
            @Override
            public void callBackUrlRequest(String request) {
                Log.e(LIME_LOG, request);
                printUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                Log.e(LIME_LOG, request);
                printCurl(request);
            }
        });
        limeApiClient.setRequestDeepClicks(new LimeApiClient.RequestCallBack() {
            @Override
            public void callBackUrlRequest(String request) {
                Log.e(LIME_LOG, request);
                printUrlRequest(request);
            }

            @Override
            public void callBackCurlRequest(String request) {
                Log.e(LIME_LOG, request);
                printCurl(request);
            }
        });
    }

    private String getLocale() {
        String locale = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LimeLocale.getLocaleTag(getResources().getConfiguration().getLocales().get(0));
        } else {
            locale = LimeLocale.getLocaleTag(getResources().getConfiguration().locale);
        }
        return locale;
    }

    void initializationLimeApiClient() {
        device_id = LimeApiClient.getDeviceId(this);
        limeApiClient = new LimeApiClient(getApplicationContext(), device_id, api_root, apiValues.getSCHEME_HTTPS(), application_id, x_access_token,
                getLocale(), getApplication().getCacheDir());
        limeApiClient.setDownloadChannelListCallBack(this);
        limeApiClient.setDownloadBroadCastCallBack(this);
        limeApiClient.setDownloadPingCallBack(this);
        limeApiClient.setDownloadSessionCallBack(this);
    }

    void loadDataFromApiManager() {
        if (apiManager != null) {
            api_root = apiManager.getTextApiRoot();
            application_id = apiManager.getTextApplicationId();
            x_access_token = apiManager.getTextAccessToken();
            device_id = apiManager.getTextDeviceId();
        }
        limeApiClient.updateLimeApiClientData(api_root, device_id, apiValues.getSCHEME_HTTPS(), application_id, x_access_token
                , getLocale());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        //инициализация апи значений
        apiValues = new ApiValues();

        //инициализация апи клиента
        initializationLimeApiClient();

        setCallBackRequests();

        //инициализация даты для запроса по формату RFC3339
        long current_time_stamp = System.currentTimeMillis();//текущее время
        final long before_date = current_time_stamp - five_days_stamp;//левая граница - 5 дней
        final long after_date = current_time_stamp + five_days_stamp;//правая граница + 5 дней

        apiManager = new ApiManager(this, new ApiManager.ApiManagerInterface() {
            @Override
            public void onClickDownloadPing() {
                loadDataFromApiManager();
                limeApiClient.downloadPing();
            }

            @Override
            public void onClickDownloadBroadcast() {
                loadDataFromApiManager();
                limeApiClient.downloadBroadcast(example_channel_id, LimeRFC.timeStampToRFC(before_date), LimeRFC.timeStampToRFC(after_date), example_time_zone);
            }

            @Override
            public void onClickDownloadChannelList() {
                loadDataFromApiManager();
                //запрос списка телеканалов
                Log.e("logos", LimeUTC.oneHourToUtcFormat("-10"));
                limeApiClient.downloadChannelList("1", LimeUTC.oneHourToUtcFormat("-10"));
            }

            @Override
            public void onClickDownloadSessions() {
                loadDataFromApiManager();
                limeApiClient.downloadSession();
            }

            @Override
            public void onClickDownloadDeepClicks() {
                loadDataFromApiManager();
                //TODO Заменить query и path на рабочие
                limeApiClient.downloadDeepClicks("query", "path");
            }
        });
    }

    private void printAnswer(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                apiManager.setTextAnswer(message);
            }
        });
    }

    private void printCurl(final String request) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                apiManager.setTextCurl(request);
            }
        });
    }

    private void printUrlRequest(final String request) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                apiManager.setTextUrl(request);
            }
        });
    }

    //region CallBack From LimeApiClient
    @Override
    public void downloadChannelListSuccess(String response) {
        Log.e(LIME_LOG, response);
        printAnswer(response);
    }

    @Override
    public void downloadBroadCastSuccess(String response, String channel_id) {
        Log.e(LIME_LOG, response);
        printAnswer(response);
    }

    @Override
    public void downloadPingSuccess(String response) {
        Log.e(LIME_LOG, response);
        printAnswer(response);
    }

    @Override
    public void downloadSessionSuccess(String response) {
        Log.e(LIME_LOG, response);
        printAnswer(response);
    }

    @Override
    public void sendingDeepClicksSuccess(String response) {
        Log.e(LIME_LOG, response);
        printAnswer(response);
    }

    @Override
    public void downloadChannelListError(String message) {
        Log.e(LIME_LOG, message);
        printAnswer(message);
    }

    @Override
    public void downloadBroadCastError(String message) {
        Log.e(LIME_LOG, message);
        printAnswer(message);
    }

    @Override
    public void downloadPingError(String message) {
        Log.e(LIME_LOG, message);
        printAnswer(message);
    }

    @Override
    public void downloadSessionError(String message) {
        Log.e(LIME_LOG, message);
        printAnswer(message);
    }

    @Override
    public void sendingDeepClicksError(String message) {
        Log.e(LIME_LOG, message);
        printAnswer(message);
    }
    //endregion
}

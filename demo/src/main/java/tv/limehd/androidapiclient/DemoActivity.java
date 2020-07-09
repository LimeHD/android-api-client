package tv.limehd.androidapiclient;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tv.limehd.androidapiclient.Adapters.ApiAdapter;
import tv.limehd.androidapiclient.Adapters.LogsAdapter;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeLocale;
import tv.limehd.androidapimodule.LimeRFC;
import tv.limehd.androidapimodule.Values.ApiValues;

public class DemoActivity extends Activity implements LimeApiClient.DownloadChannelListCallBack, LimeApiClient.DownloadBroadCastCallBack, LimeApiClient.DownloadPingCallBack,
        LimeApiClient.DownloadSessionCallBack {

    private String LIME_LOG = "limeapilog";
    //экземпляр апи клиента для запросов
    private LimeApiClient limeApiClient;
    //рут апи
    private String api_root = "";
    private String x_access_token = "";
    private String application_id = "";
    //экземпляр апи значений
    private ApiValues apiValues;
    //для примера ид телеканала с телепрограммой
    private String example_channel_id = "10505";
    //для примера timestamp в пять дней для разницы при запросе ЕПГ на +- 5 дней.
    private long five_days_stamp = 432000000;
    //для примера тайм зона в формате UTC offset
    private String example_time_zone = "UTC+03:00";

    private RecyclerView recyclerButtons;
    private RecyclerView recyclerLogs;
    ApiAdapter apiAdapter;
    LogsAdapter logsAdapter;


    private void findViewId() {
        recyclerButtons = findViewById(R.id.recyclerViewButtons);
        recyclerLogs = findViewById(R.id.recyclerViewLogs);
    }

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
    }

    void initializationLimeApiClient() {
        String locale = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LimeLocale.getLocaleTag(getResources().getConfiguration().getLocales().get(0));
        } else {
            locale = LimeLocale.getLocaleTag(getResources().getConfiguration().locale);
        }
        limeApiClient = new LimeApiClient(getApplicationContext(), api_root, apiValues.getSCHEME_HTTPS(), application_id, x_access_token,
                locale, getApplication().getCacheDir());
        limeApiClient.setDownloadChannelListCallBack(this);
        limeApiClient.setDownloadBroadCastCallBack(this);
        limeApiClient.setDownloadPingCallBack(this);
        limeApiClient.setDownloadSessionCallBack(this);
    }

    void loadDataFromLogsAdapter() {
        if (logsAdapter != null) {
            api_root = logsAdapter.getApiRoot();
            application_id = logsAdapter.getApplicationId();
            x_access_token = logsAdapter.getXAccessToken();
        }
        String locale = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LimeLocale.getLocaleTag(getResources().getConfiguration().getLocales().get(0));
        } else {
            locale = LimeLocale.getLocaleTag(getResources().getConfiguration().locale);
        }
        limeApiClient.updateLimeApiClientData(api_root, apiValues.getSCHEME_HTTPS(), application_id, x_access_token
                , locale);
        SettingsManager.setApiRoot(getApplicationContext(), api_root);
        SettingsManager.setXAccessToken(getApplicationContext(), x_access_token);
        SettingsManager.setApplicationId(getApplicationContext(), application_id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewId();

        //инициализация апи значений
        apiValues = new ApiValues();

        //получение сохраненных данных


        //инициализация апи клиента
        initializationLimeApiClient();

        setCallBackRequests();

        //инициализация даты для запроса по формату RFC3339
        long current_time_stamp = System.currentTimeMillis();//текущее время
        final long before_date = current_time_stamp - five_days_stamp;//левая граница - 5 дней
        final long after_date = current_time_stamp + five_days_stamp;//правая граница + 5 дней

        apiAdapter = new ApiAdapter(getApplicationContext());
        apiAdapter.setApiAdapterInterface(new ApiAdapter.ApiAdapterInterface() {
            @Override
            public void onClickPing() {
                loadDataFromLogsAdapter();
                //запрос пинга
                limeApiClient.downloadPing();
            }

            @Override
            public void onClickDownloadBroadcast() {
                loadDataFromLogsAdapter();
                //запрос списка телепередач для телеканала
                limeApiClient.downloadBroadcast(example_channel_id, LimeRFC.timeStampToRFC(before_date), LimeRFC.timeStampToRFC(after_date), example_time_zone);
            }

            @Override
            public void onClickDownloadChannelList() {
                loadDataFromLogsAdapter();
                //запрос списка телеканалов
                limeApiClient.downloadChannelList("1");
            }

            @Override
            public void onClickDownloadSessions() {
                loadDataFromLogsAdapter();
                //Запрос создания сессии
                limeApiClient.downloadSession();
            }
        });

        logsAdapter = new LogsAdapter(getApplicationContext());

        recyclerButtons.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerLogs.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerButtons.setAdapter(apiAdapter);
        recyclerLogs.setAdapter(logsAdapter);
    }

    private void printAnswer(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (logsAdapter != null) {
                    logsAdapter.setAnswer(message);
                    logsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void printCurl(final String request) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (logsAdapter != null) {
                    logsAdapter.setCurl(request);
                    logsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void printUrlRequest(final String request) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (logsAdapter != null) {
                    logsAdapter.setUrl(request);
                    logsAdapter.notifyDataSetChanged();
                }
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
    public void downloadBroadCastSuccess(String response) {
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
    //endregion
}

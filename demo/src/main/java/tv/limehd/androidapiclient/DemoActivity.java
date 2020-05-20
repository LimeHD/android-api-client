package tv.limehd.androidapiclient;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tv.limehd.androidapiclient.Adapters.ApiAdapter;
import tv.limehd.androidapimodule.LimeApiClient;
import tv.limehd.androidapimodule.LimeRFC;
import tv.limehd.androidapimodule.Values.ApiValues;

public class DemoActivity extends Activity implements LimeApiClient.DownloadChannelListCallBack, LimeApiClient.DownloadBroadCastCallBack, LimeApiClient.DownloadPingCallBack {

    private String LIME_LOG = "limeapilog";
    //экземпляр апи клиента для запросов
    private LimeApiClient limeApiClient;
    //рут апи
    private String api_root = "api.iptv2021.com";
    //экземпляр апи значений
    private ApiValues apiValues;
    //для примера ид телеканала с телепрограммой
    private String example_channel_id = "10505";
    //для примера timestamp в пять дней для разницы при запросе ЕПГ на +- 5 дней.
    private long five_days_stamp = 432000000;
    //для примера тайм зона в формате UTC offset
    private String example_time_zone = "UTC+03:00";

    private RecyclerView recyclerView;
    private EditText answerTextView;
    private EditText curlTextView;
    private EditText urlTextView;


    private void findViewId() {
        answerTextView = findViewById(R.id.textAnswer);
        curlTextView = findViewById(R.id.textCurl);
        urlTextView = findViewById(R.id.textUrl);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setOnClickListener() {
        answerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", answerTextView.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.answer_was_copied), Toast.LENGTH_SHORT).show();
            }
        });
        curlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", curlTextView.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.curl_was_copied), Toast.LENGTH_SHORT).show();
            }
        });
        urlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", urlTextView.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.url_request_was_copied), Toast.LENGTH_SHORT).show();
            }
        });
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
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewId();
        setOnClickListener();

        //инициализация апи клиента
        limeApiClient = new LimeApiClient(api_root);

        //инициализация апи значений
        apiValues = new ApiValues();

        limeApiClient.setDownloadChannelListCallBack(this);
        limeApiClient.setDownloadBroadCastCallBack(this);
        limeApiClient.setDownloadPingCallBack(this);

        setCallBackRequests();

        //инициализация даты для запроса по формату RFC3339
        long current_time_stamp = System.currentTimeMillis();//текущее время
        final long before_date = current_time_stamp - five_days_stamp;//левая граница - 5 дней
        final long after_date = current_time_stamp + five_days_stamp;//правая граница + 5 дней

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ApiAdapter apiAdapter = new ApiAdapter(getApplicationContext());
        apiAdapter.setApiAdapterInterface(new ApiAdapter.ApiAdapterInterface() {
            @Override
            public void onClickPing() {
                //запрос пинга
                limeApiClient.downloadPing(apiValues.getSCHEME_HTTP(), apiValues.getURL_PING_PATH());
            }

            @Override
            public void onClickDownloadBroadcast() {
                //запрос списка телепередач для телеканала
                limeApiClient.downloadBroadcast(apiValues.getSCHEME_HTTP(), apiValues.getURL_BROADCAST_PATH()
                        , example_channel_id, LimeRFC.timeStampToRFC(before_date), LimeRFC.timeStampToRFC(after_date), example_time_zone);

            }

            @Override
            public void onClickDownloadChannelList() {
                //запрос списка телеканалов
                limeApiClient.downloadChannelList(apiValues.getSCHEME_HTTP(), apiValues.getURL_CHANNELS_GRECE_PATH());
            }
        });
        recyclerView.setAdapter(apiAdapter);
    }

    private void printAnswer(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (answerTextView != null)
                    answerTextView.setText(message);
            }
        });
    }

    private void printCurl(final String request) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (curlTextView != null) {
                    curlTextView.setText(request);
                }
            }
        });
    }

    private void printUrlRequest(final String request) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (curlTextView != null) {
                    urlTextView.setText(request);
                }
            }
        });
    }

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
}

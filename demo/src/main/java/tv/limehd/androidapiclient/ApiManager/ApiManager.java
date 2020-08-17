package tv.limehd.androidapiclient.ApiManager;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tv.limehd.androidapiclient.R;
import tv.limehd.androidapiclient.SettingsManager;

public class ApiManager extends ApiManagerBase {


    private Button buttonDownloadBroadcast;
    private Button buttonDownloadPing;
    private Button buttonDownloadSession;
    private Button buttonDownloadChannelList;
    private Button buttonDownloadDeepClicks;

    public ApiManager(@NonNull Activity activity, @NonNull ApiManagerInterface apiManagerInterface) {
        super(activity, apiManagerInterface);
        initializationApiManagerBase();
    }

    protected void findViewId() {
        super.findViewId();
        buttonDownloadBroadcast = activity.findViewById(R.id.button_download_broadcast);
        buttonDownloadPing = activity.findViewById(R.id.button_download_ping);
        buttonDownloadSession = activity.findViewById(R.id.button_download_session);
        buttonDownloadChannelList = activity.findViewById(R.id.button_download_channel_list);
        buttonDownloadDeepClicks = activity.findViewById(R.id.button_download_deep_clicks);
    }

    protected void setButtonClickListener() {
        super.setButtonClickListener();

        buttonDownloadBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiManagerInterface.onClickDownloadBroadcast();
            }
        });
        buttonDownloadPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiManagerInterface.onClickDownloadPing();
            }
        });
        buttonDownloadSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiManagerInterface.onClickDownloadSessions();
            }
        });
        buttonDownloadChannelList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiManagerInterface.onClickDownloadChannelList();
            }
        });

        buttonDownloadDeepClicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiManagerInterface.onClickDownloadDeepClicks();
            }
        });
    }

    public interface ApiManagerInterface {
        void onClickDownloadPing();

        void onClickDownloadBroadcast();

        void onClickDownloadChannelList();

        void onClickDownloadSessions();

        void onClickDownloadDeepClicks();
    }



}

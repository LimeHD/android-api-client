package tv.limehd.androidapiclient;

import android.app.Activity;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ApiManager {

    private Activity activity;

    private ApiManagerInterface apiManagerInterface;

    private TextView textViewSpinnerText;
    private Spinner spinner;

    private EditText editTextApiRoot;
    private EditText editTextAccessToken;
    private EditText editTextApplicationId;
    private EditText editTextDeviceId;
    private Button buttonSaveData;

    private Button buttonDownloadBroadcast;
    private Button buttonDownloadPing;
    private Button buttonDownloadSession;
    private Button buttonDownloadChannelList;

    private EditText editTextAnswer;
    private EditText editTextUrl;
    private EditText editTextCurl;

    private DataItemsForRequest itemsForRequest = new DataItemsForRequest();
    private String[] application_ids;

    public ApiManager(@NonNull Activity activity, @NonNull ApiManagerInterface apiManagerInterface) {
        this.activity = activity;
        this.apiManagerInterface = apiManagerInterface;
        application_ids = new String[0];
        findViewId();
        setButtonClickListener();
        setDeviceId(Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
        itemsForRequest = loadDataFromSettingsManager();
        setSpinnerItemSelectedListener();
    }

    public void setTextAnswer(String message) {
        editTextAnswer.setText(message);
    }

    public void setTextUrl(String message) {
        editTextUrl.setText(message);
    }

    public void setTextCurl(String message) {
        editTextCurl.setText(message);
    }

    public void setDeviceId(String id) {
        if (id == null || id.length() == 0) {
            editTextDeviceId.setText("device_id_null");
        } else {
            editTextDeviceId.setText(id);
        }
    }

    private void setTextApiRoot(String text) {
        editTextApiRoot.setText(text);
    }

    private void setTextAccessToken(String text) {
        editTextAccessToken.setText(text);
    }

    private void setTextApplicationId(String text) {
        editTextApplicationId.setText(text);
    }

    private void setTextViewSpinnerText(String text) {
        textViewSpinnerText.setText(text);
    }

    public String getTextApiRoot() {
        return editTextApiRoot.getText().toString();
    }

    public String getTextAccessToken() {
        return editTextAccessToken.getText().toString();
    }

    public String getTextApplicationId() {
        return editTextApplicationId.getText().toString();
    }

    public String getTextDeviceId() {
        return editTextDeviceId.getText().toString();
    }


    private void findViewId() {
        textViewSpinnerText = activity.findViewById(R.id.text_view_spinner_text);
        spinner = activity.findViewById(R.id.spinner_application);

        editTextApiRoot = activity.findViewById(R.id.edit_text_api_root);
        editTextAccessToken = activity.findViewById(R.id.edit_text_access_token);
        editTextApplicationId = activity.findViewById(R.id.edit_text_application_id);
        editTextDeviceId = activity.findViewById(R.id.edit_text_device_id);
        buttonSaveData = activity.findViewById(R.id.button_save_data);

        buttonDownloadBroadcast = activity.findViewById(R.id.button_download_broadcast);
        buttonDownloadPing = activity.findViewById(R.id.button_download_ping);
        buttonDownloadSession = activity.findViewById(R.id.button_download_session);
        buttonDownloadChannelList = activity.findViewById(R.id.button_download_channel_list);

        editTextAnswer = activity.findViewById(R.id.edit_text_answer);
        editTextUrl = activity.findViewById(R.id.edit_text_url);
        editTextCurl = activity.findViewById(R.id.edit_text_curl);
    }

    private void setAdapter(DataItemsForRequest dataItemsForRequest) {
        if (dataItemsForRequest == null) {
            dataItemsForRequest = new DataItemsForRequest();
        }
        application_ids = new String[dataItemsForRequest.getDataForRequestList().size()];
        for (int i = 0; i < dataItemsForRequest.getDataForRequestList().size(); i++) {
            application_ids[i] = dataItemsForRequest.getDataForRequestList().get(i).getApplicationId();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, application_ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private DataItemsForRequest loadDataFromSettingsManager() {
        Gson gson = new Gson();
        String jsonString = SettingsManager.getDataRequests(activity);
        DataItemsForRequest dataItemsForRequest = gson.fromJson(jsonString, DataItemsForRequest.class);
        if (dataItemsForRequest == null) {
            dataItemsForRequest = new DataItemsForRequest();
        }
        setAdapter(dataItemsForRequest);
        return dataItemsForRequest;
    }

    private List<DataItemsForRequest.DataForRequest> tryReplaceItem(@NonNull List<DataItemsForRequest.DataForRequest> itemsForRequestList, DataItemsForRequest.DataForRequest dataForRequest) {
        for (int i = 0; i < itemsForRequestList.size(); i++) {
            if (itemsForRequestList.get(i).getApplicationId().equals(dataForRequest.getApplicationId())) {
                itemsForRequestList.remove(i);
                break;
            }
        }
        itemsForRequestList.add(dataForRequest);
        return itemsForRequestList;
    }

    private void saveDataFromEditTexts() {
        DataItemsForRequest.DataForRequest dataForRequest = new DataItemsForRequest.DataForRequest();
        dataForRequest.setAccessToken(getTextAccessToken());
        dataForRequest.setApiRoot(getTextApiRoot());
        dataForRequest.setApplicationId(getTextApplicationId());

        itemsForRequest = loadDataFromSettingsManager();
        List<DataItemsForRequest.DataForRequest> listLoadedFromJson = itemsForRequest.getDataForRequestList();
        listLoadedFromJson = tryReplaceItem(listLoadedFromJson, dataForRequest);
        itemsForRequest.setDataForRequestList(listLoadedFromJson);
        setAdapter(itemsForRequest);
        Gson gson = new Gson();
        String jsonString = gson.toJson(itemsForRequest);
        SettingsManager.setDataRequests(activity, jsonString);

        spinner.setSelection(itemsForRequest.getDataForRequestList().indexOf(dataForRequest));
    }

    private void setButtonClickListener() {
        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataFromEditTexts();
            }
        });

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
    }

    private void showData(@NonNull DataItemsForRequest.DataForRequest dataForRequest) {
        setTextAccessToken(dataForRequest.getAccessToken());
        setTextApiRoot(dataForRequest.getApiRoot());
        setTextApplicationId(dataForRequest.getApplicationId());
        setTextViewSpinnerText(getTextApplicationId());
    }

    private void setSpinnerItemSelectedListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataItemsForRequest.DataForRequest dataForRequest = itemsForRequest.getDataForRequestList().get(position);
                showData(dataForRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public interface ApiManagerInterface {
        void onClickDownloadPing();

        void onClickDownloadBroadcast();

        void onClickDownloadChannelList();

        void onClickDownloadSessions();
    }

    private static class DataItemsForRequest {

        private List<DataForRequest> dataForRequestList;

        public DataItemsForRequest() {
            setDataForRequestList(new ArrayList<DataForRequest>());
        }

        public List<DataForRequest> getDataForRequestList() {
            return dataForRequestList;
        }

        public void setDataForRequestList(List<DataForRequest> dataForRequestList) {
            this.dataForRequestList = dataForRequestList;
        }

        public static class DataForRequest {
            private String apiRoot;
            private String accessToken;
            private String applicationId;

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }

            public void setApiRoot(String apiRoot) {
                this.apiRoot = apiRoot;
            }

            public void setApplicationId(String applicationId) {
                this.applicationId = applicationId;
            }

            public String getAccessToken() {
                return accessToken;
            }

            public String getApiRoot() {
                return apiRoot;
            }

            public String getApplicationId() {
                return applicationId;
            }
        }
    }

}

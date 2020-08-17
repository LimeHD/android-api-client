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

public class ApiManagerBase {

    protected Activity activity;
    protected ApiManager.ApiManagerInterface apiManagerInterface;

    protected EditText editTextApiRoot;
    protected EditText editTextAccessToken;
    protected EditText editTextApplicationId;
    protected EditText editTextDeviceId;
    protected Button buttonSaveData;

    protected TextView textViewSpinnerText;
    protected Spinner spinner;

    protected EditText editTextAnswer;
    protected EditText editTextUrl;
    protected EditText editTextCurl;

    protected ImageButton buttonCopyTextAnswer;
    protected ImageButton buttonCopyTextUrl;
    protected ImageButton buttonCopyTextCurl;

    private String[] application_ids;

    private ApiManagerBase.DataItemsForRequest itemsForRequest = new ApiManagerBase.DataItemsForRequest();

    public ApiManagerBase(@NonNull Activity activity, @NonNull ApiManager.ApiManagerInterface apiManagerInterface) {
        this.activity = activity;
        this.apiManagerInterface = apiManagerInterface;
    }

    protected void initializationApiManagerBase() {
        application_ids = new String[0];
        findViewId();
        setButtonClickListener();
        setDeviceId(Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
        itemsForRequest = loadDataFromSettingsManager();
        setSpinnerItemSelectedListener();
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

    protected void findViewId() {
        textViewSpinnerText = activity.findViewById(R.id.text_view_spinner_text);
        spinner = activity.findViewById(R.id.spinner_application);

        editTextApiRoot = activity.findViewById(R.id.edit_text_api_root);
        editTextAccessToken = activity.findViewById(R.id.edit_text_access_token);
        editTextApplicationId = activity.findViewById(R.id.edit_text_application_id);
        editTextDeviceId = activity.findViewById(R.id.edit_text_device_id);
        buttonSaveData = activity.findViewById(R.id.button_save_data);

        editTextAnswer = activity.findViewById(R.id.edit_text_answer);
        editTextUrl = activity.findViewById(R.id.edit_text_url);
        editTextCurl = activity.findViewById(R.id.edit_text_curl);

        buttonCopyTextAnswer = activity.findViewById(R.id.button_copy_text_answer);
        buttonCopyTextUrl = activity.findViewById(R.id.button_copy_text_url);
        buttonCopyTextCurl = activity.findViewById(R.id.button_copy_text_curl);
    }

    protected void setButtonClickListener() {
        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataFromEditTexts();
            }
        });
        buttonCopyTextUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextFrom(editTextUrl);
            }
        });
        buttonCopyTextCurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextFrom(editTextCurl);
            }
        });
        buttonCopyTextAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextFrom(editTextAnswer);
            }
        });
    }

    private void copyTextFrom(EditText editText) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", editText.getText().toString());
        if(clipboard != null && clip != null)
            clipboard.setPrimaryClip(clip);
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

    private void showData(@NonNull DataItemsForRequest.DataForRequest dataForRequest) {
        setTextAccessToken(dataForRequest.getAccessToken());
        setTextApiRoot(dataForRequest.getApiRoot());
        setTextApplicationId(dataForRequest.getApplicationId());
        setTextViewSpinnerText(getTextApplicationId());
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


    protected static class DataItemsForRequest {

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

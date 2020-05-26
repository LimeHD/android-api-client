package tv.limehd.androidapiclient.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tv.limehd.androidapiclient.R;
import tv.limehd.androidapiclient.SettingsManager;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.AbstractViewHolder> {

    private String[] logsNames;
    private String[] logsValue;
    private String[] editTextNames;
    private Context context;

    private ViewHolderEditText[] viewHolderEditText;

    public LogsAdapter(Context context) {
        this.context = context;
        logsNames = new String[]{
                context.getResources().getString(R.string.answer),
                context.getResources().getString(R.string.curl),
                context.getResources().getString(R.string.url_request)
        };
        logsValue = new String[]{
                context.getResources().getString(R.string.answer),
                context.getResources().getString(R.string.curl),
                context.getResources().getString(R.string.url_request)
        };
        editTextNames = new String[]{
                context.getResources().getString(R.string.api_root),
                context.getResources().getString(R.string.x_access_token),
                context.getResources().getString(R.string.application_id),
        };
        viewHolderEditText = new ViewHolderEditText[3];
    }

    public String getApiRoot() {
        Log.e("Player111", "111: " + viewHolderEditText[0].editText.getText().toString());
        return viewHolderEditText[0].editText.getText().toString();
    }

    public String getXAccessToken() {
        return viewHolderEditText[1].editText.getText().toString();
    }

    public String getApplicationId() {
        return viewHolderEditText[2].editText.getText().toString();
    }

    public void setAnswer(String string) {
        if (logsValue != null)
            logsValue[0] = string;
    }

    public void setCurl(String string) {
        if (logsValue != null)
            logsValue[1] = string;
    }

    public void setUrl(String string) {
        if (logsValue != null)
            logsValue[2] = string;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position <= 2)
            return R.layout.item_recycler_edit_text;
        else if (position >= 3 && position <= 5)
            return R.layout.item_recycler_log;
        else return -1;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);
        AbstractViewHolder holder = null;
        switch (viewType) {
            case R.layout.item_recycler_log:
                holder = new ViewHolderLogs(view);
                break;
            case R.layout.item_recycler_edit_text:
                holder = new ViewHolderEditText(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AbstractViewHolder holder, final int position) {
        holder.bind(position);
    }

    private void copyToClipboard(EditText editText) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", editText.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "was copied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static abstract class AbstractViewHolder extends RecyclerView.ViewHolder {
        public AbstractViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(int position);
    }

    public class ViewHolderLogs extends AbstractViewHolder {
        private final int offsetPosition = 3;
        private ImageButton button;
        private EditText editText;
        private TextView text_name_log;

        public ViewHolderLogs(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button_copy);
            editText = itemView.findViewById(R.id.text_log);
            text_name_log = itemView.findViewById(R.id.text_name_log);
        }

        @Override
        void bind(int position) {
            position -= offsetPosition;
            text_name_log.setText(logsNames[position]);
            editText.setText(logsValue[position]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copyToClipboard(editText);
                }
            });
        }
    }

    public class ViewHolderEditText extends AbstractViewHolder {
        private final int offsetPosition = 0;
        private EditText editText;
        private TextView textName;

        public ViewHolderEditText(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.edit_text);
            textName = itemView.findViewById(R.id.text_name_edit_text);
        }

        @Override
        void bind(final int pos) {
            final int position = pos - offsetPosition;
            viewHolderEditText[position] = this;

            textName.setText(editTextNames[position]);
            if (position == 0) {
                editText.setText(SettingsManager.getApiRoot(context));
            } else if (position == 1) {
                editText.setText(SettingsManager.getXAccessToken(context));
            } else if (position == 2) {
                editText.setText(SettingsManager.getApplicationId(context));
            }
        }
    }
}

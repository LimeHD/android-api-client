package tv.limehd.androidapiclient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tv.limehd.androidapiclient.R;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private String[] functionNames;

    public ApiAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        functionNames = new String[]{
                context.getResources().getString(R.string.button_ping),
                context.getResources().getString(R.string.button_download_channel_list),
                context.getResources().getString(R.string.button_download_broadcast),
                context.getResources().getString(R.string.button_download_sessions),
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_recycler_button, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.button.setText(functionNames[position]);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (apiAdapterInterface != null) {
                    switch (position) {
                        case 0:
                            apiAdapterInterface.onClickPing();
                            break;
                        case 1:
                            apiAdapterInterface.onClickDownloadChannelList();
                            break;
                        case 2:
                            apiAdapterInterface.onClickDownloadBroadcast();
                            break;
                        case 3:
                            apiAdapterInterface.onClickDownloadSessions();
                            break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return functionNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button_save_data);
        }
    }

    public void setApiAdapterInterface(ApiAdapterInterface apiAdapterInterface) {
        this.apiAdapterInterface = apiAdapterInterface;
    }

    private ApiAdapterInterface apiAdapterInterface = null;

    public interface ApiAdapterInterface {
        void onClickPing();

        void onClickDownloadBroadcast();

        void onClickDownloadChannelList();

        void onClickDownloadSessions();
    }
}

package tv.limehd.androidapimodule.Download.Data;

import android.content.Context;

import java.io.File;

import tv.limehd.androidapimodule.Download.DownloadingBase;
import tv.limehd.androidapimodule.LimeCacheSettings;

public class Component {

    public static class DataBasic extends Component {

        private String scheme;
        private String apiRoot;
        private String endpoint;
        private String applicationId;
        private String xAccessToken;
        private String xTestIp;
        private boolean isUseSSL = false;

        public DataBasic(
                final String scheme,
                final String apiRoot,
                final String endpoint,
                final String applicationId,
                final String xAccessToken,
                final String xTestIp
        ) {
            this.scheme = scheme;
            this.apiRoot = apiRoot;
            this.endpoint = endpoint;
            this.applicationId = applicationId;
            this.xAccessToken = xAccessToken;
            this.xTestIp = xTestIp;
        }

        public String getScheme() {
            return scheme;
        }

        public String getApiRoot() {
            return apiRoot;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public String getxAccessToken() {
            return xAccessToken;
        }

        public String getxTestIp() {
            return xTestIp;
        }

        public boolean isUseSSL() {
            return isUseSSL;
        }
    }

    public static class DataCache extends Component {

        private Context context;
        private boolean isUseCache;
        private File cacheDir;

        public DataCache(Context context, boolean useCache, File cacheDir) {
            this.context = context;
            this.isUseCache = useCache;
            this.cacheDir = cacheDir;
        }

        public Context getContext() {
            return context;
        }

        public File getCacheDir() {
            return cacheDir;
        }

        public boolean isUseCache() {
            return isUseCache;
        }

        public int getMaxAgeCache(Class <? extends DownloadingBase> typeDownloading) {
            return LimeCacheSettings.getMaxAge(getContext(), typeDownloading);
        }

        public void saveMaxAgeCache(int maxAge, Class <? extends DownloadingBase> typeDownloading) {
            LimeCacheSettings.setMaxAge(getContext(), typeDownloading, maxAge);
        }
    }

    public static class DataPing extends Component {

    }

    public static class DataSession extends Component {

    }

    public static class DataBroadcast extends Component {

        private String timeZone;
        private String locale;
        private String channelId;
        private String beforeDate;
        private String afterDate;

        public DataBroadcast(
                final String timeZone,
                final String locale,
                final String channelId,
                final String beforeDate,
                final String afterDate
        ) {
            this.timeZone = timeZone;
            this.locale = locale;
            this.channelId = channelId;
            this.beforeDate = beforeDate;
            this.afterDate = afterDate;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public String getLocale() {
            return locale;
        }

        public String getChannelId() {
            return channelId;
        }

        public String getBeforeDate() {
            return beforeDate;
        }

        public String getAfterDate() {
            return afterDate;
        }
    }

    public static class DataChannelList extends Component {

        private String timeZone;
        private String locale;
        private String channelGroupId;

        public DataChannelList(
                final String timeZone,
                final String locale,
                final String channelGroupId
        ) {
            this.timeZone = timeZone;
            this.locale = locale;
            this.channelGroupId = channelGroupId;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public String getLocale() {
            return locale;
        }

        public String getChannelGroupId() {
            return channelGroupId;
        }
    }

    public static class DataDeepClick extends Component {
        private String query;
        private String path;
        private String deviceId;

        public DataDeepClick(
                final String query,
                final String path,
                final String deviceId
        ) {
            this.query = query;
            this.path = path;
            this.deviceId = deviceId;
        }

        public String getQuery() {
            return query;
        }

        public String getPath() {
            return path;
        }

        public String getDeviceId() {
            return deviceId;
        }
    }
}

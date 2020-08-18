package tv.limehd.androidapimodule.Download.Data;

public class Component {

    public static class DataBasic extends Component {

        private String scheme;
        private String apiRoot;
        private String endpoint;
        private String applicationId;
        private String xAccessToken;
        private String xTestIp;
        private boolean isUseCache;

        public DataBasic(
                final String scheme,
                final String apiRoot,
                final String endpoint,
                final String applicationId,
                final String xAccessToken,
                final String xTestIp,
                final boolean isUseCache
        ) {
            this.scheme = scheme;
            this.apiRoot = apiRoot;
            this.endpoint = endpoint;
            this.applicationId = applicationId;
            this.xAccessToken = xAccessToken;
            this.xTestIp = xTestIp;
            this.isUseCache = isUseCache;
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

        public boolean isUseCache() {
            return isUseCache;
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

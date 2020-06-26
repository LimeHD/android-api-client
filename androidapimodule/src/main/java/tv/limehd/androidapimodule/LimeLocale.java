package tv.limehd.androidapimodule;

import java.util.Locale;

public class LimeLocale {

    public static String getLocaleTag(Locale locale) {
        String tempLocale = locale.toString();
        return tempLocale.replace("_", "-");
    }

}

package tv.limehd.androidapimodule;

public class LimeUTC {

    //Example input time zone HOUR
    public static String oneHourToUtcFormat(String time_zone) {
        String default_return_value = "UTC+00:00";
        if (time_zone != null && time_zone.length() > 0) {
            String sign = "+";
            String time_zone_without_symbol = time_zone;
            try {
                int number = Integer.parseInt(time_zone);
                if (number < 0) {
                    sign = "-";
                    time_zone_without_symbol = String.valueOf(number*(-1));
                }
            } catch (Exception e) {
                return default_return_value;
            }
            if (time_zone_without_symbol.length() == 1) {
                return "UTC" + sign + "0" + time_zone_without_symbol + ":00";
            } else {
                return "UTC" + sign + time_zone_without_symbol + ":00";
            }
        } else {
            return default_return_value;
        }
    }

}

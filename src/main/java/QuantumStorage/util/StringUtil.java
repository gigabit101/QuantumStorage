package QuantumStorage.util;

/**
 * Created by modmuss50 on 19/09/2016.
 */
public class StringUtil {

    public static String getRoundedString(double euValue, String units) {
        if (euValue >= 1000000000) {
            double tenX = Math.round(euValue / 1000000000);
            return Double.toString(tenX / 10.0).concat(" B " + units);
        }else if (euValue >= 1000000) {
            double tenX = Math.round(euValue / 100000);
            return Double.toString(tenX / 10.0).concat(" M " + units);
        } else if (euValue >= 1000) {
            double tenX = Math.round(euValue / 100);
            return Double.toString(tenX / 10.0).concat(" K " + units);
        } else {
            return Double.toString(Math.floor(euValue)).concat(" " + units);
        }
    }
}

package tech.appzilla.com.setmydate.Utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by Rohit Singh$ on 11/06/2019.
 */
public class GpsUtils {
    //Check GPS Status true/false
    public static boolean checkGPSStatus(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    };
}

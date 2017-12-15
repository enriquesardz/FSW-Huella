package com.fime.fsw.huella.huella.Utilidad;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/**
 * Created by ensardz on 14/12/17.
 * Clase auxiliar para diferentes metodos usados en la aplicacion.
 */

public class Utils {
    /**
     * Metodo que obtiene la mac address del dispositivo, este metodo solamente este disponible para versiones
     * anteriores a Android 6.0. La mac address solamente se obtendra si el Wi-Fi esta activado.
     * @param context Context de la activity que llama al metodo.
     * @return String con la mac address del dispositivo.
     */
    public static String getMacAddress(Context context){
        String macAddress = null;
        WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        macAddress = wm.getConnectionInfo().getMacAddress();
        if(TextUtils.isEmpty(macAddress)){
            macAddress = "No mac address found.";
        }
        return macAddress;
    }
}

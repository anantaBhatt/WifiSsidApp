package com.wifissidapp

import android.content.Context
import android.net.wifi.WifiManager
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class WifiModule(private val reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "WifiModule"
    }

    @ReactMethod
    fun getSsid(promise: Promise) {
        try {
            val wifiManager = reactContext.applicationContext
                .getSystemService(Context.WIFI_SERVICE) as WifiManager

            val wifiInfo = wifiManager.connectionInfo
            val ssid = wifiInfo.ssid

            if (ssid == null || ssid == WifiManager.UNKNOWN_SSID || ssid == "<unknown ssid>") {
                promise.reject(
                    "NO_SSID",
                    "SSID is unknown. Make sure WiFi is connected and Location is ON."
                )
                return
            }

            promise.resolve(ssid)
        } catch (e: SecurityException) {
            promise.reject("NO_PERMISSION", "Missing location or WiFi permission: ${e.message}")
        } catch (e: Exception) {
            promise.reject("ERROR", e.message)
        }
    }
}

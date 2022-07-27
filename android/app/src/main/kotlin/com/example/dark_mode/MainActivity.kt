package com.enesmuratuzun.dark_mode

import android.app.UiModeManager
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val DARK_MODE_CHANNEL = "enesmuratuzun.com/darkMode"
    private lateinit var channel: MethodChannel
    private var uiModeManager: UiModeManager? = null

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager

        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, DARK_MODE_CHANNEL)

        channel.setMethodCallHandler { call, result ->
            if (call.method == "getDarkMode") {
                result.success(uiModeManager?.nightMode)
            }
            if (call.method == "setDarkMode") {
                val arguments = call.arguments() as Map<String, Int>?
                if (arguments != null) {
                    val darkMode = arguments["darkMode"]
                    if (darkMode != null) {
                        setDarkMode(darkMode)
                    }
                    result.success("Success")
                }
            }
        }
    }
    private fun setDarkMode(value: Int) {
        if (value == 1) uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_NO
        else uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_YES
    }
}

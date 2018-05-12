package cz.vutbr.fit.openmrdp.openmrdpdemoapp;

import android.util.Log;

import cz.vutbr.fit.openmrdp.logger.MrdpLogger;

public class MrdpAndroidLogger implements MrdpLogger {
    @Override
    public void logDebug(String s) {
        Log.d("logDebug", s);
    }

    @Override
    public void logInfo(String s) {
        Log.d("logDebug", s);
    }

    @Override
    public void logError(String s) {
        Log.d("logDebug", s);
    }
}

package cz.vutbr.fit.openmrdp.openmrdpdemoapp;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

import cz.vutbr.fit.openmrdp.api.OpenmRDPClientAPI;
import cz.vutbr.fit.openmrdp.exceptions.NetworkCommunicationException;
import cz.vutbr.fit.openmrdp.exceptions.QuerySyntaxException;
import cz.vutbr.fit.openmrdp.logger.MrdpLogger;

class OpenMRDPApiTask extends AsyncTask<String, Void, String> {

    private final MrdpLogger logger = new MrdpAndroidLogger();
    private final OpenmRDPClientAPI openmRDPClientAPI;

    OpenMRDPApiTask(OpenmRDPClientAPI openmRDPClientAPI) {
        this.openmRDPClientAPI = openmRDPClientAPI;

    }

    @Nullable
    @Override
    protected String doInBackground(String... resourceNames) {
        String operation = resourceNames[0];
        String resourceLocation;
        if (operation.equals("LOCATE")) {
            if (resourceNames.length == 2) {
                resourceLocation = executeNonSecureLocateTask(resourceNames[1]);
            } else {
                resourceLocation = executeSecureLocateTask(resourceNames[1], resourceNames[2], resourceNames[3]);
            }
        } else {
            if (resourceNames.length == 2) {
                resourceLocation = executeNonSecureIdentifyTask(resourceNames[1]);
            } else {
                resourceLocation = executeSecureIdentifyTask(resourceNames[1], resourceNames[2], resourceNames[3]);
            }
        }

        return resourceLocation;
    }

    @Nullable
    private String executeSecureIdentifyTask(String resourceName, String login, String password) {
        String resourceLocation = null;

        String authorizeHash = login + ":" + password;
        String base64 = null;
        try {
            byte[] data = authorizeHash.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            logger.logError(e.getMessage());
        }

        try {
            resourceLocation = openmRDPClientAPI.identifyResource(resourceName, base64);
        } catch (NetworkCommunicationException | QuerySyntaxException e) {
            logger.logError(e.getMessage());
        }

        return resourceLocation;
    }

    @Nullable
    private String executeNonSecureIdentifyTask(String resourceName) {
        String resourceLocation = null;
        try {
            resourceLocation = openmRDPClientAPI.identifyResource(resourceName);
        } catch (NetworkCommunicationException | QuerySyntaxException e) {
            logger.logError(e.getMessage());
        }

        return resourceLocation;
    }

    @Nullable
    private String executeSecureLocateTask(String resourceName, String login, String password) {
        String resourceLocation = null;

        String authorizeHash = login + ":" + password;
        String base64 = null;
        try {
            byte[] data = authorizeHash.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            logger.logError(e.getMessage());
        }

        try {
            resourceLocation = openmRDPClientAPI.locateResource(resourceName, base64);
        } catch (NetworkCommunicationException e) {
            logger.logError(e.getMessage());
        }

        return resourceLocation;
    }

    @Nullable
    private String executeNonSecureLocateTask(String resourceName) {
        String resourceLocation = null;
        try {
            resourceLocation = openmRDPClientAPI.locateResource(resourceName);
        } catch (NetworkCommunicationException e) {
            logger.logError(e.getMessage());
        }
        return resourceLocation;
    }
}

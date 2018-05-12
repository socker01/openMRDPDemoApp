package cz.vutbr.fit.openmrdp.openmrdpdemoapp;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import cz.vutbr.fit.openmrdp.api.OpenmRDPClientAPI;
import cz.vutbr.fit.openmrdp.exceptions.NetworkCommunicationException;

//TODO rename to OpenMRDPApiTask
class OpenMRDPApi extends AsyncTask<String, Void, String>{

    private final OpenmRDPClientAPI openmRDPClientAPI;

    OpenMRDPApi(OpenmRDPClientAPI openmRDPClientAPI) {
        this.openmRDPClientAPI = openmRDPClientAPI;
    }

    @Nullable
    @Override
    protected String doInBackground(String... resourceNames) {
        String resourceLocation = null;
        try {
            resourceLocation = openmRDPClientAPI.locateResource(resourceNames[0]);
            Log.i("result", resourceLocation);
        } catch (NetworkCommunicationException e) {
            e.printStackTrace();
        }

        return resourceLocation;
    }
}

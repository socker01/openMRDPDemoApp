package cz.vutbr.fit.openmrdp.openmrdpdemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;

import cz.vutbr.fit.openmrdp.api.OpenmRDPClientAPI;
import cz.vutbr.fit.openmrdp.api.OpenmRDPClientApiImpl;
import cz.vutbr.fit.openmrdp.logger.MrdpLogger;

public class LocateActivity extends AppCompatActivity {

    private OpenmRDPClientAPI api;
    private MrdpLogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_activity);

        initializeLocateButton();
        logger = new MrdpAndroidLogger();
    }

    private String getLocalIpAddress() throws Exception {
        String resultIpv6 = "";
        String resultIpv4 = "";

        for (Enumeration en = NetworkInterface.getNetworkInterfaces();
             en.hasMoreElements(); ) {

            NetworkInterface intf = (NetworkInterface) en.nextElement();
            for (Enumeration enumIpAddr = intf.getInetAddresses();
                 enumIpAddr.hasMoreElements(); ) {

                InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                if (!inetAddress.isLoopbackAddress()) {
                    if (inetAddress instanceof Inet4Address) {
                        resultIpv4 = inetAddress.getHostAddress();
                    } else if (inetAddress instanceof Inet6Address) {
                        resultIpv6 = inetAddress.getHostAddress();
                    }
                }
            }
        }

        return ((resultIpv4.length() > 0) ? resultIpv4 : resultIpv6);
    }

    private void initializeLocateButton() {
        final Button locateButton = findViewById(R.id.send_locate_btn);
        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocateMessage();
            }
        });
    }

    private void sendLocateMessage() {

        try {
            api = new OpenmRDPClientApiImpl(getLocalIpAddress() + ":27741/", new MrdpAndroidLogger());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EditText resourceToFind = findViewById(R.id.locate_resource_name_et);

        String responseString = null;

        try {
            responseString = new OpenMRDPApi(api).execute(resourceToFind.getText().toString()).get();
        } catch (InterruptedException | ExecutionException e) {
            logger.logError(e.getMessage());
        }

        final Intent intent = new Intent(this, LocateResultActivity.class);
        intent.putExtra("RESULT", responseString);

        startActivity(intent);
    }
}

package cz.vutbr.fit.openmrdp.openmrdpdemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import cz.vutbr.fit.openmrdp.api.OpenmRDPClientAPI;
import cz.vutbr.fit.openmrdp.api.OpenmRDPClientApiImpl;
import cz.vutbr.fit.openmrdp.logger.MrdpLogger;

public class IdentifyActivity extends AppCompatActivity {

    private OpenmRDPClientAPI api;
    private MrdpLogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify_activity);

        initializeIdentifyButton();
        logger = new MrdpAndroidLogger();
    }

    private void initializeIdentifyButton() {
        final Button locateButton = findViewById(R.id.send_identify_btn);
        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIdentifyMessage();
            }
        });
    }

    private void sendIdentifyMessage() {
        try {
            api = new OpenmRDPClientApiImpl(LocalAddressRetriever.getLocalIpAddress() + ":27741/", logger, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EditText queryToIdentify = findViewById(R.id.identify_resource_name_et);
        EditText userNameET = findViewById(R.id.identify_login_et);
        String userName = userNameET.getText().toString();
        EditText passwordET = findViewById(R.id.identify_password_et);
        String password = passwordET.getText().toString();

        if ((!userName.equals("") && password.equals(""))
                || (userName.equals("") && !password.equals(""))) {
            Toast.makeText(getApplicationContext(), "You must enter login and password.", Toast.LENGTH_LONG)
                    .show();

            return;
        }

        String responseString = null;

        try {
            if (userName.equals("")) {
                responseString = new OpenMRDPApiTask(api).execute("IDENTIFY", queryToIdentify.getText().toString()).get();
            } else {
                responseString = new OpenMRDPApiTask(api).execute("IDENTIFY",
                        queryToIdentify.getText().toString(),
                        userName,
                        password).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.logError(e.getMessage());
        }

        if (responseString != null) {
            final Intent intent = new Intent(this, IdentifyResultActivity.class);
            intent.putExtra("RESULT", responseString);

            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Servers don't have any information about this query.", Toast.LENGTH_LONG)
                    .show();
        }
    }
}

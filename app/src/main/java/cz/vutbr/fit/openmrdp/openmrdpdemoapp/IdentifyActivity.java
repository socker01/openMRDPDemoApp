package cz.vutbr.fit.openmrdp.openmrdpdemoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class IdentifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify_activity);

        initializeIdentifyButton();
    }

    private void initializeIdentifyButton(){
        final Button locateButton = findViewById(R.id.send_identify_btn);
        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIdentifyMessage();
            }
        });
    }

    private void sendIdentifyMessage() {
        //TODO: implement me
    }
}

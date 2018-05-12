package cz.vutbr.fit.openmrdp.openmrdpdemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LocateResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_result);

        Intent intent = getIntent();
        String result = intent.getStringExtra("RESULT");

        TextView resultTextView = findViewById(R.id.resource_location_result_tw);
        resultTextView.setText(result);
    }
}

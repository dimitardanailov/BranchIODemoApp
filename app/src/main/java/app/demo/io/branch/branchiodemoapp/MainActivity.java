package app.demo.io.branch.branchiodemoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mGreetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetings = (TextView) findViewById(R.id.greetings);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Branch init
        Branch.getInstance().initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
                    // params will be empty if no data found
                    // ... insert custom logic here ...
                    Log.d(TAG, referringParams.toString());
                    mGreetings.setText("Success: " + referringParams.toString());
                    // Log.d(TAG, "Branch io success");
                } else {
                    Log.d(TAG, "Branch io error");
                    Log.d(TAG, error.getMessage());

                    mGreetings.setText("Error: " + error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

}

package ehr.cfcs.com.ehr.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.GPSTracker;

public class LoginActivity extends AppCompatActivity {

    public Button loginBtn;
    public Context mContext;
    public ConnectionDetector conn;
    public GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        loginBtn = (Button)findViewById(R.id.loginbtn);
        mContext = LoginActivity.this;
        conn = new ConnectionDetector(LoginActivity.this);
        gps = new GPSTracker(mContext,LoginActivity.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkGPS();
            }
        });

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


    }

    public void  checkGPS()
    {  if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    } else {
        // Toast.makeText(mContext, "You need have granted permission", Toast.LENGTH_SHORT).show();
        if (conn.getConnectivityStatus() > 0) {
            if (gps.canGetLocation()) {

                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                finish();

            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();

            }
        }else
        {
            conn.showNoInternetAlret();
        }
    }

    }

}

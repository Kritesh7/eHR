package ehr.cfcs.com.ehr.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class ViewAttendanceDetailsActivity extends AppCompatActivity {

    public TextView titleTxt, empNmaeTxt, inTimeDateTxt, empIdTxt, inTimeTxt, outTimeTxt, durationTxt, halfDayTxt,
                    lateArivalTxt, earlyLeavingTxt, statusTxt;
    public Button updateBtn;
    public String viewDetailsUrl = SettingConstant.BaseUrl + "AppEmployeeAttendanceDetail";
    public ConnectionDetector conn;
    public String authcode = "", isRequest = "", attendaceLogId= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance_details);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.viewleavtoolbar);
        setSupportActionBar(toolbar);

        titleTxt = (TextView)toolbar.findViewById(R.id.titletxt);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
                onBackPressed();

            }
        });

        titleTxt.setText("Update Attendance Details");

        Intent intent = getIntent();
        if (intent != null)
        {

            attendaceLogId = intent.getStringExtra("AttendnaceLogId");
           // isRequest = intent.getStringExtra("Visibile");


        }

        conn = new ConnectionDetector(ViewAttendanceDetailsActivity.this);
        authcode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ViewAttendanceDetailsActivity.this)));

        //find widget
        updateBtn = (Button)findViewById(R.id.updatedetail);
        empNmaeTxt = (TextView)findViewById(R.id.empname);
        inTimeDateTxt = (TextView)findViewById(R.id.intimedate);
       // outTimeEditTxt = (TextView)findViewById(R.id.outtimedate);
        inTimeTxt = (TextView)findViewById(R.id.intime);
        outTimeTxt = (TextView)findViewById(R.id.outtime);
        durationTxt = (TextView)findViewById(R.id.duration);
        halfDayTxt = (TextView)findViewById(R.id.halfday);
        lateArivalTxt = (TextView)findViewById(R.id.latearival);
        earlyLeavingTxt = (TextView)findViewById(R.id.earlylearning);
        statusTxt = (TextView)findViewById(R.id.status);
        empIdTxt = (TextView)findViewById(R.id.empid);


        if (conn.getConnectivityStatus()>0)
        {
            viewDetails(authcode,attendaceLogId);
        }else
            {
                conn.showNoInternetAlret();
            }




    }

    //View Attendance Details
    public void viewDetails(final String AuthCode , final String LeaveApplicationID ) {


        final ProgressDialog pDialog = new ProgressDialog(ViewAttendanceDetailsActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, viewDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        // String status = jsonObject.getString("status");
                        if (jsonObject.has("MsgNotification"))
                        {
                            String MsgNotification = jsonObject.getString("MsgNotification");
                            Toast.makeText(ViewAttendanceDetailsActivity.this, MsgNotification, Toast.LENGTH_SHORT).show();

                        }

                        String Name = jsonObject.getString("Name");
                        String AttendanceDateText = jsonObject.getString("AttendanceDateText");
                        String InTime = jsonObject.getString("InTime");
                        String OutTime = jsonObject.getString("OutTime");
                        String InOutDuration = jsonObject.getString("InOutDuration");
                        String Halfday = jsonObject.getString("Halfday");
                        String LateArrival = jsonObject.getString("LateArrival");
                        String EarlyLeaving = jsonObject.getString("EarlyLeaving");
                        String Status = jsonObject.getString("Status");
                        String isRequest = jsonObject.getString("IsRequest");
                        String EmpID = jsonObject.getString("EmpID");


                        empNmaeTxt.setText(Name);
                        inTimeDateTxt.setText(AttendanceDateText);
                        inTimeTxt.setText(InTime);
                        outTimeTxt.setText(OutTime);
                        durationTxt.setText(InOutDuration);
                        statusTxt.setText(Status);
                        halfDayTxt.setText(Halfday);
                        lateArivalTxt.setText(LateArrival);
                        earlyLeavingTxt.setText(EarlyLeaving);
                        empIdTxt.setText(EmpID);

                        //hide button
                        if (isRequest.equalsIgnoreCase("0"))
                        {
                            updateBtn.setVisibility(View.GONE);
                        }


                    }

                    pDialog.dismiss();

                } catch (JSONException e) {
                    Log.e("checking json excption" , e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Login", "Error: " + error.getMessage());
                // Log.e("checking now ",error.getMessage());

                Toast.makeText(ViewAttendanceDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("AuthCode",AuthCode);
                params.put("AttendanceLogID",LeaveApplicationID);


                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }

}

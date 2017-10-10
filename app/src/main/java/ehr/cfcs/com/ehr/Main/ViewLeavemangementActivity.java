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

public class ViewLeavemangementActivity extends AppCompatActivity {

    public TextView titleTxt;
    public String LeaveApplication_Id = "",authCode = "";
    public TextView leaveTypeTxt, startDateTxt,endDateTxt,numberofDaysTxt, appliedOnTxt, statusTxt,commentByMangerTxt, managerCommentedOn,
                    commentByHrTxt, hrCommentedOnTxt;
    public String viewDetailsUrl = SettingConstant.BaseUrl + "AppEmployeeLeaveDetail";
    public ConnectionDetector conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_leavemangement);

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

        titleTxt.setText("Leave Details");

        Intent intent = getIntent();
        if (intent != null)
        {
            LeaveApplication_Id = intent.getStringExtra("LeaveApplication_Id");
        }


        conn = new ConnectionDetector(ViewLeavemangementActivity.this);
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ViewLeavemangementActivity.this)));

        //find id
        leaveTypeTxt = (TextView)findViewById(R.id.leavetype);
        startDateTxt = (TextView)findViewById(R.id.startdate);
        endDateTxt = (TextView)findViewById(R.id.enddate);
        numberofDaysTxt = (TextView)findViewById(R.id.numberofdays);
        appliedOnTxt = (TextView)findViewById(R.id.appliedon);
        statusTxt = (TextView)findViewById(R.id.status);
        commentByMangerTxt = (TextView)findViewById(R.id.commentbymanger);
        managerCommentedOn = (TextView)findViewById(R.id.managercommentedon);
        commentByHrTxt = (TextView)findViewById(R.id.commentedbyhr);
        hrCommentedOnTxt = (TextView)findViewById(R.id.hrcommentedon);

        if (conn.getConnectivityStatus()>0)
        {
            viewDetails(authCode,LeaveApplication_Id);
        }else
            {
                conn.showNoInternetAlret();
            }



    }

    //view Details Api
    public void viewDetails(final String AuthCode , final String LeaveApplicationID ) {


        final ProgressDialog pDialog = new ProgressDialog(ViewLeavemangementActivity.this,R.style.AppCompatAlertDialogStyle);
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
                            Toast.makeText(ViewLeavemangementActivity.this, MsgNotification, Toast.LENGTH_SHORT).show();

                        }

                        String LeaveTypeName = jsonObject.getString("LeaveTypeName");
                        String StartDateText = jsonObject.getString("StartDateText");
                        String EndDateText = jsonObject.getString("EndDateText");
                        String Noofdays = jsonObject.getString("Noofdays");
                        String AppliedDate = jsonObject.getString("AppliedDate");
                        String StatusText = jsonObject.getString("StatusText");
                        String ManagerComment = jsonObject.getString("ManagerComment");
                        String ManagerDateText = jsonObject.getString("ManagerDateText");
                        String HRComment = jsonObject.getString("HRComment");
                        String HRDateText = jsonObject.getString("HRDateText");


                        leaveTypeTxt.setText(LeaveTypeName);
                        startDateTxt.setText(StartDateText);
                        endDateTxt.setText(EndDateText);
                        numberofDaysTxt.setText(Noofdays);
                        appliedOnTxt.setText(AppliedDate);
                        statusTxt.setText(StatusText);
                        commentByMangerTxt.setText(ManagerComment);
                        managerCommentedOn.setText(ManagerDateText);
                        commentByHrTxt.setText(HRComment);
                        hrCommentedOnTxt.setText(HRDateText);


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

                Toast.makeText(ViewLeavemangementActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("AuthCode",AuthCode);
                params.put("LeaveApplicationID",LeaveApplicationID);


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

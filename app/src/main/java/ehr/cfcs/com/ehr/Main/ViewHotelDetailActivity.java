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

import ehr.cfcs.com.ehr.Model.CabItemModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class ViewHotelDetailActivity extends AppCompatActivity {

    public TextView titleTxt,employeNameTxt,hotelNameTxt,cityNameTxt,statusTxt,requestDateTxt,approvalDateTxt,hrCommTxt,empCommTxt,
                    checkInDateTxt,checkInTimeTxt, checkOutDateTxt;
    public String hotelDetailsUrl = SettingConstant.BaseUrl + "AppEmployeeHotelBookingDetail";
    public String deleteUrl = SettingConstant.BaseUrl + "AppEmployeeHotelBookingDelete";
    public String bidString = "",authcode = "";
    public ConnectionDetector conn;
    public Button delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel_detail);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.viewhoteltoolbar);
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

        titleTxt.setText("Hotel Details");

        Intent intent = getIntent();
        if (intent != null)
        {
            bidString = intent.getStringExtra("Bid");
        }

        conn = new ConnectionDetector(ViewHotelDetailActivity.this);
        authcode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ViewHotelDetailActivity.this)));

        employeNameTxt = (TextView) findViewById(R.id.hotel_empname);
        hotelNameTxt = (TextView) findViewById(R.id.hotel_name);
        cityNameTxt = (TextView) findViewById(R.id.hotel_cityname);
        requestDateTxt = (TextView) findViewById(R.id.hotel_request_date);
        approvalDateTxt = (TextView) findViewById(R.id.hotel_approvaldate);
        statusTxt = (TextView) findViewById(R.id.hotel_status);
        checkInDateTxt = (TextView) findViewById(R.id.hotel_checkindate);
        checkInTimeTxt = (TextView) findViewById(R.id.hotel_checkintime);
        checkOutDateTxt = (TextView) findViewById(R.id.hotel_checkoutdate);
        empCommTxt = (TextView) findViewById(R.id.hotel_employcomment);
        hrCommTxt = (TextView) findViewById(R.id.hotel_hr_comment);
        delBtn = (Button) findViewById(R.id.deleteBtn);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (conn.getConnectivityStatus()>0)
                {
                    deleteMethod(authcode,bidString);
                }else
                {
                    conn.showNoInternetAlret();
                }
            }
        });


        if (conn.getConnectivityStatus()>0)
        {
            viewHotelDetails(authcode,bidString);
        }else
            {
                conn.showNoInternetAlret();
            }
    }


    //View Hotel Details
    public void viewHotelDetails(final String AuthCode ,final String BID) {

        final ProgressDialog pDialog = new ProgressDialog(ViewHotelDetailActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, hotelDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String EmpName = object.getString("EmpName");
                        String requestDate = object.getString("AddDateText");
                        String approvedBy = object.getString("AppDateText");
                        String HrComment = object.getString("HrComment");
                        String AppStatusText = object.getString("AppStatusText");
                        String CityName = object.getString("CityName");
                        String CheckInDateText = object.getString("CheckInDateText");
                        String EmpComment = object.getString("EmpRemark");
                        String CheckOutDateText = object.getString("CheckOutDateText");
                        String CheckInTime = object.getString("CheckInTime");
                        String HotelName = object.getString("HotelName");

                        employeNameTxt.setText(EmpName);
                        empCommTxt.setText(EmpComment);
                        requestDateTxt.setText(requestDate);
                        approvalDateTxt.setText(approvedBy);
                        hrCommTxt.setText(HrComment);
                        statusTxt.setText(AppStatusText);
                        cityNameTxt.setText(CityName);
                        checkInTimeTxt.setText(CheckInTime);
                        checkInDateTxt.setText(CheckInDateText);
                        checkOutDateTxt.setText(CheckOutDateText);
                        hotelNameTxt.setText(HotelName);


                        /*if (HrComment.equalsIgnoreCase("") ||  HrComment.equalsIgnoreCase("null") )
                        {
                            hrCommTxt.setVisibility(View.GONE);
                            hrcommentFontTxt.setVisibility(View.GONE);

                        }else if (approvedBy.equalsIgnoreCase("") || approvedBy.equalsIgnoreCase("null"))
                        {
                            approvalDateTxt.setVisibility(View.GONE);
                            followDateTxt.setVisibility(View.GONE);
                        }*/


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

                Toast.makeText(ViewHotelDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("BID",BID);


                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }

    //delete the Details
    public void deleteMethod(final String AuthCode ,final String BID) {

        final ProgressDialog pDialog = new ProgressDialog(ViewHotelDetailActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, deleteUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                    if (jsonObject.has("status"))
                    {
                        String status = jsonObject.getString("status");

                        if (status.equalsIgnoreCase("success"))
                        {
                            onBackPressed();
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

                Toast.makeText(ViewHotelDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("BID",BID);

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

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ehr.cfcs.com.ehr.Adapter.CabItemsAdapter;
import ehr.cfcs.com.ehr.Adapter.RequestedItemAdapter;
import ehr.cfcs.com.ehr.Model.BookMeaPrevisionModel;
import ehr.cfcs.com.ehr.Model.CabItemModel;
import ehr.cfcs.com.ehr.Model.RequestItemModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class ViewCabDetailsActivity extends AppCompatActivity {

    public TextView titleTxt,empNameTxt,requestDateTxt,approvedByTxt,bookDateTxt,cityNameTxt,statusTxt,empcommTxt,hrcommTxt,
                     followDateTxt,hrcommentFontTxt;
    public String cabDetailsUrl = SettingConstant.BaseUrl + "AppEmployeeTaxiBookingRequestDetail";
    public String deleteUrl = SettingConstant.BaseUrl + "AppEmployeeTaxiRequestDelete";
    public ConnectionDetector conn;
    public String bidString = "",authCode = "";
    public ehr.cfcs.com.ehr.Source.MyListLayout cabItemList;
    public CabItemsAdapter adapter;
    public ArrayList<CabItemModel> list = new ArrayList<>();
    public Button deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cab_details);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.viewcabtoolbar);
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

        titleTxt.setText("Cab Details");

        Intent intent = getIntent();
        if (intent != null)
        {
            bidString = intent.getStringExtra("Bid");
        }

        conn = new ConnectionDetector(ViewCabDetailsActivity.this);
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ViewCabDetailsActivity.this)));

        empNameTxt = (TextView)findViewById(R.id.cab_empname);
        requestDateTxt = (TextView)findViewById(R.id.cab_request_date);
        approvedByTxt = (TextView)findViewById(R.id.cab_request);
        bookDateTxt = (TextView)findViewById(R.id.cab_bookdate);
        cityNameTxt = (TextView)findViewById(R.id.cab_cityname);
        statusTxt = (TextView)findViewById(R.id.cab_status);
        empcommTxt = (TextView)findViewById(R.id.cab_employcomment);
        hrcommTxt = (TextView)findViewById(R.id.cab_hr_comment);
        followDateTxt = (TextView)findViewById(R.id.followdatetxt);
        hrcommentFontTxt = (TextView)findViewById(R.id.hrcommenttxt);
        cabItemList = (ehr.cfcs.com.ehr.Source.MyListLayout ) findViewById(R.id.cab_item_list);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        adapter = new CabItemsAdapter(ViewCabDetailsActivity.this,list);

        cabItemList.setAdapter(adapter);

        //show details of cab
        if (conn.getConnectivityStatus()>0) {

            viewCabDetails(authCode,bidString);

        }else
            {
                conn.showNoInternetAlret();
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (conn.getConnectivityStatus()>0) {

                        deleteMethod(authCode,bidString);

                    }else
                    {
                        conn.showNoInternetAlret();
                    }

                }
            });

    }

    //view cab details
    public void viewCabDetails(final String AuthCode ,final String BID) {

        final ProgressDialog pDialog = new ProgressDialog(ViewCabDetailsActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, cabDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                    JSONArray requestDetailsArray = jsonObject.getJSONArray("TaxiBookingMaster");
                    for (int i = 0; i<requestDetailsArray.length(); i++)
                    {
                        JSONObject object = requestDetailsArray.getJSONObject(i);

                        String EmpName = object.getString("EmpName");
                        String requestDate = object.getString("AddDateText");
                        String approvedBy = object.getString("AppDateText");
                        String HrComment = object.getString("HrComment");
                        String AppStatusText = object.getString("AppStatusText");
                        String CityName = object.getString("CityName");
                        String BookDateText = object.getString("BookDateText");
                        String EmpComment = object.getString("EmpComment");
                        //String BookDateText = object.getString("BookDateText");

                        empNameTxt.setText(EmpName);
                        empcommTxt.setText(EmpComment);
                        requestDateTxt.setText(requestDate);
                        approvedByTxt.setText(approvedBy);
                        hrcommTxt.setText(HrComment);
                        statusTxt.setText(AppStatusText);
                        cityNameTxt.setText(CityName);
                        bookDateTxt.setText(BookDateText);


                        if (HrComment.equalsIgnoreCase("") ||  HrComment.equalsIgnoreCase("null") )
                        {
                            hrcommTxt.setVisibility(View.GONE);
                            hrcommentFontTxt.setVisibility(View.GONE);

                        }else if (approvedBy.equalsIgnoreCase("") || approvedBy.equalsIgnoreCase("null"))
                        {
                            approvedByTxt.setVisibility(View.GONE);
                            followDateTxt.setVisibility(View.GONE);
                        }


                    }

                    JSONArray itemdetaislArray = jsonObject.getJSONArray("TaxiBookingDetail");
                    if (list.size()>0)
                    {
                        list.clear();
                    }
                    for (int j=0 ; j<itemdetaislArray.length();j++)
                    {
                        JSONObject object = itemdetaislArray.getJSONObject(j);

                        String BookTime = object.getString("BookTime");
                        String SourceAdd = object.getString("SourceAdd");
                        String DestinationAdd = object.getString("DestinationAdd");


                        list.add(new CabItemModel(BookTime,SourceAdd,DestinationAdd));



                    }


                    adapter.notifyDataSetChanged();
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

                Toast.makeText(ViewCabDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        final ProgressDialog pDialog = new ProgressDialog(ViewCabDetailsActivity.this,R.style.AppCompatAlertDialogStyle);
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

                Toast.makeText(ViewCabDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

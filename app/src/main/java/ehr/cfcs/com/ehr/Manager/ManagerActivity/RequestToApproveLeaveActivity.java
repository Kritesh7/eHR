package ehr.cfcs.com.ehr.Manager.ManagerActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ehr.cfcs.com.ehr.Manager.ManagerAdapter.ManagerLeaveRequestApproveAndRejectAdapter;
import ehr.cfcs.com.ehr.Manager.ManagerAdapter.ManagerRequestToApprovedShortLeaveAdapter;
import ehr.cfcs.com.ehr.Manager.ManagerModel.ManagerLeaveRequestApproveAndRejectModel;
import ehr.cfcs.com.ehr.Manager.ManagerModel.ManagerRequestToApprovedShortLeaveModel;
import ehr.cfcs.com.ehr.Model.LeaveManagementModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class RequestToApproveLeaveActivity extends AppCompatActivity {

    public TextView titleTxt,noRecordFoundTxt;
    public String leaveListUrl = SettingConstant.BaseUrl + "AppManagerToApproveLeaveRequestDashBoardList";
    public RecyclerView leaveRequestRecycler;
    public ManagerLeaveRequestApproveAndRejectAdapter adapter;
    public ArrayList<ManagerLeaveRequestApproveAndRejectModel> list = new ArrayList<>();
    public String userId = "", authCode = "";
    public ConnectionDetector conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_to_approve_leave);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.mgrtoolbar);
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

        titleTxt.setText("Leave Request");

        leaveRequestRecycler = (RecyclerView) findViewById(R.id.request_leave_recycler);
        noRecordFoundTxt = (TextView) findViewById(R.id.norecordfound);

        conn = new ConnectionDetector(RequestToApproveLeaveActivity.this);

        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(RequestToApproveLeaveActivity.this)));
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(RequestToApproveLeaveActivity.this)));

        adapter = new ManagerLeaveRequestApproveAndRejectAdapter(RequestToApproveLeaveActivity.this,list,
                RequestToApproveLeaveActivity.this,"Leave Request");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RequestToApproveLeaveActivity.this);
        leaveRequestRecycler.setLayoutManager(mLayoutManager);
        leaveRequestRecycler.setItemAnimator(new DefaultItemAnimator());
        leaveRequestRecycler.setAdapter(adapter);

        leaveRequestRecycler.getRecycledViewPool().setMaxRecycledViews(0, 0);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (conn.getConnectivityStatus()>0)
        {
            requestToApproveLeave(authCode,userId);
        }else
        {
            conn.showNoInternetAlret();
        }

    }

    public void requestToApproveLeave(final String AuthCode , final String AdminID) {

        final ProgressDialog pDialog = new ProgressDialog(RequestToApproveLeaveActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, leaveListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    if (list.size()>0)
                    {
                        list.clear();
                    }
                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String LeaveTypeName = jsonObject.getString("LeaveTypeName");
                        String StartDateText = jsonObject.getString("StartDateText");
                        String EndDateText = jsonObject.getString("EndDateText");
                        String AppliedDate = jsonObject.getString("AppliedDate");
                        String StatusText = jsonObject.getString("StatusText");
                        String LeaveApplication_Id = jsonObject.getString("LeaveApplication_Id");
                        String Noofdays = jsonObject.getString("Noofdays");
                        String UserName = jsonObject.getString("UserName");

                        list.add(new ManagerLeaveRequestApproveAndRejectModel(UserName,LeaveTypeName,StartDateText,EndDateText,
                                AppliedDate,StatusText, LeaveApplication_Id,Noofdays));



                    }

                    if (list.size() == 0)
                    {
                        noRecordFoundTxt.setVisibility(View.VISIBLE);
                        leaveRequestRecycler.setVisibility(View.GONE);
                    }else
                    {
                        noRecordFoundTxt.setVisibility(View.GONE);
                        leaveRequestRecycler.setVisibility(View.VISIBLE);
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

                Toast.makeText(RequestToApproveLeaveActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("AdminID",AdminID);


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

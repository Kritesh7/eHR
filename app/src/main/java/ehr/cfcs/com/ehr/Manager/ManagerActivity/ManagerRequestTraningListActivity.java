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

import ehr.cfcs.com.ehr.Adapter.AssestsDetailsAdapter;
import ehr.cfcs.com.ehr.Manager.ManagerAdapter.ManagerRequestTraningAdapter;
import ehr.cfcs.com.ehr.Manager.ManagerModel.ManagerRequestTraningModel;
import ehr.cfcs.com.ehr.Model.AssestDetailsModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class ManagerRequestTraningListActivity extends AppCompatActivity {

    public TextView titleTxt,noCust;
    public ManagerRequestTraningAdapter adapter;
    public ArrayList<ManagerRequestTraningModel> list = new ArrayList<>();
    public RecyclerView traningRecy;
    public String userId = "", authCode = "";
    public ConnectionDetector conn;
    public String traningRequestUrl = SettingConstant.BaseUrl + "AppManagerToApproveTrainingRequestDashBoardList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_request_traning_list);

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

        titleTxt.setText("Training Request");

        traningRecy = (RecyclerView) findViewById(R.id.mgr_traning_request_recycler);
        noCust = (TextView) findViewById(R.id.no_record_txt);

        conn = new ConnectionDetector(ManagerRequestTraningListActivity.this);
        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(ManagerRequestTraningListActivity.this)));
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ManagerRequestTraningListActivity.this)));


        adapter = new ManagerRequestTraningAdapter(ManagerRequestTraningListActivity.this,list,
                ManagerRequestTraningListActivity.this,"1");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ManagerRequestTraningListActivity.this);
        traningRecy.setLayoutManager(mLayoutManager);
        traningRecy.setItemAnimator(new DefaultItemAnimator());
        traningRecy.setAdapter(adapter);

        traningRecy.getRecycledViewPool().setMaxRecycledViews(0, 0);

        //bind list
        if (conn.getConnectivityStatus()>0)
        {
            traningRequestData(authCode,userId,"1");
        }else
            {
                conn.showNoInternetAlret();
            }



    }

    //Traning Request List Bind
    public void traningRequestData(final String AuthCode , final String AdminID, final String Status) {

        final ProgressDialog pDialog = new ProgressDialog(ManagerRequestTraningListActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, traningRequestUrl, new Response.Listener<String>() {
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
                        JSONObject object = jsonArray.getJSONObject(i);
                        String ApplicationID = object.getString("ApplicationID");
                        String DomainName = object.getString("DomainName");
                        String CourseName = object.getString("CourseName");
                        String StartDate = object.getString("StartDate");
                        String EndDate = object.getString("EndDate");
                        String ProficiencyName = object.getString("ProficiencyName");
                        String EmployeeName = object.getString("EmployeeName");
                        String StatusText = object.getString("StatusText");




                        list.add(new ManagerRequestTraningModel(DomainName,CourseName,StartDate  ,EndDate
                                ,ProficiencyName,EmployeeName, ApplicationID,StatusText));



                    }



                    if (list.size() == 0)
                    {
                        noCust.setVisibility(View.VISIBLE);
                        traningRecy.setVisibility(View.GONE);
                    }else
                    {
                        noCust.setVisibility(View.GONE);
                        traningRecy.setVisibility(View.VISIBLE);
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

                Toast.makeText(ManagerRequestTraningListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("AdminID",AdminID);
                params.put("Status", Status);


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

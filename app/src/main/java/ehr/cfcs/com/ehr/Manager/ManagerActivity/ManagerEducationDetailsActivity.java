package ehr.cfcs.com.ehr.Manager.ManagerActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import ehr.cfcs.com.ehr.Adapter.EducationDetailsAdapter;
import ehr.cfcs.com.ehr.Model.EducationModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class ManagerEducationDetailsActivity extends AppCompatActivity {

    public TextView titleTxt;
    public String empId = "";
    public EducationDetailsAdapter adapter;
    public ArrayList<EducationModel> list = new ArrayList<>();
    public RecyclerView educationRecycler;
    public TextView noCust ;
    public String educationUrl = SettingConstant.BaseUrl + "AppEmployeeEducationList";
    public ConnectionDetector conn;
    public String userId = "",authCode = "", IsAddEducationalDetail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_education_details);


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

        Intent intent = getIntent();
        if (intent != null)
        {
            empId = intent.getStringExtra("empId");
        }

        titleTxt.setText("Education Details");

        educationRecycler = (RecyclerView)findViewById(R.id.education_recycler);
        noCust = (TextView)findViewById(R.id.no_record_txt);

        conn = new ConnectionDetector(ManagerEducationDetailsActivity.this);
        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(ManagerEducationDetailsActivity.this)));
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ManagerEducationDetailsActivity.this)));


        adapter = new EducationDetailsAdapter(ManagerEducationDetailsActivity.this,list,
                ManagerEducationDetailsActivity.this,"SecondOne");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ManagerEducationDetailsActivity.this);
        educationRecycler.setLayoutManager(mLayoutManager);
        educationRecycler.setItemAnimator(new DefaultItemAnimator());
        educationRecycler.setAdapter(adapter);

        educationRecycler.getRecycledViewPool().setMaxRecycledViews(0, 0);

        if (conn.getConnectivityStatus()>0) {

            educationList(authCode,userId,empId);

        }else
        {
            conn.showNoInternetAlret();
        }



    }

    //Skills list
    public void educationList(final String AuthCode , final String AdminID, final String EmployeeID) {

        final ProgressDialog pDialog = new ProgressDialog(ManagerEducationDetailsActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, educationUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                    if (list.size()>0)
                    {
                        list.clear();
                    }
                    JSONArray jsonArray = jsonObject.getJSONArray("List");
                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String QualificationName = object.getString("QualificationName");
                        String DisciplineName = object.getString("DisciplineName");
                        String PassingDate = object.getString("PassingDate");
                        String Institute = object.getString("Institute");
                        String CourseType = object.getString("CourseType");
                        String HighestDegree = object.getString("HighestDegree");
                        String Comments = object.getString("Comments");
                        String StatusText = object.getString("StatusText");
                        String Editable = object.getString("Editable");
                        String Deleteable = object.getString("Deleteable");
                        String RecordID = object.getString("RecordID");




                        list.add(new EducationModel(QualificationName,DisciplineName,PassingDate,Institute,CourseType,HighestDegree,
                                RecordID,Comments,StatusText,"0","0"));



                    }

                    JSONArray statusArray = jsonObject.getJSONArray("Status");
                    for (int j = 0; j<statusArray.length(); j++)
                    {
                        JSONObject obj = statusArray.getJSONObject(j);

                        IsAddEducationalDetail = obj.getString("IsAddEducationalDetail");
                    }

                    if (list.size() == 0)
                    {
                        noCust.setVisibility(View.VISIBLE);
                        educationRecycler.setVisibility(View.GONE);
                    }else
                    {
                        noCust.setVisibility(View.GONE);
                        educationRecycler.setVisibility(View.VISIBLE);
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

                Toast.makeText(ManagerEducationDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("LoginAdminID",AdminID);
                params.put("EmployeeID",EmployeeID);

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

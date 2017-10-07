package ehr.cfcs.com.ehr.Main;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ehr.cfcs.com.ehr.Model.LeaveTypeModel;
import ehr.cfcs.com.ehr.Model.LeaveYearTypeModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class NewAddLeaveMangementActivity extends AppCompatActivity {

    public Spinner leaveTypeSpinner, leaveYearSpinner;
    public ArrayList<LeaveTypeModel> leaveTypeList = new ArrayList<>();
    public ArrayList<LeaveYearTypeModel> leaveYearList = new ArrayList<>();
    public ImageView startCal, endCal;
    public int month,year,day;
    public EditText startTxt,endTxt;
    public TextView titleTxt;
    public String leaveYearUrl = SettingConstant.BaseUrl + "AppEmployeeLeaveYearList";
    public String leaveTypeUrl = SettingConstant.BaseUrl + "AppEmployeeLeaveTypeList";
    public ArrayAdapter<LeaveYearTypeModel>  leaveYearAdapter;
    public ArrayAdapter<LeaveTypeModel> leaveTypeAdapter;
    public String userId = "", authcode = "";
    public ConnectionDetector conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_leave_mangement);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.newaddtollbar);
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

        titleTxt.setText("Apply Leave");

        leaveTypeSpinner = (Spinner)findViewById(R.id.leavetypespinner);
        leaveYearSpinner = (Spinner)findViewById(R.id.leaveyearspinner);

        startCal = (ImageView)findViewById(R.id.cal);
        endCal = (ImageView)findViewById(R.id.end_cal);
        startTxt = (EditText)findViewById(R.id.startdate);
        endTxt = (EditText) findViewById(R.id.enddate);

        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(NewAddLeaveMangementActivity.this)));
        authcode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(NewAddLeaveMangementActivity.this)));
        conn = new ConnectionDetector(NewAddLeaveMangementActivity.this);


        startCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(new View(NewAddLeaveMangementActivity.this).getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                Calendar cal = Calendar.getInstance();

                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAddLeaveMangementActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                               /* mm = month;
                                yy = year;
                                dd = dayOfMonth;*/

                                startTxt.setText(dayOfMonth + "-" + (month+1) + "-" + year);

                            }
                        },year , month, day);
                datePickerDialog.show();
            }
        });

        endCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(new View(NewAddLeaveMangementActivity.this).getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                Calendar cal = Calendar.getInstance();

                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAddLeaveMangementActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                               /* mm = month;
                                yy = year;
                                dd = dayOfMonth;*/

                                endTxt.setText(dayOfMonth + "-" + (month+1) + "-" + year);

                            }
                        },year , month, day);
                datePickerDialog.show();
            }
        });



        //Leave Type Spinner Work
        leaveTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        leaveTypeAdapter = new ArrayAdapter<LeaveTypeModel>(NewAddLeaveMangementActivity.this, R.layout.customizespinner,
                leaveTypeList);
        leaveTypeAdapter.setDropDownViewResource(R.layout.customizespinner);
        leaveTypeSpinner.setAdapter(leaveTypeAdapter);


        //leaveYearType Spinner
        leaveYearSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        leaveYearAdapter = new ArrayAdapter<LeaveYearTypeModel>(NewAddLeaveMangementActivity.this, R.layout.customizespinner,
                leaveYearList);
        leaveYearAdapter.setDropDownViewResource(R.layout.customizespinner);
        leaveYearSpinner.setAdapter(leaveYearAdapter);




        //get leave year type Data
        if (conn.getConnectivityStatus()>0) {

            getLeaveYearType(authcode, userId);

        }else
            {
                conn.showNoInternetAlret();
            }


        //selectbale Year Type Spinner
        leaveYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

           //     String year = ;

                try {

                    getLeaveType(authcode,userId,leaveYearList.get(i+1).getLeaveYear());

                } catch (IndexOutOfBoundsException e) {

                    getLeaveType(authcode,userId,leaveYearList.get(i).getLeaveYear());

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }


    //leave Year spinner bind Data
    public void getLeaveYearType(final String AuthCode ,final String AdminID) {


        final ProgressDialog pDialog = new ProgressDialog(NewAddLeaveMangementActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, leaveYearUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    if (leaveYearList.size()>0)
                    {
                        leaveYearList.clear();
                    }

                    leaveYearList.add(new LeaveYearTypeModel("","Please Select Leave Year"));

                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String LeaveYear = jsonObject.getString("LeaveYear");
                        String LeaveYearText = jsonObject.getString("LeaveYearText");

                        leaveYearList.add(new LeaveYearTypeModel(LeaveYear,LeaveYearText));


                    }

                    //getLeaveType(authcode,userId,leaveYearList.get(1).getLeaveYear());

                    leaveYearAdapter.notifyDataSetChanged();
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

                Toast.makeText(NewAddLeaveMangementActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    //Leave Type Spinner bind
    public void getLeaveType(final String AuthCode ,final String AdminID, final String Year) {


        final ProgressDialog pDialog = new ProgressDialog(NewAddLeaveMangementActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, leaveTypeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    if (leaveTypeList.size()>0)
                    {
                        leaveTypeList.clear();
                    }

                    leaveTypeList.add(new LeaveTypeModel("","Please Select Leave Type"));

                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String LeaveID = jsonObject.getString("LeaveID");
                        String LeaveTypeName = jsonObject.getString("LeaveTypeName");

                        leaveTypeList.add(new LeaveTypeModel(LeaveID,LeaveTypeName));


                    }

                    leaveTypeAdapter.notifyDataSetChanged();
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

                Toast.makeText(NewAddLeaveMangementActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("AdminID",AdminID);
                params.put("Year",Year);


                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }


}

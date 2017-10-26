package ehr.cfcs.com.ehr.Main;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Button;
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

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ehr.cfcs.com.ehr.Model.BloodGroupModel;
import ehr.cfcs.com.ehr.Model.PolicyTypeModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class AddMedicalandAnssuranceActivity extends AppCompatActivity {

    public TextView titleTxt;
    public Spinner  policyTypeSpinner;
    public String personalDdlDetailsUrl = SettingConstant.BaseUrl + "AppddlEmployeePersonalData";
    public String addUrl = SettingConstant.BaseUrl + "AppEmployeeMedicalPolicyInsUpdt";
    public ArrayList<PolicyTypeModel> policyTypeList = new ArrayList<>();
    public ArrayAdapter<PolicyTypeModel> policyTypeAdapter;
    public ConnectionDetector conn;
    public ImageView startDateBtn, endDateBtn;
    public EditText startDateTxt, endDateTxt, policyNumberTxt, policyNameTxt, policyDurationTxt, policyByTxt, insuranceCompTxt,
                    amountInsuredTxt;
    public Button addBtn;
    private int yy, mm, dd;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public String policyTypeIdStr = "", authcode = "", userId = "", actionMode = "", recordidStr = "",policyTypeStr = ""
            ,policyNameStr = "", policyNumberStr = "", policyDurationStr = "", policyByStr = "", insuranceCompStr = ""
            ,amountStr = "", startDateStr = "", endDateStr = "",policyType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaland_anssurance);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.medicaltollbar);
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

        titleTxt.setText("Add New Medical&Assurance");

        Intent intent = getIntent();
        if (intent != null)
        {
            actionMode = intent.getStringExtra("Mode");
            recordidStr = intent.getStringExtra("RecordId");
            policyTypeStr = intent.getStringExtra("PolicyType");
            policyNameStr = intent.getStringExtra("PolicyName");
            policyNumberStr = intent.getStringExtra("PolicyNumber");
            policyDurationStr = intent.getStringExtra("PolicyDuration");
            policyByStr = intent.getStringExtra("PolicyBy");
            insuranceCompStr = intent.getStringExtra("InsuranceCompany");
            amountStr = intent.getStringExtra("AmountInsured");
            startDateStr = intent.getStringExtra("StartDate");
            endDateStr = intent.getStringExtra("EndDate");
        }

        conn = new ConnectionDetector(AddMedicalandAnssuranceActivity.this);
        authcode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(AddMedicalandAnssuranceActivity.this)));
        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(AddMedicalandAnssuranceActivity.this)));


        //   policyCompanySpinner = (Spinner)findViewById(R.id.policycompanynamespinner);
        policyTypeSpinner = (Spinner)findViewById(R.id.policytypespinner);
        startDateBtn = (ImageView) findViewById(R.id.policy_startdate);
        endDateBtn = (ImageView) findViewById(R.id.policy_enddate);
        startDateTxt = (EditText) findViewById(R.id.startdatetxt);
        endDateTxt = (EditText) findViewById(R.id.enddatetxt);
        policyNameTxt = (EditText) findViewById(R.id.policy_name);
        policyNumberTxt = (EditText) findViewById(R.id.policy_number);
        policyDurationTxt = (EditText) findViewById(R.id.policy_duration);
        policyByTxt = (EditText) findViewById(R.id.policy_by);
        insuranceCompTxt = (EditText) findViewById(R.id.policy_insurance);
        amountInsuredTxt = (EditText) findViewById(R.id.policy_amount);
        addBtn = (Button) findViewById(R.id.newrequestbtn);

        //change button name
        if (actionMode.equalsIgnoreCase("EditMode"))
        {
            addBtn.setText("Update Medical AND Assurance");
            titleTxt.setText("Update Medical&Assurance");

            policyNameTxt.setText(policyNameStr);
            policyNumberTxt.setText(policyNumberStr);
            policyDurationTxt.setText(policyDurationStr);
            policyByTxt.setText(policyByStr);
            insuranceCompTxt.setText(insuranceCompStr);
            amountInsuredTxt.setText(amountStr);
            startDateTxt.setText(startDateStr);
            endDateTxt.setText(endDateStr);
        }

        //Policy Type List Spinner
        //change spinner arrow color
        policyTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        policyTypeAdapter = new ArrayAdapter<PolicyTypeModel>(AddMedicalandAnssuranceActivity.this, R.layout.customizespinner,
                policyTypeList);
        policyTypeAdapter.setDropDownViewResource(R.layout.customizespinner);
        policyTypeSpinner.setAdapter(policyTypeAdapter);

        //start date Picker
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicalandAnssuranceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                yy = year;
                                mm = monthOfYear;
                                dd = dayOfMonth;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.MONTH, monthOfYear);
                                String sdf = new SimpleDateFormat("LLL", Locale.getDefault()).format(calendar.getTime());
                                sdf = new DateFormatSymbols().getShortMonths()[monthOfYear];

                                Log.e("checking,............", sdf + " null");
                                startDateTxt.setText(dayOfMonth + "-" + sdf + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        //end date Picker
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicalandAnssuranceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                yy = year;
                                mm = monthOfYear;
                                dd = dayOfMonth;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.MONTH, monthOfYear);
                                String sdf = new SimpleDateFormat("LLL", Locale.getDefault()).format(calendar.getTime());
                                sdf = new DateFormatSymbols().getShortMonths()[monthOfYear];

                                Log.e("checking,............", sdf + " null");
                                endDateTxt.setText(dayOfMonth + "-" + sdf + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // find policy type id
        policyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                policyTypeIdStr = policyTypeList.get(i).getPolicyId();
                policyType = policyTypeList.get(i).getPolicyType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // add new records
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (policyTypeIdStr.equalsIgnoreCase(""))
                {
                    Toast.makeText(AddMedicalandAnssuranceActivity.this, "Please select Policy Type", Toast.LENGTH_SHORT).show();
                }else if (policyNumberTxt.getText().toString().equalsIgnoreCase(""))
                {
                    policyNumberTxt.setError("Please enter policy number");
                }else if (policyNameTxt.getText().toString().equalsIgnoreCase(""))
                {
                    policyNameTxt.setError("Please enter policy name");
                }else if (policyDurationTxt.getText().toString().equalsIgnoreCase(""))
                {
                    policyDurationTxt.setError("please enter policy duration");
                }else if (policyByTxt.getText().toString().equalsIgnoreCase(""))
                {
                    policyByTxt.setError("Please enter policy by");
                }else if (insuranceCompTxt.getText().toString().equalsIgnoreCase(""))
                {
                    insuranceCompTxt.setError("Please enter insurance company ");
                }else if (amountInsuredTxt.getText().toString().equalsIgnoreCase(""))
                {
                    amountInsuredTxt.setError("Please enter amount insured");
                }else {

                    if (conn.getConnectivityStatus()>0) {
                        addMedicalAnssuranceData(userId, recordidStr, policyTypeIdStr, policyNameTxt.getText().toString(), authcode,
                                policyNumberTxt.getText().toString(), startDateTxt.getText().toString(), endDateTxt.getText().toString(),
                                policyDurationTxt.getText().toString(), policyByTxt.getText().toString(), insuranceCompTxt.getText().toString(),
                                amountInsuredTxt.getText().toString());
                    }else
                        {
                            conn.showNoInternetAlret();
                        }
                }

            }
        });
        // bind spinner data
        if (conn.getConnectivityStatus()>0)
        {
            personalDdlDetails();
        }else
            {
                conn.showNoInternetAlret();
            }
    }

    //bind all spiiner data
    public void personalDdlDetails() {


        final  ProgressDialog pDialog = new ProgressDialog(AddMedicalandAnssuranceActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, personalDdlDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                    //bind material List
                    if (policyTypeList.size()>0)
                    {
                        policyTypeList.clear();
                    }
                    policyTypeList.add(new PolicyTypeModel("Please Select Policy Type",""));

                    JSONArray policyObj = jsonObject.getJSONArray("PolicyTypeMaster");
                    for (int i =0; i<policyObj.length(); i++)
                    {
                        JSONObject object = policyObj.getJSONObject(i);

                        String PolicyTypeID = object.getString("PolicyTypeID");
                        String PolicyTypeName = object.getString("PolicyTypeName");

                        policyTypeList.add(new PolicyTypeModel(PolicyTypeName,PolicyTypeID));

                    }


                    for (int k =0; k<policyTypeList.size(); k++)
                    {
                        if (actionMode.equalsIgnoreCase("EditMode"))
                        {
                            if (policyTypeList.get(k).getPolicyType().equalsIgnoreCase(policyTypeStr))
                            {
                                policyTypeSpinner.setSelection(k);
                            }
                        }
                    }

                    policyTypeAdapter.notifyDataSetChanged();
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

                Toast.makeText(AddMedicalandAnssuranceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        });
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }

    //add new medical anssurance
    public void addMedicalAnssuranceData(final String AdminID  ,final String RecordID, final String PolicyID, final String Name,
                                 final String AuthCode, final String Number, final String StartDate, final String EndDate,
                                 final String Duration, final String PolicyBy, final String InsuranceCompany, final String AmountInsured )  {

        final ProgressDialog pDialog = new ProgressDialog(AddMedicalandAnssuranceActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, addUrl, new Response.Listener<String>() {
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

                Toast.makeText(AddMedicalandAnssuranceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AdminID",AdminID);
                params.put("AuthCode",AuthCode);
                params.put("RecordID",RecordID);
                params.put("PolicyID",PolicyID);
                params.put("Name",Name);
                params.put("Number",Number);
                params.put("StartDate",StartDate);
                params.put("EndDate",EndDate);
                params.put("Duration",Duration);
                params.put("PolicyBy",PolicyBy);
                params.put("InsuranceCompany",InsuranceCompany);
                params.put("AmountInsured",AmountInsured);
                params.put("ImgJson","");
                params.put("ImageExtension","");


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

package ehr.cfcs.com.ehr.Main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class ApplyShortLeaveActivity extends AppCompatActivity {

    public TextView titleTxt;
    public ImageView dateBtn, toTimeBtn, fromTimeBtn;
    public EditText dateTxt, toTimeTxt, fromTimeTxt;
    private int hh, m;
    private int yy, mm, dd;
    private int year, month, day, mHour, mMinute;
    public String applyUrl = SettingConstant.BaseUrl + "AppEmployeeShortLeaveApply";
    public ConnectionDetector conn;
    public String userid = "", authCode = "", mgrId = "", type = "",MsgNotification = "", userName = "", compId = "";
    public Button subBtn ;
    public EditText commentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_short_leave);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.applyshortleavetollbar);
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

        titleTxt.setText("Apply Short Leave");

        conn = new ConnectionDetector(ApplyShortLeaveActivity.this);

        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ApplyShortLeaveActivity.this)));
        userid = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(ApplyShortLeaveActivity.this)));
        mgrId = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getMgrDir(ApplyShortLeaveActivity.this)));
        type = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserType(ApplyShortLeaveActivity.this)));
        userName = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserName(ApplyShortLeaveActivity.this)));
        compId = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getCompanyId(ApplyShortLeaveActivity.this)));

        dateTxt = (EditText)findViewById(R.id.dateleavetxt);
        toTimeTxt = (EditText)findViewById(R.id.datetotxt);
        fromTimeTxt = (EditText)findViewById(R.id.datefromtxt);
        dateBtn = (ImageView) findViewById(R.id.dateleave);
        toTimeBtn = (ImageView) findViewById(R.id.dateto);
        fromTimeBtn = (ImageView) findViewById(R.id.datefrom);
        subBtn = (Button) findViewById(R.id.applyshortbtn);
        commentTxt = (EditText) findViewById(R.id.shortleave_comment);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(new View(ApplyShortLeaveActivity.this).getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                Calendar cal = Calendar.getInstance();

                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ApplyShortLeaveActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                               /* mm = month;
                                yy = year;
                                dd = dayOfMonth;*/
                                Calendar calendar = Calendar.getInstance();
                                //calendar.set(Calendar.MONTH, monthOfYear);
                                String sdf = new SimpleDateFormat("LLL", Locale.getDefault()).format(calendar.getTime());
                                sdf = new DateFormatSymbols().getShortMonths()[month];

                                dateTxt.setText(dayOfMonth + "-" + sdf + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        toTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ApplyShortLeaveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                hh = hourOfDay;
                                m = minute;
                                // ro = checking + hourOfDay  + minute;


                                toTimeTxt.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        fromTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ApplyShortLeaveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                hh = hourOfDay;
                                m = minute;
                                // ro = checking + hourOfDay  + minute;


                                fromTimeTxt.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        dateTxt.setText(getCurrentTime());



        // apply leave
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (toTimeTxt.getText().toString().equalsIgnoreCase(""))
                {
                    toTimeTxt.setError("Please enter valid Time");
                }else if (fromTimeTxt.getText().toString().equalsIgnoreCase(""))
                {
                    fromTimeTxt.setError("please Enter Valid Time");
                }else if (dateTxt.getText().toString().equalsIgnoreCase(""))
                {
                    dateTxt.setError("Please enter valid date");
                }else
                    {

                        if (conn.getConnectivityStatus()>0)
                        {
                            applyShortLeave(userid,type,authCode,mgrId,dateTxt.getText().toString(),fromTimeTxt.getText().toString(),
                                    toTimeTxt.getText().toString(),commentTxt.getText().toString(),"0", compId,userName);

                        }else
                        {
                            conn.showNoInternetAlret();
                        }
                    }
            }
        });




    }

    //get current time
    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        Calendar cal = Calendar.getInstance();
        String sdf = new SimpleDateFormat("LLL", Locale.getDefault()).format(cal.getTime());
        //sdf = new DateFormatSymbols().getShortMonths()[month];

        return dateFormat.format(cal.getTime());
    }

    //Apply Short Leave
    public void applyShortLeave(final String AdminID  , final String Type, final String AuthCode ,
                          final String MgrID , final String StartDate, final String TimeFrom, final String TimeTo,
                                final String Comment, final String PopUpCount, final String CompID, final String username) {


        final ProgressDialog pDialog = new ProgressDialog(ApplyShortLeaveActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, applyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Apply Short Leave", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.has("MsgNotification"))
                        {
                            MsgNotification = jsonObject.getString("MsgNotification");
                            Toast.makeText(ApplyShortLeaveActivity.this, MsgNotification, Toast.LENGTH_SHORT).show();

                        }

                        if (jsonObject.has("status"))
                        {
                            String status = jsonObject.getString("status");

                            if (status.equalsIgnoreCase("success"))
                            {
                                onBackPressed();

                            }else if (status.equalsIgnoreCase("popup"))
                            {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        ApplyShortLeaveActivity.this);

                                alertDialogBuilder.setCancelable(false);

                                // set dialog message
                                alertDialogBuilder
                                        .setTitle("Short Leave Status")
                                        .setMessage(MsgNotification)
                                        .setCancelable(false)
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {


                                                dialog.dismiss();

                                            }
                                        })
                                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {


                                                if (conn.getConnectivityStatus()>0)
                                                {
                                                    applyShortLeave(userid,type,authCode,mgrId,dateTxt.getText().toString(),fromTimeTxt.getText().toString(),
                                                            toTimeTxt.getText().toString(),commentTxt.getText().toString(),"1",compId,userName);

                                                }else
                                                {
                                                    conn.showNoInternetAlret();
                                                }

                                            }
                                        });


                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // show it
                                alertDialog.show();

                            }
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

                Toast.makeText(ApplyShortLeaveActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AdminID", AdminID);
                params.put("Type",Type);
                params.put("MgrID",MgrID);
                params.put("StartDate",StartDate);
                params.put("TimeFrom",TimeFrom);
                params.put("TimeTo",TimeTo);
                params.put("Comment",Comment);
                params.put("AuthCode",AuthCode);
                params.put("PopUpCount",PopUpCount);
                params.put("CompID",CompID);
                params.put("UserName",username);

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

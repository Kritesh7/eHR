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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

import ehr.cfcs.com.ehr.Adapter.BookMeaPrevisonAdapter;
import ehr.cfcs.com.ehr.Interface.AddItemInterface;
import ehr.cfcs.com.ehr.Model.BookMeaPrevisionModel;
import ehr.cfcs.com.ehr.Model.SendListModel;
import ehr.cfcs.com.ehr.Model.SkillsModel;
import ehr.cfcs.com.ehr.Model.StationaryRequestModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class AddNewStationaryRequestActivity extends AppCompatActivity implements AddItemInterface  {

    public TextView titleTxt;
    public BookMeaPrevisonAdapter adapter;
    public ArrayList<BookMeaPrevisionModel> list = new ArrayList<>();
    public ListView listView;
    public String stationoryUrl = SettingConstant.BaseUrl + "AppEmployeeStationaryItemDetail";
    public String addUrl = SettingConstant.BaseUrl + "AppEmployeeStationaryRequestInsUpdt";
    public ConnectionDetector conn;
    public String authCode = "",modeString = "",editList = "",userId = "";
    public ArrayList<BookMeaPrevisionModel> myList = new ArrayList<>();
    public Button addBtn;
    public ArrayList<SendListModel> sendListInner = new ArrayList<>();
    public ArrayList<SendListModel> emptyList = new ArrayList<>();
    public AddItemInterface itemInterface;
    ArrayList<String> secondQuant = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_stationary_request);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.staionaory_tollbar);
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

                onBackPressed();

            }
        });

        titleTxt.setText("Add New Stationary Request");

        Intent intent = getIntent();
        if (intent != null)
        {
            modeString = intent.getStringExtra("Mode");
            myList = (ArrayList<BookMeaPrevisionModel>)getIntent().getSerializableExtra("mylist");

        }

        conn = new ConnectionDetector(AddNewStationaryRequestActivity.this);
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(AddNewStationaryRequestActivity.this)));
        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(AddNewStationaryRequestActivity.this)));

        listView = (ListView)findViewById(R.id.listview);
        addBtn = (Button)findViewById(R.id.newrequestbtn);

        //listView.setItemsCanFocus(true);

        if (modeString.equalsIgnoreCase("Edit"))
        {
            adapter = new BookMeaPrevisonAdapter(myList,AddNewStationaryRequestActivity.this,this);
        }else
            {
                adapter = new BookMeaPrevisonAdapter(list,AddNewStationaryRequestActivity.this,this);
            }


            listView.setItemsCanFocus(true);
            listView.setAdapter(adapter);

        if (conn.getConnectivityStatus()>0) {

            stationryData(authCode,"1");

        }else
            {
                conn.showNoInternetAlret();
            }

            //add new data
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String splitString  = String.valueOf(adapter.getSelectedString());
                String id = String.valueOf(adapter.getSelectedId());


                String quanty = String.valueOf(secondQuant);
                String remark = String.valueOf(adapter.getSelectedRemark());

                //remove first and last character
                String removeHip = splitString.substring(1, splitString.length() - 1);
                String removeId = id.substring(1, id.length() - 1);
                String removeQuant = quanty.substring(1, quanty.length() - 1);
                String removeRemark = remark.substring(1, remark.length() - 1);

               // str.replaceAll("\[|\]", "");
                String[] separated = removeHip.split(",");
                String[] separatedId = removeId.split(",");
                String[] separatedquant = removeQuant.split(",");
                String[] separatedRemark = removeRemark.split(",");


                JSONArray mainArray = new JSONArray();
                //JSONObject object = new JSONObject();
                try {

                    Log.e("Split  of string",quanty);
                    Log.e("lenth of string",adapter.getSelectedQuan().size() + "");
                    Log.e("checking first",separated[0]);

                    for (int i =0; i<separated.length; i++) {

                        JSONObject filterJson = new JSONObject();
                        filterJson.put("ItemID", separatedId[i]);
                        filterJson.put("ItemName", separated[i]);
                        filterJson.put("Qty", "testing");
                        filterJson.put("Remark", separatedRemark[i]);

                        mainArray.put(filterJson);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.e("checking the json is",mainArray.toString());




                //addStaionoryItem(userId,"","1","14-10-2017",authCode,splitString,separated);


            }
        });

    }


    //Stationary Item Data
    public void stationryData(final String AuthCode , final String ItemCatID) {

        final ProgressDialog pDialog = new ProgressDialog(AddNewStationaryRequestActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, stationoryUrl, new Response.Listener<String>() {
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
                        String ItemID = jsonObject.getString("ItemID");
                        String ItemName = jsonObject.getString("ItemName");
                        String MaxQuantity = jsonObject.getString("MaxQuantity");

                        list.add(new BookMeaPrevisionModel(ItemName,ItemID,MaxQuantity,"","false"));



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

                Toast.makeText(AddNewStationaryRequestActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("ItemCatID",ItemCatID);


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

    //add new staionry Data
    public void addStaionoryItem(final String AdminID  ,final String RID, final String ItemCatID, final String IdealCosureDate,
                                 final String AuthCode, final String splitString, final String[] separated)  {

        final ProgressDialog pDialog = new ProgressDialog(AddNewStationaryRequestActivity.this,R.style.AppCompatAlertDialogStyle);
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

                Toast.makeText(AddNewStationaryRequestActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AdminID",AdminID);
                params.put("RID",RID);
                params.put("ItemCatID",ItemCatID);
                params.put("IdealCosureDate",IdealCosureDate);
              //  params.put("ItemDetailJson",mainArray.toString());
                params.put("AuthCode",AuthCode);

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
    public void getAllItem(ArrayList<String> sendList) {

       // sendListInner = sendList;

        secondQuant = sendList;
        Log.e("checking the size",secondQuant.size()+"");
    }
}

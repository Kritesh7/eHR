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
import ehr.cfcs.com.ehr.Model.BookMeaPrevisionModel;
import ehr.cfcs.com.ehr.Model.SkillsModel;
import ehr.cfcs.com.ehr.Model.StationaryRequestModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class AddNewStationaryRequestActivity extends AppCompatActivity {

    public TextView titleTxt;
    public BookMeaPrevisonAdapter adapter;
    public ArrayList<BookMeaPrevisionModel> list = new ArrayList<>();
    public ListView listView;
    public String stationoryUrl = SettingConstant.BaseUrl + "AppEmployeeStationaryItemDetail";
    public ConnectionDetector conn;
    public String authCode = "",modeString = "",editList = "";
    public ArrayList<BookMeaPrevisionModel> myList = new ArrayList<>();


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

                //
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

        listView = (ListView)findViewById(R.id.listview);

        //listView.setItemsCanFocus(true);

        if (modeString.equalsIgnoreCase("Edit"))
        {
            adapter = new BookMeaPrevisonAdapter(myList,AddNewStationaryRequestActivity.this);
        }else
            {
                adapter = new BookMeaPrevisonAdapter(list,AddNewStationaryRequestActivity.this);
            }


        listView.setAdapter(adapter);

        if (conn.getConnectivityStatus()>0) {

            stationryData(authCode,"1");

        }else
            {
                conn.showNoInternetAlret();
            }



    }

   /* private void prepareInsDetails() {

        BookMeaPrevisionModel model = new BookMeaPrevisionModel("Pencil");
        list.add(model);
        model = new BookMeaPrevisionModel("Shopener");
        list.add(model);
        model = new BookMeaPrevisionModel("Notepad");
        list.add(model);
        model = new BookMeaPrevisionModel("Bag");
        list.add(model);



        adapter.notifyDataSetChanged();

    }*/

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


}

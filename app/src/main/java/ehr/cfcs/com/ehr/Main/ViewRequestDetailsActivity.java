package ehr.cfcs.com.ehr.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;
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

import ehr.cfcs.com.ehr.Adapter.RequestedItemAdapter;
import ehr.cfcs.com.ehr.Model.BookMeaPrevisionModel;
import ehr.cfcs.com.ehr.Model.RequestItemModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.MyListLayout;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class ViewRequestDetailsActivity extends AppCompatActivity {

    public TextView titleTxt,requestByTxt, requestDateTxt,empNameTxt,statusTxt,hrCommentTxt ,closerDateTxt, hrTxt;
    public ehr.cfcs.com.ehr.Source.MyListLayout requestItemList;
    public RequestedItemAdapter adapter;
    public ArrayList<RequestItemModel> list = new ArrayList<>();
    public ArrayList<BookMeaPrevisionModel> itemBindList = new ArrayList<>();
    public ConnectionDetector conn;
    public String stationoryUrl = SettingConstant.BaseUrl + "AppEmployeeStationaryRequestDetail";
    public String authCode = "", rid="";
    public Button updateDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request_details);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.viewleavtoolbar);
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

        titleTxt.setText("Stationary Request Details");

        Intent intent = getIntent();
        if (intent != null)
        {
            rid = intent.getStringExtra("Rid");
        }
        conn = new ConnectionDetector(ViewRequestDetailsActivity.this);
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ViewRequestDetailsActivity.this)));

        requestItemList = (ehr.cfcs.com.ehr.Source.MyListLayout) findViewById(R.id.request_item_list);
        requestByTxt = (TextView) findViewById(R.id.staionory_request);
        requestDateTxt = (TextView) findViewById(R.id.staionory_request_date);
        empNameTxt = (TextView)findViewById(R.id.staionory_empname);
        statusTxt = (TextView)findViewById(R.id.stationory_current_status);
        hrCommentTxt = (TextView)findViewById(R.id.statonory_hr_comment);
        closerDateTxt = (TextView)findViewById(R.id.staionory_closer_date);
        hrTxt = (TextView)findViewById(R.id.hrcommenttxt);
        updateDetails = (Button)findViewById(R.id.editstaionry);

        adapter = new RequestedItemAdapter(list,ViewRequestDetailsActivity.this);

        requestItemList.setAdapter(adapter);
       // MyListLayout.setListViewHeightBasedOnChildren(requestItemList);

        if (conn.getConnectivityStatus()>0)
        {
            viewStationryDetails(authCode,rid,"1");
        }else
            {
                conn.showNoInternetAlret();
            }


     updateDetails.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             Intent i = new Intent(ViewRequestDetailsActivity.this, AddNewStationaryRequestActivity.class);
             i.putExtra("Mode", "Edit");
             i.putExtra("mylist", itemBindList);
             startActivity(i);
             overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
         }
     });



    }

    //View Stationry Details
    public void viewStationryDetails(final String AuthCode ,final String RID, final String ItemCatID) {

        final ProgressDialog pDialog = new ProgressDialog(ViewRequestDetailsActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, stationoryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                    JSONArray requestDetailsArray = jsonObject.getJSONArray("RequestDetail");
                    for (int i = 0; i<requestDetailsArray.length(); i++)
                    {
                        JSONObject object = requestDetailsArray.getJSONObject(i);

                        String EmpName = object.getString("EmpName");
                        String AddDateText = object.getString("AddDateText");
                        String AppBy = object.getString("ApprovedBy");
                        String HrComment = object.getString("HrComment");
                        String AppStatusText = object.getString("AppStatusText");
                        String IdealClosureDateText = object.getString("IdealClosureDateText");
                        String Visibility = object.getString("Visibility");

                        closerDateTxt.setText(IdealClosureDateText);
                        empNameTxt.setText(EmpName);
                        requestByTxt.setText(AppBy);
                        requestDateTxt.setText(AddDateText);
                        hrCommentTxt.setText(HrComment);
                        statusTxt.setText(AppStatusText);


                        if (HrComment.equalsIgnoreCase(""))
                        {
                            hrTxt.setVisibility(View.GONE);
                            hrCommentTxt.setVisibility(View.GONE);
                        }


                    }

                    JSONArray itemdetaislArray = jsonObject.getJSONArray("ItemsDetail");
                    if (list.size()>0)
                    {
                        list.clear();
                    }
                    for (int j=0 ; j<itemdetaislArray.length();j++)
                    {
                        JSONObject object = itemdetaislArray.getJSONObject(j);

                        String ItemName = object.getString("ItemName");
                        String NoOfItem = object.getString("NoOfItem");
                        String Remark = object.getString("Remark");
                        String ItemID = object.getString("ItemID");


                        list.add(new RequestItemModel(ItemName,NoOfItem,Remark,ItemID));



                    }

                    JSONArray itemsBindDataArray = jsonObject.getJSONArray("ItemsBindData");

                    if (itemBindList.size()>0)
                    {
                        itemBindList.clear();
                    }
                    for (int k=0 ; k<itemsBindDataArray.length(); k++)
                    {
                        JSONObject object = itemsBindDataArray.getJSONObject(k);
                        String ItemID = object.getString("ItemID");
                        String ItemName = object.getString("ItemName");
                        String Quantity = object.getString("Quantity");
                        String Remark = object.getString("Remark");
                        String chkValue = object.getString("chkValue");


                        itemBindList.add(new BookMeaPrevisionModel(ItemName,ItemID,Quantity,Remark,chkValue));



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

                Toast.makeText(ViewRequestDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("RID",RID);
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
        }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }


}


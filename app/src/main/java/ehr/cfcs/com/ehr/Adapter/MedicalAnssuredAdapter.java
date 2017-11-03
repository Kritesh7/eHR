package ehr.cfcs.com.ehr.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ehr.cfcs.com.ehr.Main.AddMedicalandAnssuranceActivity;
import ehr.cfcs.com.ehr.Main.ViewCabDetailsActivity;
import ehr.cfcs.com.ehr.Main.ViewRequestDetailsActivity;
import ehr.cfcs.com.ehr.Model.MedicalAnssuranceModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.DownloadTask;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

/**
 * Created by Admin on 20-09-2017.
 */

public class MedicalAnssuredAdapter extends RecyclerView.Adapter<MedicalAnssuredAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<MedicalAnssuranceModel> list = new ArrayList<>();
    public Activity activity;
    public String deleteUrl = SettingConstant.BaseUrl + "AppEmployeeMedicalPolicyDelete";
    public String authCode = "";
    String[] permissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };
    public String checkNavigateStr = "MedialAnssurance";
    public String userid = "";

    public MedicalAnssuredAdapter(Context context, ArrayList<MedicalAnssuranceModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.medicalanssurance_item_layout, parent, false);
        return new MedicalAnssuredAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MedicalAnssuranceModel model = list.get(position);
        
        authCode = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(context)));
        userid = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(context)));


        holder.policyTypeTxt.setText(model.getPolicyType());
        holder.policyNumberTxt.setText(model.getPolicyNumber());
        holder.policyDurationTxt.setText(model.getPolicyDuration());
        holder.policyNameTxt.setText(model.getPolicyName());
        holder.amountInsuredTxt.setText(model.getPolicyInsured());
        holder.policyByTxt.setText(model.getPolicyBy());

        holder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, AddMedicalandAnssuranceActivity.class);
                i.putExtra("RecordId",model.getRecordId());
                i.putExtra("Mode","EditMode");
                i.putExtra("PolicyType",model.getPolicyType());
                i.putExtra("PolicyName",model.getPolicyName());
                i.putExtra("PolicyNumber",model.getPolicyNumber());
                i.putExtra("PolicyDuration",model.getPolicyDuration());
                i.putExtra("PolicyBy",model.getPolicyBy());
                i.putExtra("InsuranceCompany",model.getInsuranceComp());
                i.putExtra("AmountInsured",model.getPolicyInsured());
                i.putExtra("StartDate",model.getStartDate());
                i.putExtra("EndDate", model.getEndDate());
                i.putExtra("File",model.getFileNameText());
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });
        
        //delete the list
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSettingsAlert(position,authCode,model.getRecordId(),userid);
            }
        });

        if (model.getFileNameText().equalsIgnoreCase(""))
        {
            holder.downloadLay.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
        }else
        {
            holder.downloadLay.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);
        }

        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermissions()) {
                    new DownloadTask(context, SettingConstant.DownloadUrl + model.getFileNameText(),checkNavigateStr);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView policyTypeTxt,policyNumberTxt,policyDurationTxt,policyNameTxt, amountInsuredTxt, policyByTxt;
        public ImageView delBtn;
        public ImageView mainLay;
        public View view;
        public LinearLayout downloadLay;
        public ImageView downloadBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            policyTypeTxt = (TextView)itemView.findViewById(R.id.policytype);
            policyNumberTxt = (TextView)itemView.findViewById(R.id.policynumber);
            policyDurationTxt = (TextView)itemView.findViewById(R.id.policy_duration);
            policyNameTxt = (TextView)itemView.findViewById(R.id.policyname);
            amountInsuredTxt = (TextView)itemView.findViewById(R.id.amountinsured);
            policyByTxt = (TextView)itemView.findViewById(R.id.policyby);
            delBtn = (ImageView) itemView.findViewById(R.id.delbtn);
            mainLay = (ImageView)itemView.findViewById(R.id.main_lay);
            downloadLay = (LinearLayout) itemView.findViewById(R.id.downloadOptionLay);
            view = (View)itemView.findViewById(R.id.view);
            downloadBtn = (ImageView) itemView.findViewById(R.id.downloadOptionBtn);

        }
    }


    public void remove(int position) {
        if (position < 0 || position >= list.size()) {
            return;
        }
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void showSettingsAlert(final int postion, final String authcode, final String recordId, final String userId) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        //  alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage("Are You Sure You Want to Delete?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                deleteMethod(authcode,recordId, postion,userId);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    //delete the Details
    public void deleteMethod(final String AuthCode ,final String RecordID, final int  postion, final String AdminID) {

        final ProgressDialog pDialog = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
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

                            remove(postion);
                            Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode",AuthCode);
                params.put("AdminID",AdminID);
                params.put("RecordID",RecordID);

                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

}

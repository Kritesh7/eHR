package ehr.cfcs.com.ehr.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Main.AddMedicalandAnssuranceActivity;
import ehr.cfcs.com.ehr.Main.ViewCabDetailsActivity;
import ehr.cfcs.com.ehr.Model.MedicalAnssuranceModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 20-09-2017.
 */

public class MedicalAnssuredAdapter extends RecyclerView.Adapter<MedicalAnssuredAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<MedicalAnssuranceModel> list = new ArrayList<>();
    public Activity activity;

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
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView policyTypeTxt,policyNumberTxt,policyDurationTxt,policyNameTxt, amountInsuredTxt, policyByTxt;

        public LinearLayout mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            policyTypeTxt = (TextView)itemView.findViewById(R.id.policytype);
            policyNumberTxt = (TextView)itemView.findViewById(R.id.policynumber);
            policyDurationTxt = (TextView)itemView.findViewById(R.id.policy_duration);
            policyNameTxt = (TextView)itemView.findViewById(R.id.policyname);
            amountInsuredTxt = (TextView)itemView.findViewById(R.id.amountinsured);
            policyByTxt = (TextView)itemView.findViewById(R.id.policyby);

            mainLay = (LinearLayout)itemView.findViewById(R.id.main_lay);

        }
    }
}

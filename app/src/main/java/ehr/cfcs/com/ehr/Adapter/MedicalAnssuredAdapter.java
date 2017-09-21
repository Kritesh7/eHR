package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.MedicalAnssuranceModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 20-09-2017.
 */

public class MedicalAnssuredAdapter extends RecyclerView.Adapter<MedicalAnssuredAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<MedicalAnssuranceModel> list = new ArrayList<>();

    public MedicalAnssuredAdapter(Context context, ArrayList<MedicalAnssuranceModel> list) {
        this.context = context;
        this.list = list;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView policyTypeTxt,policyNumberTxt,policyDurationTxt,policyNameTxt, amountInsuredTxt;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            policyTypeTxt = (TextView)itemView.findViewById(R.id.policytype);
            policyNumberTxt = (TextView)itemView.findViewById(R.id.policynumber);
            policyDurationTxt = (TextView)itemView.findViewById(R.id.policy_duration);
            policyNameTxt = (TextView)itemView.findViewById(R.id.policyname);
            amountInsuredTxt = (TextView)itemView.findViewById(R.id.amountinsured);

            // mainLay = (CardView)itemView.findViewById(R.id.leave_management_main_lay);

        }
    }
}

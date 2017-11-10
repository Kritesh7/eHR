package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.LeaveManagementModel;
import ehr.cfcs.com.ehr.Model.LeaveSummarryModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 21-09-2017.
 */

public class LeaveSummarryAdapter extends RecyclerView.Adapter<LeaveSummarryAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<LeaveSummarryModel> list = new ArrayList<>();

    public LeaveSummarryAdapter(Context context, ArrayList<LeaveSummarryModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LeaveSummarryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.leave_summrry_item_layout, parent, false);
        return new LeaveSummarryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaveSummarryAdapter.ViewHolder holder, int position) {

        LeaveSummarryModel model = list.get(position);

        holder.leaveTypeTxt.setText(model.getLeaveType());
        holder.leaveyearTxt.setText(model.getLeaveYear());
        holder.entitlementTxt.setText(model.getEntitilement());
        holder.carryoverTxt.setText(model.getCarryOver());
        holder.approvedTxt.setText(model.getApproved());
        holder.balanceTxt.setText(model.getBalance());
        holder.avlBalnce.setText(model.getLeaveAvail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView leaveTypeTxt,leaveyearTxt,entitlementTxt,carryoverTxt, approvedTxt,balanceTxt, avlBalnce;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            leaveTypeTxt = (TextView)itemView.findViewById(R.id.summrry_leave_type);
            leaveyearTxt = (TextView)itemView.findViewById(R.id.leaveyear);
            entitlementTxt = (TextView)itemView.findViewById(R.id.entitlement);
            carryoverTxt = (TextView)itemView.findViewById(R.id.carryover);
            approvedTxt = (TextView)itemView.findViewById(R.id.summrry_approved);
            avlBalnce = (TextView) itemView.findViewById(R.id.avl_balance);

            balanceTxt = (TextView)itemView.findViewById(R.id.balance);





        }
    }
}

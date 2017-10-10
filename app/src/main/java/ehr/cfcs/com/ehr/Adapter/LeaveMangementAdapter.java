package ehr.cfcs.com.ehr.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Main.NewAddLeaveMangementActivity;
import ehr.cfcs.com.ehr.Main.ViewLeavemangementActivity;
import ehr.cfcs.com.ehr.Model.LeaveManagementModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 18-09-2017.
 */

public class LeaveMangementAdapter extends RecyclerView.Adapter<LeaveMangementAdapter.ViewHolder>
{

    public Context context ;
    public ArrayList<LeaveManagementModel> list =new ArrayList<>();
    Activity activity;

    public LeaveMangementAdapter(Context context, ArrayList<LeaveManagementModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.leavemangaemnet_layout, parent, false);
        return new LeaveMangementAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final LeaveManagementModel model = list.get(position);

        holder.leaveTypeTxt.setText(model.getLeaveType());
        holder.startDateTxt.setText(model.getStartDate());
        holder.endDateTxt.setText(model.getEndDate());
        holder.appliedOnTxt.setText(model.getAppliedOn());
        holder.statusTxt.setText(model.getStatus());

        holder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ViewLeavemangementActivity.class);
                i.putExtra("LeaveApplication_Id",model.getLeaveApplication_Id());
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });

      /*  if (position % 2 == 1) {
            holder.mainLay.setCardBackgroundColor(context.getResources().getColor(R.color.col1));
        }
        else{
            holder.mainLay.setCardBackgroundColor(context.getResources().getColor(R.color.col2));
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView leaveTypeTxt,startDateTxt,endDateTxt,appliedOnTxt, statusTxt;

        public LinearLayout mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            leaveTypeTxt = (TextView)itemView.findViewById(R.id.leave_type);
            startDateTxt = (TextView)itemView.findViewById(R.id.start_date);
            endDateTxt = (TextView)itemView.findViewById(R.id.end_date);
            appliedOnTxt = (TextView)itemView.findViewById(R.id.appliedon);
            statusTxt = (TextView)itemView.findViewById(R.id.status);

            mainLay = (LinearLayout)itemView.findViewById(R.id.leave_management_main_lay);





        }
    }
}

package ehr.cfcs.com.ehr.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Main.ViewLeavemangementActivity;
import ehr.cfcs.com.ehr.Main.ViewShortLeaveHistoryActivity;
import ehr.cfcs.com.ehr.Model.ShortLeaveHistoryModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 09-10-2017.
 */

public class ShortLeaveHistoryAdapter extends RecyclerView.Adapter<ShortLeaveHistoryAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<ShortLeaveHistoryModel> list = new ArrayList<>();
    public Activity activity;

    public ShortLeaveHistoryAdapter(Context context, ArrayList<ShortLeaveHistoryModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.short_leave_history_item, parent, false);
        return new ShortLeaveHistoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ShortLeaveHistoryModel model = list.get(position);

        holder.leaveTypeTxt.setText(model.getLeaveTypeName());
        holder.startDateTxt.setText(model.getStartDate());
        holder.timeFromTxt.setText(model.getTimeFrom());
        holder.timeToTxt.setText(model.getTimeTo());
        holder.AppliedDateTxt.setText(model.getAppliedDate());
        holder.statusTxt.setText(model.getStatusText());
        holder.commentTxt.setText(model.getCommentText());
        holder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ViewShortLeaveHistoryActivity.class);
                i.putExtra("LeaveApplication_Id",model.getLeaveApplication_Id());
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
        public TextView leaveTypeTxt,startDateTxt,timeFromTxt,timeToTxt, AppliedDateTxt,statusTxt,commentTxt;

        public LinearLayout mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            leaveTypeTxt = (TextView)itemView.findViewById(R.id.short_leave_type);
            startDateTxt = (TextView)itemView.findViewById(R.id.short_start_date);
            timeFromTxt = (TextView)itemView.findViewById(R.id.short_time_from);
            timeToTxt = (TextView)itemView.findViewById(R.id.short_time_to);
            statusTxt = (TextView)itemView.findViewById(R.id.short_status);
            AppliedDateTxt = (TextView)itemView.findViewById(R.id.short_applied_date);
            commentTxt = (TextView)itemView.findViewById(R.id.short_comment);

            mainLay = (LinearLayout)itemView.findViewById(R.id.short_leave_main_lay);





        }
    }
}

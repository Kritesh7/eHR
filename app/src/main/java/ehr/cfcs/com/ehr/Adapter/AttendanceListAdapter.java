package ehr.cfcs.com.ehr.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.AttendanceListModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 10-10-2017.
 */

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<AttendanceListModel> list = new ArrayList<>();
    public Activity activity;

    public AttendanceListAdapter(Context context, ArrayList<AttendanceListModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.attendance_list_layout, parent, false);
        return new AttendanceListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AttendanceListModel model = list.get(position);

        holder.dateTxt.setText(model.getDate());
        holder.inTimeTxt.setText(model.getInTime());
        holder.outTimeTxt.setText(model.getOutTime());
        holder.workTimeTxt.setText(model.getWorkTime());
        holder.halfDayTxt.setText(model.getHalfday());
        holder.lateArivalTxt.setText(model.getLateArrival());
        holder.earlyLeavingTxt.setText(model.getEarlyLeaving());
        holder.statusTxt.setText(model.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTxt,inTimeTxt,outTimeTxt,workTimeTxt,halfDayTxt,lateArivalTxt,earlyLeavingTxt,statusTxt;

        public LinearLayout mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            dateTxt = (TextView)itemView.findViewById(R.id.attendancelist_date);
            inTimeTxt = (TextView)itemView.findViewById(R.id.attendancelist_intime);
            outTimeTxt = (TextView)itemView.findViewById(R.id.attendancelist_outtime);
            workTimeTxt = (TextView)itemView.findViewById(R.id.attendancelist_worktime);
            halfDayTxt = (TextView)itemView.findViewById(R.id.attendacelist_halfday);
            lateArivalTxt = (TextView)itemView.findViewById(R.id.attendacelist_latearival);
            earlyLeavingTxt = (TextView)itemView.findViewById(R.id.attendacelist_earlyleaving);
            statusTxt = (TextView)itemView.findViewById(R.id.attendacelist_status);


            mainLay = (LinearLayout)itemView.findViewById(R.id.leave_management_main_lay);





        }
    }
}

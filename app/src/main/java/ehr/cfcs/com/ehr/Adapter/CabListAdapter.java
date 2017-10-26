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

import ehr.cfcs.com.ehr.Main.AddNewStationaryRequestActivity;
import ehr.cfcs.com.ehr.Main.ViewCabDetailsActivity;
import ehr.cfcs.com.ehr.Main.ViewRequestDetailsActivity;
import ehr.cfcs.com.ehr.Model.CabListModel;
import ehr.cfcs.com.ehr.Model.DocumentListModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 18-09-2017.
 */

public class CabListAdapter extends RecyclerView.Adapter<CabListAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<CabListModel> list = new ArrayList<>();
    public Activity activity;

    public CabListAdapter(Context context, ArrayList<CabListModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cab_item_layout, parent, false);
        return new CabListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CabListModel model = list.get(position);

        holder.empNameTxt.setText(model.getEmployName());
        holder.zoneNameTxt.setText(model.getZoneName());
        holder.cityNameTXT.setText(model.getCityName());
        holder.bookingDateTxt.setText(model.getBookingDate());
        holder.requestDateTxt.setText(model.getRequestDate());
        holder.followupDateTxt.setText(model.getFollowUpDate());
        holder.statusTxt.setText(model.getStatus());

        holder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ViewCabDetailsActivity.class);
                i.putExtra("Bid",model.getBID());
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });

        /*if (position % 2 == 1) {
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
        public TextView empNameTxt,zoneNameTxt,cityNameTXT,requestDateTxt, bookingDateTxt,followupDateTxt,statusTxt;

        public LinearLayout mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            empNameTxt = (TextView)itemView.findViewById(R.id.cab_employename);
            zoneNameTxt = (TextView)itemView.findViewById(R.id.cab_zonename);
            cityNameTXT = (TextView)itemView.findViewById(R.id.city);
            requestDateTxt = (TextView)itemView.findViewById(R.id.cab_requestdate);
            bookingDateTxt = (TextView)itemView.findViewById(R.id.bookingdate);
            followupDateTxt = (TextView)itemView.findViewById(R.id.cab_followupdate);
            statusTxt = (TextView)itemView.findViewById(R.id.cab_status);

            mainLay = (LinearLayout)itemView.findViewById(R.id.cab_main_lay);
        }
    }
}

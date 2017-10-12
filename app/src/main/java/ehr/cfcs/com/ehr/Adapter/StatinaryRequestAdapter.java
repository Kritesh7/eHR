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

import ehr.cfcs.com.ehr.Main.ViewLeavemangementActivity;
import ehr.cfcs.com.ehr.Main.ViewRequestDetailsActivity;
import ehr.cfcs.com.ehr.Model.StationaryRequestModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 18-09-2017.
 */

public class StatinaryRequestAdapter extends RecyclerView.Adapter<StatinaryRequestAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<StationaryRequestModel> list = new ArrayList<>();
    public Activity activity;

    public StatinaryRequestAdapter(Context context, ArrayList<StationaryRequestModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.staionary_request_item_layout, parent, false);
        return new StatinaryRequestAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final StationaryRequestModel model = list.get(position);

        holder.empNameTxt.setText(model.getEmployName());
        holder.zoneNameTxt.setText(model.getZoneName());
        holder.qunatityTxt.setText(model.getQuantity());
        holder.idleclouserDateTxt.setText(model.getIdleClousersDate());
        holder.requestDateTxt.setText(model.getRequestDate());
        holder.followupDateTxt.setText(model.getFollowUpDate());
        holder.statusTxt.setText(model.getStatus());

        holder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ViewRequestDetailsActivity.class);
                i.putExtra("Rid",model.getRID());
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });

       /* if (position % 2 == 1) {
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
        public TextView empNameTxt,zoneNameTxt,qunatityTxt,requestDateTxt, idleclouserDateTxt,followupDateTxt,statusTxt;

        public LinearLayout mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            empNameTxt = (TextView)itemView.findViewById(R.id.employename);
            zoneNameTxt = (TextView)itemView.findViewById(R.id.zonename);
            qunatityTxt = (TextView)itemView.findViewById(R.id.quantity);
            requestDateTxt = (TextView)itemView.findViewById(R.id.requestdate);
            idleclouserDateTxt = (TextView)itemView.findViewById(R.id.idleclouserdate);
            followupDateTxt = (TextView)itemView.findViewById(R.id.followupdate);
            statusTxt = (TextView)itemView.findViewById(R.id.statinarystatus);

            mainLay = (LinearLayout)itemView.findViewById(R.id.stationary_request_main_lay);
        }
    }
}

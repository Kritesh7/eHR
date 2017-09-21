package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Fragment.HotelBookingListFragment;
import ehr.cfcs.com.ehr.Model.HotelBookingListModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 18-09-2017.
 */

public class HotelBookingListAdapter extends RecyclerView.Adapter<HotelBookingListAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<HotelBookingListModel> list = new ArrayList<>();

    public HotelBookingListAdapter(Context context, ArrayList<HotelBookingListModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.hotel_bokking_list_item_layout, parent, false);
        return new HotelBookingListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HotelBookingListModel model = list.get(position);

        holder.empNameTxt.setText(model.getEmployeDate());
        holder.cityNameTXT.setText(model.getCityName());
        holder.requestDateTxt.setText(model.getRequestDate());
        holder.checkInDate.setText(model.getCheckInDate());
        holder.checkInTime.setText(model.getCheckInTime());
        holder.checkOutDate.setText(model.getCheckOutDate());
        holder.followupDateTxt.setText(model.getFollowUpDate());
        holder.statusTxt.setText(model.getStaus());

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
        public TextView empNameTxt,cityNameTXT,requestDateTxt, checkInDate,checkInTime,checkOutDate,followupDateTxt,statusTxt;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            empNameTxt = (TextView)itemView.findViewById(R.id.hotel_employename);
            cityNameTXT = (TextView)itemView.findViewById(R.id.hotel_cityname);
            checkInDate = (TextView)itemView.findViewById(R.id.checkindate);
            checkInTime = (TextView)itemView.findViewById(R.id.checkintime);
            checkOutDate = (TextView)itemView.findViewById(R.id.checkoutdate);
            requestDateTxt = (TextView)itemView.findViewById(R.id.hotel_requestdate);
            followupDateTxt = (TextView)itemView.findViewById(R.id.hotelfollowdate);
            statusTxt = (TextView)itemView.findViewById(R.id.hotel_status);

            mainLay = (CardView)itemView.findViewById(R.id.hotel_main_lay);
        }
    }
}

package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.AppreceationModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 25-09-2017.
 */

public class AppreceationAdapter extends RecyclerView.Adapter<AppreceationAdapter.ViewHolder>
{
    public Context context ;
    public ArrayList<AppreceationModel> list = new ArrayList<>();

    public AppreceationAdapter(Context context, ArrayList<AppreceationModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.appreceation_item_layout, parent, false);
        return new AppreceationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AppreceationModel model = list.get(position);

        holder.appreceaionDateTxt.setText(model.getAppreceationDate());
        holder.appreceationDetailsTxt.setText(model.getAppreceationDetails());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView appreceaionDateTxt,appreceationDetailsTxt;

        //public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            appreceaionDateTxt = (TextView)itemView.findViewById(R.id.appreceation_date);
            appreceationDetailsTxt = (TextView)itemView.findViewById(R.id.appreceation_details);
            /*issuesDateTxt = (TextView)itemView.findViewById(R.id.issuesdate);
            estimatedReturnDateTxt = (TextView)itemView.findViewById(R.id.estimated_return_date);
            issuesReasonsTxt = (TextView)itemView.findViewById(R.id.issuesreasons);
            remarkTxt = (TextView)itemView.findViewById(R.id.remark);*/

           // mainLay = (CardView)itemView.findViewById(R.id.assets_main_lay);
        }
    }
}

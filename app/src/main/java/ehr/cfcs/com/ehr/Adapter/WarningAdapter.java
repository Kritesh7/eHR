package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.WarningModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 25-09-2017.
 */

public class WarningAdapter extends RecyclerView.Adapter<WarningAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<WarningModel> list = new ArrayList<>();

    public WarningAdapter(Context context, ArrayList<WarningModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.warning_item_layout, parent, false);
        return new WarningAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WarningModel model = list.get(position);

        holder.warningDteTxt.setText(model.getWarningDate());
        holder.warningDetailsTxt.setText(model.getWarningDetails());
        holder.warningTitileTxt.setText(model.getWarningTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView warningDteTxt,warningDetailsTxt, warningTitileTxt;

        //public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            warningDteTxt = (TextView)itemView.findViewById(R.id.warning_date);
            warningDetailsTxt = (TextView)itemView.findViewById(R.id.warning_details);
            warningTitileTxt = (TextView)itemView.findViewById(R.id.warning_title);

        }
    }
}

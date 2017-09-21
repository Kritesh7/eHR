package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.DocumentListModel;
import ehr.cfcs.com.ehr.Model.StationaryRequestModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 18-09-2017.
 */

public class DocumentListAdapter extends  RecyclerView.Adapter<DocumentListAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<DocumentListModel> list = new ArrayList<>();

    public DocumentListAdapter(Context context, ArrayList<DocumentListModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.document_list_item_layout, parent, false);
        return new DocumentListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DocumentListModel model = list.get(position);

        holder.empNameTxt.setText(model.getEmployName());
        holder.zoneNameTxt.setText(model.getZoneName());
        holder.qunatityTxt.setText(model.getQunatity());
        holder.idleclouserDateTxt.setText(model.getIdleClosersDate());
        holder.requestDateTxt.setText(model.getRquestDate());
        holder.followupDateTxt.setText(model.getFolllowUpDate());
        holder.statusTxt.setText(model.getStatus());

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
        public TextView empNameTxt,zoneNameTxt,qunatityTxt,requestDateTxt, idleclouserDateTxt,followupDateTxt,statusTxt;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            empNameTxt = (TextView)itemView.findViewById(R.id.document_employename);
            zoneNameTxt = (TextView)itemView.findViewById(R.id.document_zonename);
            qunatityTxt = (TextView)itemView.findViewById(R.id.document_quantity);
            requestDateTxt = (TextView)itemView.findViewById(R.id.document_requestdate);
            idleclouserDateTxt = (TextView)itemView.findViewById(R.id.document_idleclouserdate);
            followupDateTxt = (TextView)itemView.findViewById(R.id.document_followupdate);
            statusTxt = (TextView)itemView.findViewById(R.id.document_statinarystatus);

            mainLay = (CardView)itemView.findViewById(R.id.document_main_lay);
        }
    }
}

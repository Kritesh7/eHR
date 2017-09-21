package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.OfficealyModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 20-09-2017.
 */

public class OfficelyAdapter extends RecyclerView.Adapter<OfficelyAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<OfficealyModel> list = new ArrayList<>();

    public OfficelyAdapter(Context context, ArrayList<OfficealyModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.officely_item_layout, parent, false);
        return new OfficelyAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        OfficealyModel model = list.get(position);

        holder.documentTypeTxt.setText(model.getDocumentType());
        holder.noOfDocumentTxt.setText(model.getNoOfDocuments());
        holder.issuesDateTxt.setText(model.getIssuesDate());
        holder.expiryDateTxt.setText(model.getExpiryDate());
        holder.placeOfIssuesTxt.setText(model.getPlaceOfIssues());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView documentTypeTxt,noOfDocumentTxt,issuesDateTxt,expiryDateTxt, placeOfIssuesTxt;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            documentTypeTxt = (TextView)itemView.findViewById(R.id.document_type);
            noOfDocumentTxt = (TextView)itemView.findViewById(R.id.no_document);
            issuesDateTxt = (TextView)itemView.findViewById(R.id.officely_issue_date);
            expiryDateTxt = (TextView)itemView.findViewById(R.id.officely_expiry_date);
            placeOfIssuesTxt = (TextView)itemView.findViewById(R.id.placeof_issues);

           // mainLay = (CardView)itemView.findViewById(R.id.leave_management_main_lay);

        }
    }
}

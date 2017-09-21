package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.PreviousExpreinceModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 21-09-2017.
 */

public class PreviousExpreinceAdapter extends RecyclerView.Adapter<PreviousExpreinceAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<PreviousExpreinceModel> list = new ArrayList<>();

    public PreviousExpreinceAdapter(Context context, ArrayList<PreviousExpreinceModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.previousexprience_item_layout, parent, false);
        return new PreviousExpreinceAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PreviousExpreinceModel model = list.get(position);

        holder.companyNametxt.setText(model.getCompanyName());
        holder.joingDateTxt.setText(model.getJoiningDate());
        holder.jobDescTxt.setText(model.getJobDescription());
        holder.jobPeriodeTxt.setText(model.getJobPeriode());
        holder.designationTxt.setText(model.getDesignation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView companyNametxt,joingDateTxt,jobDescTxt,jobPeriodeTxt, designationTxt;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            companyNametxt = (TextView)itemView.findViewById(R.id.companyname);
            joingDateTxt = (TextView)itemView.findViewById(R.id.joiningdate);
            jobDescTxt = (TextView)itemView.findViewById(R.id.job_description);
            jobPeriodeTxt = (TextView)itemView.findViewById(R.id.Job_period);
            designationTxt = (TextView)itemView.findViewById(R.id.designation);

          //  mainLay = (CardView)itemView.findViewById(R.id.traning_main_lay);
        }
    }
}

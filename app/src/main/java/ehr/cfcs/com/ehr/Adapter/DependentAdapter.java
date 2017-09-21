package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.DependentModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 20-09-2017.
 */

public class DependentAdapter extends RecyclerView.Adapter<DependentAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<DependentModel> list = new ArrayList<>();

    public DependentAdapter(Context context, ArrayList<DependentModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dependent_item_layout, parent, false);
        return new DependentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DependentModel model = list.get(position);

        holder.nameTxt.setText(model.getName());
        holder.dobTxt.setText(model.getDob());
        holder.genderTxt.setText(model.getGender());
        holder.relationshipTxt.setText(model.getRelationship());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTxt,dobTxt,genderTxt,relationshipTxt;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTxt = (TextView)itemView.findViewById(R.id.dependent_name);
            dobTxt = (TextView)itemView.findViewById(R.id.dependent_dob);
            genderTxt = (TextView)itemView.findViewById(R.id.dependent_gender);
            relationshipTxt = (TextView)itemView.findViewById(R.id.dependent_relationship);

        }
    }
}

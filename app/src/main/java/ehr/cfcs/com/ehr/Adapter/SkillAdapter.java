package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.SkillsModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 21-09-2017.
 */

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<SkillsModel> list = new ArrayList<>();

    public SkillAdapter(Context context, ArrayList<SkillsModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.skils_item_layout, parent, false);
        return new SkillAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SkillsModel model = list.get(position);

        holder.skillTxt.setText(model.getSkill());
        holder.proficencyTxt.setText(model.getProficency());
        holder.sourceTxt.setText(model.getSource());
        holder.lastUsedTxt.setText(model.getLastUsed());

        if (model.getCurrentUsed().equalsIgnoreCase("true")) {
            holder.currentUsedTxt.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);
        }else
            {
                holder.currentUsedTxt.setVisibility(View.GONE);
                holder.view.setVisibility(View.GONE);
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView skillTxt,proficencyTxt,sourceTxt,lastUsedTxt;
        public View view;
        public LinearLayout currentUsedTxt;

        public ViewHolder(View itemView) {
            super(itemView);

            skillTxt = (TextView)itemView.findViewById(R.id.skill);
            proficencyTxt = (TextView)itemView.findViewById(R.id.proficiency);
            sourceTxt = (TextView)itemView.findViewById(R.id.source);
            lastUsedTxt = (TextView)itemView.findViewById(R.id.lastused);
            currentUsedTxt = (LinearLayout)itemView.findViewById(R.id.currentused);
            view = (View) itemView.findViewById(R.id.view);

           // mainLay = (CardView)itemView.findViewById(R.id.traning_main_lay);
        }
    }
}

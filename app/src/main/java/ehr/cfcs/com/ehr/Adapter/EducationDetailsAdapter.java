package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.EducationModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 21-09-2017.
 */

public class EducationDetailsAdapter extends RecyclerView.Adapter<EducationDetailsAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<EducationModel> list = new ArrayList<>();

    public EducationDetailsAdapter(Context context, ArrayList<EducationModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.education_detials_item_layout, parent, false);
        return new EducationDetailsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        EducationModel model = list.get(position);

        holder.qualificationTxt.setText(model.getQualification());
        holder.desciplineTxt.setText(model.getDescipline());
        holder.passingDateTxt.setText(model.getPassingDate());
        holder.instituteTxt.setText(model.getInstitute());
        holder.courseTypeTxt.setText(model.getCourseType());
        holder.highestDegreeTxt.setText(model.getHighestDegree());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView qualificationTxt,desciplineTxt,passingDateTxt,instituteTxt, courseTypeTxt,highestDegreeTxt;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            qualificationTxt = (TextView)itemView.findViewById(R.id.qualification);
            desciplineTxt = (TextView)itemView.findViewById(R.id.decipline);
            passingDateTxt = (TextView)itemView.findViewById(R.id.passingdate);
            instituteTxt = (TextView)itemView.findViewById(R.id.institute);
            courseTypeTxt = (TextView)itemView.findViewById(R.id.coursetype);
            highestDegreeTxt = (TextView)itemView.findViewById(R.id.highestdegree);

           // mainLay = (CardView)itemView.findViewById(R.id.leave_management_main_lay);





        }
    }
}

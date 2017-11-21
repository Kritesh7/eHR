package ehr.cfcs.com.ehr.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Main.HomeActivity;
import ehr.cfcs.com.ehr.Manager.ManagerActivity.PersonalDeatilsActivity;
import ehr.cfcs.com.ehr.Model.AttendanceLogDetailsModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Admin on 18-11-2017.
 */

public class AttendanceLogListAdapter extends RecyclerView.Adapter<AttendanceLogListAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<AttendanceLogDetailsModel> list = new ArrayList<>();
    public Activity activity;
    public PhotoViewAttacher mAttacher;

    public AttendanceLogListAdapter(Context context, ArrayList<AttendanceLogDetailsModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.punch_loglist_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AttendanceLogDetailsModel model = list.get(position);

        holder.nameTxt.setText(model.getName() +"("+model.getEmpId()+")");
        holder.designationTxt.setText(model.getDesignation());
        holder.punchDateTxt.setText(model.getPunchDate());
        holder.punchTimeTxt.setText(model.getPunchTime());
        holder.punchLocationTxt.setText(model.getPunchLocation());
        holder.remark.setText(model.getRemark());
        holder.punchTypeTxt.setText(model.getPunchType());

        if (model.getPunchType().equalsIgnoreCase("Machine"))
        {
            holder.punchLocationLay.setVisibility(View.GONE);
            holder.remark.setVisibility(View.GONE);
            holder.remarkView.setVisibility(View.GONE);
            holder.locationView.setVisibility(View.GONE);

        }else
            {
                holder.punchLocationLay.setVisibility(View.VISIBLE);
                holder.remark.setVisibility(View.VISIBLE);
                holder.remarkView.setVisibility(View.VISIBLE);
                holder.locationView.setVisibility(View.VISIBLE);

            }

        Log.e("check image Url",SettingConstant.DownloadUrl + model.getProfilePic());
        //set profile Image
        Picasso pic = Picasso.with(activity);
        pic.setIndicatorsEnabled(true);
        pic.with(activity).cancelRequest(holder.proImg);
        pic.with(activity)
                .load(SettingConstant.DownloadUrl + model.getProfilePic())
                .placeholder(R.drawable.prf)
                .error(R.drawable.prf)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .tag(activity)
                .into(holder.proImg);


        holder.proImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Display display = activity.getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int height = display.getHeight();
                loadPhoto(holder.proImg,width,height);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTxt,designationTxt,punchTimeTxt,punchDateTxt,punchTypeTxt,punchLocationTxt,remark;
        public View locationView, remarkView;
        public LinearLayout punchLocationLay, reamrkLay;
        public de.hdodenhof.circleimageview.CircleImageView proImg;

        public ViewHolder(View itemView) {
            super(itemView);

            designationTxt = (TextView)itemView.findViewById(R.id.designation);
            punchTimeTxt = (TextView)itemView.findViewById(R.id.punchtime);
            punchDateTxt = (TextView)itemView.findViewById(R.id.punchdate);
            punchTypeTxt = (TextView)itemView.findViewById(R.id.punchtype);
            punchLocationTxt = (TextView)itemView.findViewById(R.id.punchlocation);
            remark = (TextView)itemView.findViewById(R.id.remark);
            nameTxt = (TextView) itemView.findViewById(R.id.name);
            remarkView = (View) itemView.findViewById(R.id.reameklay) ;
            locationView = (View) itemView.findViewById(R.id.locationview) ;
            proImg = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.pro_image);


            punchLocationLay = (LinearLayout)itemView.findViewById(R.id.punchlocationlay);
            reamrkLay = (LinearLayout)itemView.findViewById(R.id.reameklay);





        }
    }

    private void loadPhoto(ImageView imageView, int width, int height) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.setContentView(R.layout.custom_fullimage_dialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_fullimage_dialoge,
                (ViewGroup)activity.findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        ImageView croosImg = (ImageView) layout.findViewById(R.id.imgClose);

        croosImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        image.setImageDrawable(imageView.getDrawable());
        image.getLayoutParams().height = height;
        image.getLayoutParams().width = width;
        mAttacher = new PhotoViewAttacher(image);
        image.requestLayout();
        dialog.setContentView(layout);
        dialog.show();

    }
}

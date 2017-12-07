package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.AddNewStationoryRequestModel;
import ehr.cfcs.com.ehr.Model.getQuantAndRemarkModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 06-12-2017.
 */

public class AddStationoryRequestNewAdapter extends RecyclerView.Adapter<AddStationoryRequestNewAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<AddNewStationoryRequestModel> list = new ArrayList<>();

    public AddStationoryRequestNewAdapter(Context context, ArrayList<AddNewStationoryRequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.stationory_request_new_adapter_layout, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AddNewStationoryRequestModel model = list.get(position);


        holder.nameTxt.setText(model.getItemName());
        holder.quantityTxt.setText(model.getQuantity());
        holder.remarkTxt.setText(model.getRemark());

        holder.quantityTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().equalsIgnoreCase("")) {
                    try {
                        if (Integer.parseInt(model.getSetMaxQuantity()) >= Integer.parseInt(charSequence.toString())) {

                            list.get(position).quantity = charSequence.toString();

                        } else {
                            //  Toast.makeText(context,"Maximum quantity exedded",Toast.LENGTH_LONG).show();
                            holder.quantityTxt.setError("Max quantity : " + model.getSetMaxQuantity());
                            holder.quantityTxt.setText("");

                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }else
                    {
                       // list.remove(position);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.remarkTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!holder.quantityTxt.getText().toString().equalsIgnoreCase("")) {

                    if (!charSequence.toString().equalsIgnoreCase("")) {

                        list.get(position).remark = charSequence.toString();

                    } else {

                       // list.get(position).setRemark(model.getRemark());
                    }

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {


        return list.size();

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTxt;
        public EditText quantityTxt, remarkTxt;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTxt = (TextView)itemView.findViewById(R.id.itemname);
            quantityTxt = (EditText)itemView.findViewById(R.id.edit_quantity);
            remarkTxt = (EditText)itemView.findViewById(R.id.remark);

        }
    }

    public ArrayList<AddNewStationoryRequestModel> getListData()
    {
        return list;
    }


}

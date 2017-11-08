package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import ehr.cfcs.com.ehr.Interface.AddItemInterface;
import ehr.cfcs.com.ehr.Model.BookMeaPrevisionModel;
import ehr.cfcs.com.ehr.Model.SendListModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.EditTextWatcher;

/**
 * Created by Admin on 21-09-2017.
 */

public class BookMeaPrevisonAdapter extends BaseAdapter {

    public ArrayList<BookMeaPrevisionModel> list = new ArrayList<>();
    public static ArrayList<SendListModel> sendList = new ArrayList<>();
    ArrayList<String> selectedStrings = new ArrayList<String>();
    ArrayList<String> selectedId = new ArrayList<String>();
    ArrayList<String> selectedQuantity = new ArrayList<String>();
    ArrayList<String> selectedRemark = new ArrayList<String>();
    public Context context;
    LayoutInflater inflater;
    public boolean flag = false;
    public int postion;
    public AddItemInterface ItemInterface;


    public BookMeaPrevisonAdapter(ArrayList<BookMeaPrevisionModel> list, Context context,AddItemInterface ItemInterface) {

        this.list = list;
        this.context = context;
        this.ItemInterface = ItemInterface;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;



            holder = new ViewHolder();
            holder.mWatcher = new EditTextWatcher();
            view = inflater.inflate(R.layout.book_me_a_previoson_item_layout, null);
            holder.tvName = (CheckBox) view.findViewById(R.id.itemcheckbox);
            holder.quantityTxt = (EditText)view.findViewById(R.id.edit_quantity);
            holder.remarkTxt = (EditText)view.findViewById(R.id.remark);






        holder.tvName.setText(list.get(i).getItemName());

        holder.quantityTxt.setText(list.get(i).getFillQuanty());
        holder.remarkTxt.setText(list.get(i).getRemarkString());

        //Edit Text Listeners:-

        holder.quantityTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                list.get(i).setFillQuanty(s.toString());
            }
        });

        holder.remarkTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                list.get(i).setRemarkString(editable.toString());
            }
        });





        final ViewHolder finalHolder = holder;
        holder.tvName.setTag(i);
        holder.tvName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {


                    //testing mode

                    if (!finalHolder.quantityTxt.getText().toString().equalsIgnoreCase("")) {

                        if (selectedStrings.contains(finalHolder.tvName.getText().toString()))
                        {

                        }else {

                            //check max quantity
                            if (Integer.parseInt(list.get(i).getMaxQuantity())>=
                                    Integer.parseInt(finalHolder.quantityTxt.getText().toString())) {

                                selectedStrings.add(finalHolder.tvName.getText().toString());
                                selectedId.add(list.get(i).getItemID());
                                selectedQuantity.add(finalHolder.quantityTxt.getText().toString());
                                selectedRemark.add(finalHolder.remarkTxt.getText().toString());

                            }else
                                {
                                    Toast.makeText(context, "Item Qty is greater than maximum "+ finalHolder.tvName.getText().toString()+
                                            " for request", Toast.LENGTH_SHORT).show();
                                    finalHolder.quantityTxt.setText("");
                                    finalHolder.tvName.setChecked(false);
                                }
                        }

                        Log.e("adapter list size", sendList.size() + "");

                    }else
                        {
                            Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show();
                            finalHolder.tvName.setChecked(false);
                        }



                        flag = true;
                        postion = i;
                        list.get(i).setToKill(true);



                }else
                    {

                        //visibile Gone Widget
                        selectedStrings.remove(finalHolder.tvName.getText().toString());
                        selectedId.remove(list.get(i).getItemID());
                        selectedRemark.remove(finalHolder.remarkTxt.getText().toString());
                        list.get(i).setToKill(false);
                        selectedQuantity.remove(finalHolder.quantityTxt.getText().toString());

                        finalHolder.quantityTxt.setText("");
                        finalHolder.remarkTxt.setText("");

                        flag = false;
                    }
            }
        });


        holder.tvName.setChecked(list.get(i).isToKill() );
        if (list.get(i).isToKill() == true || list.get(i).getCheckValue().equalsIgnoreCase("true"))
        {
            holder.tvName.setChecked(true);

        }
        else
        {
            holder.tvName.setChecked(false);
        }



        return view;
    }


    private class ViewHolder {

        CheckBox tvName;
        EditText quantityTxt, remarkTxt;
        EditTextWatcher mWatcher;
     //   LinearLayout mainLay,primory_layout;
    }

    //get the data
    public ArrayList<String> getSelectedString(){
        return selectedStrings;
    }
    public ArrayList<String> getSelectedId(){
        return selectedId;
    }
    public ArrayList<String> getSelectedQuan(){
        return selectedQuantity;
    }
    public ArrayList<String> getSelectedRemark(){
        return selectedRemark;
    }

}


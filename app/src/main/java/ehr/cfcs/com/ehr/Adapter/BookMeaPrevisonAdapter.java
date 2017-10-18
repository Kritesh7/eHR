package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Interface.AddItemInterface;
import ehr.cfcs.com.ehr.Model.BookMeaPrevisionModel;
import ehr.cfcs.com.ehr.Model.SendListModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 21-09-2017.
 */

public class BookMeaPrevisonAdapter extends BaseAdapter {

    public ArrayList<BookMeaPrevisionModel> list = new ArrayList<>();
    public static ArrayList<SendListModel> sendList = new ArrayList<>();
    public Context context;
    LayoutInflater inflater;
    public boolean flag = false;
    public int postion;
    public AddItemInterface ItemInterface;
    public Integer pos;
    public boolean isFlag;


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
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        CheckBox checkBox = null;



        if (view == null) {

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.book_me_a_previoson_item_layout, null);
            holder.tvName = (CheckBox) view.findViewById(R.id.itemcheckbox);
            holder.mainLay = (LinearLayout)view.findViewById(R.id.mainlay);
            holder.quantityTxt = (EditText)view.findViewById(R.id.edit_quantity);
            holder.remarkTxt = (EditText)view.findViewById(R.id.remark);
            holder.primory_layout = (LinearLayout)view.findViewById(R.id.primory_layout);


            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvName.setText(list.get(i).getItemName());
        holder.remarkTxt.setText(list.get(i).getRemark());
        holder.quantityTxt.setText(list.get(i).getMaxQuantity());

        final ViewHolder finalHolder = holder;



       /* //check check box is check or not
        if (list.get(i).getCheckValue().equalsIgnoreCase("true"))
        {
            holder.tvName.setChecked(true);

            //visibile widget
            TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
            finalHolder.mainLay.setVisibility(View.VISIBLE);


        }else
            {
                holder.tvName.setChecked(false);

                //invisibile widgit
                TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                finalHolder.mainLay.setVisibility(View.GONE);
            }
*/



        //add data in a list
        final ViewHolder finalHolder1 = holder;

       /* //holder.tvName.setTag(R.integer.btnplusview, view);
        holder.tvName.setTag(i);
        holder.tvName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {

                    sendList.add(new SendListModel(list.get(postion).getItemID(), list.get(postion).getItemName(), finalHolder1.quantityTxt.getText().toString(),
                            finalHolder1.remarkTxt.getText().toString()));

                    //save in interface
                    ItemInterface.getAllItem(sendList);

                    //visibile View
                    TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                    finalHolder.mainLay.setVisibility(View.VISIBLE);

                    flag = true;
                    postion = i;
                    list.get(i).setToKill(true);


                }else
                    {
                        //visibile Gone Widget
                        TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                        finalHolder.mainLay.setVisibility(View.GONE);

                        flag = false;
                    }
            }
        });*/


        holder.tvName.setChecked(list.get(i).isToKill() );

        if (list.get(i).isToKill() == true || list.get(i).getCheckValue().equalsIgnoreCase("true"))
        {
            holder.tvName.setChecked(true);

            TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
            finalHolder.mainLay.setVisibility(View.VISIBLE);
        }else
            {
                holder.tvName.setChecked(false);

                TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                finalHolder.mainLay.setVisibility(View.GONE);
            }

        //holder.tvName.setTag(R.integer.btnplusview, view);
        holder.tvName.setTag(i);
        final ViewHolder finalHolder2 = holder;

        holder.tvName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                pos = (Integer)  finalHolder2.tvName.getTag();

                if (b)
                {
                    postion = i;
                    isFlag = true;
                    list.get(pos).setToKill(true);
                    //visibile View
                    TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                    finalHolder.mainLay.setVisibility(View.VISIBLE);

                    sendList.add(new SendListModel(list.get(i).getItemID(),list.get(i).getItemName(),finalHolder2.quantityTxt.getText().toString(),
                            finalHolder2.remarkTxt.getText().toString()));
                  /*  sendList.add(new SendListModel(list.get(i).getItemID(), list.get(i).getItemName(), finalHolder1.quantityTxt.getText().toString(),
                            finalHolder1.remarkTxt.getText().toString()));

                    //save in interface
                    ItemInterface.getAllItem(i);*/

                    ItemInterface.getAllItem(sendList);
                }else
                    {
                        isFlag = false;
                        list.get(pos).setToKill(false);

                        TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                        finalHolder.mainLay.setVisibility(View.GONE);
                    }

            }
        });

       /* holder.quantityTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (isFlag)
                {

                    sendList.add(new SendListModel(list.get(pos).getItemID(), list.get(pos).getItemName(), finalHolder2.quantityTxt.getText().toString(),
                            finalHolder2.remarkTxt.getText().toString()));

                    //save in interface
                    ItemInterface.getAllItem(sendList);

                }
            }
        });*/
        /*holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  View tempview = (View) finalHolder2.tvName.getTag(R.integer.btnplusview);
                Integer pos = (Integer)  finalHolder2.tvName.getTag();
               // Toast.makeText(context, "Checkbox " + pos + " clicked!", Toast.LENGTH_SHORT).show();

                if (list.get(pos).isToKill()){
                    list.get(pos).setToKill(false);
                    //visibile Gone Widget
                    TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                    finalHolder.mainLay.setVisibility(View.GONE);
                }
                else {
                    list.get(pos).setToKill(true);
                    //visibile View
                    TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                    finalHolder.mainLay.setVisibility(View.VISIBLE);

                    sendList.add(new SendListModel(list.get(postion).getItemID(), list.get(postion).getItemName(), finalHolder2.quantityTxt.getText().toString(),
                            finalHolder2.remarkTxt.getText().toString()));

                    //save in interface
                    ItemInterface.getAllItem(sendList);

                }

            }
        });*/

        return view;
    }

    public  ArrayList<SendListModel> getSelectedString(){
        return sendList;
    }

    private class ViewHolder {

        CheckBox tvName;
        EditText quantityTxt, remarkTxt;
        LinearLayout mainLay,primory_layout;
    }


}


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
    ArrayList<String> secondQuant = new ArrayList<String>();
    ArrayList<String> selectedremark = new ArrayList<String>();
    public Context context;
    LayoutInflater inflater;
    public boolean flag = false;
    public int postion;
    public AddItemInterface ItemInterface;
    String scoresToUpdate[];


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

       /* if (view == null) {*/

            holder = new ViewHolder();
            holder.mWatcher = new EditTextWatcher();
            view = inflater.inflate(R.layout.book_me_a_previoson_item_layout, null);
            holder.tvName = (CheckBox) view.findViewById(R.id.itemcheckbox);
            holder.quantityTxt = (EditText)view.findViewById(R.id.edit_quantity);
            holder.remarkTxt = (EditText)view.findViewById(R.id.remark);
          //  holder.quantityTxt.addTextChangedListener(holder.mWatcher);




            //view.setTag(holder);
       // }
       /* else {
            holder = (ViewHolder) view.getTag();
        }*/

        holder.tvName.setText(list.get(i).getItemName());
        holder.remarkTxt.setText(list.get(i).getRemark());

        holder.quantityTxt.setText(list.get(i).getFillQuanty());

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





        final ViewHolder finalHolder = holder;
        holder.tvName.setTag(i);
        holder.tvName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {


                    //testing mode

                    selectedStrings.add(finalHolder.tvName.getText().toString());
                    selectedId.add(list.get(i).getItemID());

                    sendList.add(new SendListModel(list.get(i).getItemID(), list.get(i).getItemName(), finalHolder.quantityTxt.getText().toString(),
                                finalHolder.remarkTxt.getText().toString()));

                    Log.e("adapter list size", sendList.size() + "");



                        flag = true;
                        postion = i;
                        list.get(i).setToKill(true);



                }else
                    {

                        //visibile Gone Widget
                        selectedStrings.remove(finalHolder.tvName.getText().toString());
                        selectedId.remove(list.get(i).getItemID());
                        selectedremark.remove(finalHolder.remarkTxt.getText().toString());
                        list.get(i).setToKill(false);


                        flag = false;
                    }
            }
        });

        //set the quantity
        selectedremark.add(finalHolder.remarkTxt.getText().toString());

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
        return secondQuant;
    }
    public ArrayList<String> getSelectedRemark(){
        return selectedremark;
    }

}


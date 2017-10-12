package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.BookMeaPrevisionModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 21-09-2017.
 */

public class BookMeaPrevisonAdapter extends BaseAdapter {

    public ArrayList<BookMeaPrevisionModel> list = new ArrayList<>();
    public Context context;
    LayoutInflater inflater;

    public BookMeaPrevisonAdapter(ArrayList<BookMeaPrevisionModel> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

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

        holder.tvName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {
                    TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                    finalHolder.mainLay.setVisibility(View.VISIBLE);
                }else
                    {
                        TransitionManager.beginDelayedTransition(finalHolder.primory_layout);
                        finalHolder.mainLay.setVisibility(View.GONE);
                    }
            }
        });

        //check check box is check or not
        if (list.get(i).getCheckValue().equalsIgnoreCase("true"))
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

        return view;
    }

    private class ViewHolder {

        CheckBox tvName;
        EditText quantityTxt, remarkTxt;
        LinearLayout mainLay,primory_layout;
    }

}

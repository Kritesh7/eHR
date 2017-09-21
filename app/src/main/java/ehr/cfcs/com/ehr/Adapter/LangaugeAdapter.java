package ehr.cfcs.com.ehr.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Model.LanguageModel;
import ehr.cfcs.com.ehr.R;

/**
 * Created by Admin on 21-09-2017.
 */

public class LangaugeAdapter extends RecyclerView.Adapter<LangaugeAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<LanguageModel> list = new ArrayList<>();

    public LangaugeAdapter(Context context, ArrayList<LanguageModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.langayge_item_layout, parent, false);
        return new LangaugeAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LanguageModel model = list.get(position);

        holder.languageTxt.setText(model.getLangaugae());
        holder.readTxt.setText(model.getRead());
        holder.writeTxt.setText(model.getWrite());
        holder.speakTxt.setText(model.getSpeak());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView languageTxt,readTxt,writeTxt,speakTxt;

        public CardView mainLay;

        public ViewHolder(View itemView) {
            super(itemView);

            languageTxt = (TextView)itemView.findViewById(R.id.langauge);
            readTxt = (TextView)itemView.findViewById(R.id.read);
            writeTxt = (TextView)itemView.findViewById(R.id.write);
            speakTxt = (TextView)itemView.findViewById(R.id.speak);

        }
    }
}

package ehr.cfcs.com.ehr.Main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Adapter.BookMeaPrevisonAdapter;
import ehr.cfcs.com.ehr.Model.BookMeaPrevisionModel;
import ehr.cfcs.com.ehr.R;

public class AddDocumentActivity extends AppCompatActivity {

    public TextView titleTxt;
    public BookMeaPrevisonAdapter adapter;
    public ArrayList<BookMeaPrevisionModel> list = new ArrayList<>();
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.document_tollbar);
        setSupportActionBar(toolbar);
        titleTxt = (TextView)toolbar.findViewById(R.id.titletxt);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
                onBackPressed();

            }
        });

        titleTxt.setText("Add New Document");


        listView = (ListView)findViewById(R.id.listview);

        //listView.setItemsCanFocus(true);

        adapter = new BookMeaPrevisonAdapter(list,AddDocumentActivity.this);
        listView.setAdapter(adapter);

       // prepareInsDetails();



    }

   /* private void prepareInsDetails() {

        BookMeaPrevisionModel model = new BookMeaPrevisionModel("10th Marksheet");
        list.add(model);
        model = new BookMeaPrevisionModel("12th Marksheet");
        list.add(model);
        model = new BookMeaPrevisionModel("Graduation Certificate");
        list.add(model);
        model = new BookMeaPrevisionModel("Post Graduation Certificate");
        list.add(model);



        adapter.notifyDataSetChanged();

    }*/

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }


}

package ehr.cfcs.com.ehr.Main;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.R;

public class AddPreviousExpreinceActivity extends AppCompatActivity {

    public TextView titleTxt;
    public Spinner yearSpinner, monthSpinner;
    public ArrayList<String> yearList = new ArrayList<>();
    public ArrayList<String> monthList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_previous_expreince);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.previoustollbar);
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

        titleTxt.setText("Add Previous Experience");

        yearSpinner = (Spinner)findViewById(R.id.yearspinner);
        monthSpinner = (Spinner)findViewById(R.id.monthspinner);


        //Year List Spinner
        if (yearList.size()>0)
        {
            yearList.clear();
        }
        yearList.add("Please Select Year");
        yearList.add("2010");
        yearList.add("2011");
        yearList.add("2012");
        yearList.add("2013");

        //change spinner arrow color
        yearSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(AddPreviousExpreinceActivity.this, R.layout.customizespinner,
                yearList);
        yearAdapter.setDropDownViewResource(R.layout.customizespinner);
        yearSpinner.setAdapter(yearAdapter);

        //month list spinner
        if (monthList.size()>0)
        {
            monthList.clear();
        }
        monthList.add("Please Select Month");
        monthList.add("Jan");
        monthList.add("Fab");
        monthList.add("Mar");
        monthList.add("APR");

        //change spinner arrow color
        monthSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> MonthAdater = new ArrayAdapter<String>(AddPreviousExpreinceActivity.this, R.layout.customizespinner,
                monthList);
        MonthAdater.setDropDownViewResource(R.layout.customizespinner);
        monthSpinner.setAdapter(MonthAdater);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }

}

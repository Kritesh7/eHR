package ehr.cfcs.com.ehr.Main;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import ehr.cfcs.com.ehr.R;

public class NewAddLeaveMangementActivity extends AppCompatActivity {

    public Spinner leaveTypeSpinner;
    public ArrayList<String> leaveTypeList = new ArrayList<>();
    public ImageView startCal, endCal;
    public int month,year,day;
    public EditText startTxt,endTxt;
    public TextView titleTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_leave_mangement);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.newaddtollbar);
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

        titleTxt.setText("Apply Leave");

        leaveTypeSpinner = (Spinner)findViewById(R.id.leavetypespinner);

        startCal = (ImageView)findViewById(R.id.cal);
        endCal = (ImageView)findViewById(R.id.end_cal);
        startTxt = (EditText)findViewById(R.id.startdate);
        endTxt = (EditText) findViewById(R.id.enddate);



        startCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(new View(NewAddLeaveMangementActivity.this).getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                Calendar cal = Calendar.getInstance();

                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAddLeaveMangementActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                               /* mm = month;
                                yy = year;
                                dd = dayOfMonth;*/

                                startTxt.setText(dayOfMonth + "-" + (month+1) + "-" + year);

                            }
                        },year , month, day);
                datePickerDialog.show();
            }
        });

        endCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(new View(NewAddLeaveMangementActivity.this).getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                Calendar cal = Calendar.getInstance();

                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAddLeaveMangementActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                               /* mm = month;
                                yy = year;
                                dd = dayOfMonth;*/

                                endTxt.setText(dayOfMonth + "-" + (month+1) + "-" + year);

                            }
                        },year , month, day);
                datePickerDialog.show();
            }
        });



        //Sppiner Work
        if (leaveTypeList.size()>0)
        {
            leaveTypeList.clear();
        }



        leaveTypeList.add("Please Select Leave Type");
        leaveTypeList.add("SL");
        leaveTypeList.add("CL");
        leaveTypeList.add("PL");


        //change spinner arrow color

        leaveTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> assignToAdapter = new ArrayAdapter<String>(NewAddLeaveMangementActivity.this, R.layout.customizespinner,
                leaveTypeList);
        assignToAdapter.setDropDownViewResource(R.layout.customizespinner);
        leaveTypeSpinner.setAdapter(assignToAdapter);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }

}

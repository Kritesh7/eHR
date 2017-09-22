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

import java.util.ArrayList;

import ehr.cfcs.com.ehr.R;

public class AddQualificationActivity extends AppCompatActivity {

    public TextView titleTxt;
    public Spinner qualificationSpinner, courseTypeSpinner,deciplineSpinner;
    public ArrayList<String> qualificationList = new ArrayList<>();
    public ArrayList<String> courseTypeList = new ArrayList<>();
    public ArrayList<String> deciplineList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_qualification);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.addqualificationtollbar);
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

        titleTxt.setText("Add Qualification Detail");

        qualificationSpinner = (Spinner)findViewById(R.id.qualificationspinner);
        courseTypeSpinner = (Spinner)findViewById(R.id.coursetypespinner);
        deciplineSpinner = (Spinner)findViewById(R.id.deciplinespinner);



        //Qualification List Spinner
        if (qualificationList.size()>0)
        {
            qualificationList.clear();
        }
        qualificationList.add("Please Select Qualification");
        qualificationList.add("10th");
        qualificationList.add("12th");
        qualificationList.add("Graduation");

        //change spinner arrow color
        qualificationSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> qualificationAdapter = new ArrayAdapter<String>(AddQualificationActivity.this, R.layout.customizespinner,
                qualificationList);
        qualificationAdapter.setDropDownViewResource(R.layout.customizespinner);
        qualificationSpinner.setAdapter(qualificationAdapter);

        //course Type Spinner
        if (courseTypeList.size()>0)
        {
            courseTypeList.clear();
        }
        courseTypeList.add("Please Select Course Type");
        courseTypeList.add("Full Time");
        courseTypeList.add("Part time");


        //change spinner arrow color
        courseTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> CourseTypeAdapter = new ArrayAdapter<String>(AddQualificationActivity.this, R.layout.customizespinner,
                courseTypeList);
        CourseTypeAdapter.setDropDownViewResource(R.layout.customizespinner);
        courseTypeSpinner.setAdapter(CourseTypeAdapter);

        //Decipline Spinner
        if (deciplineList.size()>0)
        {
            deciplineList.clear();
        }
        deciplineList.add("Please Select Discipline");
        deciplineList.add("Computer Science");
        deciplineList.add("Other");


        //change spinner arrow color
        deciplineSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> deciplineAdapter = new ArrayAdapter<String>(AddQualificationActivity.this, R.layout.customizespinner,
                deciplineList);
        deciplineAdapter.setDropDownViewResource(R.layout.customizespinner);
        deciplineSpinner.setAdapter(deciplineAdapter);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }

}

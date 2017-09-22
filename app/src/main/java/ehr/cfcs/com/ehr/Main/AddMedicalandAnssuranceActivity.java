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

public class AddMedicalandAnssuranceActivity extends AppCompatActivity {

    public TextView titleTxt;
    public Spinner policyCompanySpinner, policyTypeSpinner;
    public ArrayList<String> policyCompanyNmaeList = new ArrayList<>();
    public ArrayList<String> policyTypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaland_anssurance);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.medicaltollbar);
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

        titleTxt.setText("Add New Medical&Assurance");

        policyCompanySpinner = (Spinner)findViewById(R.id.policycompanynamespinner);
        policyTypeSpinner = (Spinner)findViewById(R.id.policytypespinner);

        //Policy Type List Spinner
        if (policyTypeList.size()>0)
        {
            policyTypeList.clear();
        }
        policyTypeList.add("Please Select Policy Type");
        policyTypeList.add("3 Month");
        policyTypeList.add("6 Month");
        policyTypeList.add("1 Year");

        //change spinner arrow color
        policyTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> policyTypeAdapter = new ArrayAdapter<String>(AddMedicalandAnssuranceActivity.this, R.layout.customizespinner,
                policyTypeList);
        policyTypeAdapter.setDropDownViewResource(R.layout.customizespinner);
        policyTypeSpinner.setAdapter(policyTypeAdapter);

        //policy Company Spinner
        if (policyCompanyNmaeList.size()>0)
        {
            policyCompanyNmaeList.clear();
        }
        policyCompanyNmaeList.add("Please Select Policy Company");
        policyCompanyNmaeList.add("Lic");
        policyCompanyNmaeList.add("Other");


        //change spinner arrow color
        policyCompanySpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> policyCompanyAdapter = new ArrayAdapter<String>(AddMedicalandAnssuranceActivity.this, R.layout.customizespinner,
                policyCompanyNmaeList);
        policyCompanyAdapter.setDropDownViewResource(R.layout.customizespinner);
        policyCompanySpinner.setAdapter(policyCompanyAdapter);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }


}

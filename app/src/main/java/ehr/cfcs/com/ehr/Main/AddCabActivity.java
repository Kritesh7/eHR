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

public class AddCabActivity extends AppCompatActivity {

    public TextView titleTxt;
    public Spinner cityOfBookingSpinner;
    public ArrayList<String> listOfBooking = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cab);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.addcabtollbar);
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

        titleTxt.setText("Add New Cab Details");

        cityOfBookingSpinner = (Spinner)findViewById(R.id.cityofbokkinglist);

        //City List Spinner

        if (listOfBooking.size()>0)
        {
            listOfBooking.clear();
        }
        listOfBooking.add("Please Select City");
        listOfBooking.add("Noida");
        listOfBooking.add("Delhi");
        listOfBooking.add("Agra");



        //change spinner arrow color

        cityOfBookingSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(AddCabActivity.this, R.layout.customizespinner,
                listOfBooking);
        cityAdapter.setDropDownViewResource(R.layout.customizespinner);
        cityOfBookingSpinner.setAdapter(cityAdapter);


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }

}

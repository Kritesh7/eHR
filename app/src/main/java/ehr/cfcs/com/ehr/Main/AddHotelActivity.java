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

public class AddHotelActivity extends AppCompatActivity {

    public TextView titleTxt;
    public Spinner hotelTypeSpinner, cityofBookingSpinner,hotelSpinner;
    public ArrayList<String> cityList = new ArrayList<>();
    public ArrayList<String> hotelTypeList = new ArrayList<>();
    public ArrayList<String> hotelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.tolbarofhotel);
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

        titleTxt.setText("Add New Hotel Booking");

        hotelTypeSpinner = (Spinner)findViewById(R.id.hoteltypespinner);
        cityofBookingSpinner = (Spinner)findViewById(R.id.cityofbookingspinner);
        hotelSpinner = (Spinner)findViewById(R.id.hotelspinner);

        //City List Spinner
        if (cityList.size()>0)
        {
            cityList.clear();
        }
        cityList.add("Please Select City");
        cityList.add("Noida");
        cityList.add("Delhi");
        cityList.add("Agra");

        //change spinner arrow color
        cityofBookingSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(AddHotelActivity.this, R.layout.customizespinner,
                cityList);
        cityAdapter.setDropDownViewResource(R.layout.customizespinner);
        cityofBookingSpinner.setAdapter(cityAdapter);

        //hotel Type Spinner
        if (hotelTypeList.size()>0)
        {
            hotelTypeList.clear();
        }
        hotelTypeList.add("Please Select Hotel Type");
        hotelTypeList.add("5 Star");
        hotelTypeList.add("4 Star");
        hotelTypeList.add("2 Star");

        //change spinner arrow color
        hotelTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> hotelTypeAdapter = new ArrayAdapter<String>(AddHotelActivity.this, R.layout.customizespinner,
                hotelTypeList);
        hotelTypeAdapter.setDropDownViewResource(R.layout.customizespinner);
        hotelTypeSpinner.setAdapter(hotelTypeAdapter);

        //hotel Spinner
        if (hotelList.size()>0)
        {
            hotelList.clear();
        }
        hotelList.add("Please Select Hotel");
        hotelList.add("Mx Hotel");
        hotelList.add("Vaibhav Hotel");
        hotelList.add("Abhinav Hotel");

        //change spinner arrow color
        hotelSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(AddHotelActivity.this, R.layout.customizespinner,
                hotelList);
        hotelAdapter.setDropDownViewResource(R.layout.customizespinner);
        hotelSpinner.setAdapter(hotelAdapter);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }

}

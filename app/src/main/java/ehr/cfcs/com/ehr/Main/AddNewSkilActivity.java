package ehr.cfcs.com.ehr.Main;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.R;

public class AddNewSkilActivity extends AppCompatActivity {

    public TextView titleTxt;
    public Spinner skillSpinner, proficenacySpinner, sourceSpinner;
    public ArrayList<String> skillList = new ArrayList<>();
    public ArrayList<String> profyList = new ArrayList<>();
    public ArrayList<String> sourceList = new ArrayList<>();
    public RadioGroup radioGroup;
    public RadioButton lastUsedBtn, currentUsedBtn;
    public LinearLayout mainLay, lastUsedLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_skil);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.newskilltollbar);
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

        titleTxt.setText("Add New Skill");

        skillSpinner = (Spinner)findViewById(R.id.skillspinner);
        proficenacySpinner = (Spinner)findViewById(R.id.proficenacySpinner);
        sourceSpinner = (Spinner)findViewById(R.id.sourceSpinner);
        radioGroup = (RadioGroup) findViewById(R.id.skillsusedradiogroup);
        lastUsedBtn = (RadioButton)findViewById(R.id.lastused);
        currentUsedBtn = (RadioButton)findViewById(R.id.currentused);
        lastUsedLay = (LinearLayout)findViewById(R.id.lastusedlay);
        mainLay = (LinearLayout)findViewById(R.id.mainlay);



        //Skill Spinner List Spinner
        if (skillList.size()>0)
        {
            skillList.clear();
        }
        skillList.add("Please Select Skill");
        skillList.add(".Net");
        skillList.add("Java");
        skillList.add("Web Technology");
        skillList.add("Other");

        //change spinner arrow color
        skillSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> skillAdapter = new ArrayAdapter<String>(AddNewSkilActivity.this, R.layout.customizespinner,
                skillList);
        skillAdapter.setDropDownViewResource(R.layout.customizespinner);
        skillSpinner.setAdapter(skillAdapter);

        //Proficeancy Spinner
        if (profyList.size()>0)
        {
            profyList.clear();
        }
        profyList.add("Please Select Proficiency");
        profyList.add("Beginner");
        profyList.add("Medium");
        profyList.add("Expert");
        profyList.add("Other");

        //change spinner arrow color
        proficenacySpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> profyAdapter = new ArrayAdapter<String>(AddNewSkilActivity.this, R.layout.customizespinner,
                profyList);
        profyAdapter.setDropDownViewResource(R.layout.customizespinner);
        proficenacySpinner.setAdapter(profyAdapter);

        //Source Spinner
        if (sourceList.size()>0)
        {
            sourceList.clear();
        }
        sourceList.add("Please Select Source");
        sourceList.add("By Internet");
        sourceList.add("Training");
        sourceList.add("Institute");
        sourceList.add("Other");

        //change spinner arrow color
        sourceSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<String>(AddNewSkilActivity.this, R.layout.customizespinner,
                sourceList);
        sourceAdapter.setDropDownViewResource(R.layout.customizespinner);
        sourceSpinner.setAdapter(sourceAdapter);

        //Radio Group Work
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if (i == R.id.lastused)
                {
                    TransitionManager.beginDelayedTransition(mainLay);
                    lastUsedLay.setVisibility(View.VISIBLE);

                }
                else if (i == R.id.currentused)
                {
                    TransitionManager.beginDelayedTransition(mainLay);
                    lastUsedLay.setVisibility(View.GONE);

                }
            }
        });

    }

}

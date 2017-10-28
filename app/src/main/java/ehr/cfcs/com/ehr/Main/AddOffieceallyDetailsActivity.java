package ehr.cfcs.com.ehr.Main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import ehr.cfcs.com.ehr.R;

public class AddOffieceallyDetailsActivity extends AppCompatActivity {

    public TextView titleTxt;
    public Spinner documentTypeSpinner;
    public ArrayList<String> documentTypeList = new ArrayList<>();
    public Button uploadBtn;
    public StringTokenizer tokens;
    public String uploadedFileName = "", first = "",file_1 = "";
    public File file1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offieceally_details);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.offecalytollbar);
        setSupportActionBar(toolbar);

        titleTxt = (TextView) toolbar.findViewById(R.id.titletxt);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null) {
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

        titleTxt.setText("Add Officially Detail");

        documentTypeSpinner = (Spinner) findViewById(R.id.documenttypespinner);
        uploadBtn = (Button) findViewById(R.id.uploaddocsbtn);

        //DOcument Type List Spinner
        if (documentTypeList.size() > 0) {
            documentTypeList.clear();
        }
        documentTypeList.add("Please Select Document Type");
        documentTypeList.add("Certificate");
        documentTypeList.add("Other");


        //change spinner arrow color
        documentTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> documentAdapter = new ArrayAdapter<String>(AddOffieceallyDetailsActivity.this, R.layout.customizespinner,
                documentTypeList);
        documentAdapter.setDropDownViewResource(R.layout.customizespinner);
        documentTypeSpinner.setAdapter(documentAdapter);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showFileChooser();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AddOffieceallyDetailsActivity.this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Uri selectedFileURI = data.getData();

                if (data.getData() != null)
                {
                    Log.e("Checking Null",selectedFileURI.getPath().toString());

                }else
                    {
                        Log.e("Checking Null","Null");
                    }

                File file = new File(selectedFileURI.getPath().toString());
                Log.d("", "File : " + file.getName());
                uploadedFileName = file.getName().toString();
                tokens = new StringTokenizer(uploadedFileName, ":");

                Log.e("File Name",uploadedFileName);
                first = tokens.nextToken();
                //file_1 = tokens.nextToken().trim();
                // txt_file_name_1.setText(file_1);
            }
        }
    }
}

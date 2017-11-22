package ehr.cfcs.com.ehr.Main;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ehr.cfcs.com.ehr.Fragment.AttendanceFragment;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.CameraView;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.GPSTracker;
import ehr.cfcs.com.ehr.Source.LocationAddress;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class AttendanceModule extends AppCompatActivity implements OnMapReadyCallback {


    public TextView titleTxt;
    public SupportMapFragment mSupportMapFragment;
    public MarkerOptions options;
    public ArrayList<LatLng> MarkerPoints;
    public GPSTracker gpsTracker;
    public Context mContext;
    public ImageView locationImg;
    public EditText locationTxt;
    String locationAddress = "";
    public Button subBtn;
    private GoogleMap mMap;
    public ImageView profileImg;
    public Button cancelBtn;
    public ImageView profileSelectImg;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    public TextView timeTxt, dateTxt, addTxt;
    public ConnectionDetector conn;
    public String imageBase64 = "";
    public String addUrl = SettingConstant.BaseUrl + "AppEmployeeAttendanceLogInsUpdt";
    public String authCode = "",userId = "";
    public EditText remarkTxt;
    public  ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_module);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.attendancetollbar);
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

        gpsTracker = new GPSTracker(AttendanceModule.this, AttendanceModule.this);
        conn = new ConnectionDetector(AttendanceModule.this);
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(AttendanceModule.this)));
        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(AttendanceModule.this)));


        titleTxt.setText("Mark Attendance");
        profileImg = (ImageView)findViewById(R.id.pro_image);
        subBtn = (Button)findViewById(R.id.submitbtn);
        timeTxt = (TextView) findViewById(R.id.time);
        dateTxt = (TextView) findViewById(R.id.date);
        addTxt = (TextView) findViewById(R.id.addreestxt);
        remarkTxt = (EditText) findViewById(R.id.remarktxt);
        cancelBtn = (Button)findViewById(R.id.cancelbtn);


        //current Time incresing
        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();


                timeTxt.setText(String.format("%02d:%02d:%02d", c.get(Calendar.HOUR), c.get(Calendar.MINUTE),c.get(Calendar.SECOND)));
            }
            public void onFinish() {

            }
        };
        newtimer.start();

        //set current date
        dateTxt.setText(getCurrentTime());

        if (gpsTracker.canGetLocation())
        {
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), AttendanceModule.this,
                    new GeocoderHandler());



        }else
            {
                gpsTracker.showSettingsAlert();
            }


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        /*locationImg = (ImageView)findViewById(R.id.locationimg);
        locationTxt = (EditText)findViewById(R.id.locationtxt);
        subBtn = (Button)findViewById(R.id.submitbtn);

        profileSelectImg = (ImageView)findViewById(R.id.pro_image);


        gpsTracker = new GPSTracker(AttendanceModule.this, AttendanceModule.this);

        mSupportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapwhere);
        mSupportMapFragment.getMapAsync(this);


        MarkerPoints = new ArrayList<>();
        options = new MarkerOptions();

        locationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationTxt.setText(locationAddress);
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            0);
                }else {
                    selectImage();
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
*/


        try{

           if (checkCameraRear())
           {
               
               if (checkCameraFront(AttendanceModule.this)) {
                   mCamera = Camera.open(1);
               }else
                   {
                       mCamera = Camera.open();
                   }
           }else
               {
                   Toast.makeText(this, "Camera is Not support your device", Toast.LENGTH_SHORT).show();
               }
           
             //you can use open(int) to use different cameras

        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if(mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            FrameLayout camera_view = (FrameLayout)findViewById(R.id.camera_view);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }

        //btn to close the application
        ImageButton imgClose = (ImageButton)findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });


        //click on button and take pic


        final Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                Bitmap bm = null;

                if (data != null) {
                    int screenWidth = getResources().getDisplayMetrics().widthPixels;
                    int screenHeight = getResources().getDisplayMetrics().heightPixels;
                    bm = BitmapFactory.decodeByteArray(data, 0, (data != null) ? data.length : 0);

                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        // Notice that width and height are reversed
                        Bitmap scaled = Bitmap.createScaledBitmap(bm, screenHeight, screenWidth, true);
                        int w = scaled.getWidth();
                        int h = scaled.getHeight();
                        // Setting post rotate to 90
                        Matrix mtx = new Matrix();

                        int CameraEyeValue = setPhotoOrientation(AttendanceModule.this, checkCameraFront(AttendanceModule.this)==true ? 1:0); // CameraID = 1 : front 0:back
                        if(checkCameraFront(AttendanceModule.this)) { // As Front camera is Mirrored so Fliping the Orientation
                            if (CameraEyeValue == 270) {
                                mtx.postRotate(90);
                            } else if (CameraEyeValue == 90) {
                                mtx.postRotate(270);
                            }
                        }else{
                            mtx.postRotate(CameraEyeValue); // CameraEyeValue is default to Display Rotation
                        }

                        bm = Bitmap.createBitmap(scaled, 0, 0, w, h, mtx, true);
                    }else{// LANDSCAPE MODE
                        //No need to reverse width and height
                        Bitmap scaled = Bitmap.createScaledBitmap(bm, screenWidth, screenHeight, true);
                        bm=scaled;
                    }
                }




                imageBase64 = getEncoded64ImageStringFromBitmap(bm);

                // profileImg.setImageBitmap(bm);

                    camera.startPreview();
            }
        };

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCamera.takePicture(null,null,jpegCallback);

                pDialog = new ProgressDialog(AttendanceModule.this,R.style.AppCompatAlertDialogStyle);
                pDialog.setTitle("Mark Attendance");
                pDialog.setMessage("Please Wait...");
                pDialog.show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        if (imageBase64.equalsIgnoreCase(""))
                        {
                            Toast.makeText(AttendanceModule.this, "Plesae clcik the pic properely", Toast.LENGTH_SHORT).show();
                        }else if (addTxt.getText().toString().equalsIgnoreCase(""))
                        {
                            addTxt.setError("Please get address");
                        }else if (gpsTracker.getLongitude() == 0 || gpsTracker.getLongitude() == 0)
                        {
                            Toast.makeText(AttendanceModule.this, "Please get location", Toast.LENGTH_SHORT).show();
                        }else {

                            if (conn.getConnectivityStatus() > 0) {

                                attendaceDetails(userId, gpsTracker.getLongitude() + "", gpsTracker.getLatitude() + "", addTxt.getText().toString(),
                                        remarkTxt.getText().toString(), imageBase64, authCode, ".jpeg");
                            } else {

                                conn.showNoInternetAlret();
                            }

                        }
                    }}, 3000);




            }
        });

    }

    //add Attendnace Request
    public void attendaceDetails(final String AdminID  ,final String Lang, final String Lat, final String LocationAddress,
                              final String Remark, final String FileJson, final String AuthCode, final String FileExtension)  {


        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, addUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                    if (jsonObject.has("status"))
                    {
                        String status = jsonObject.getString("status");

                        if (status.equalsIgnoreCase("success"))
                        {
                            String MsgNotification = jsonObject.getString("MsgNotification");
                            Toast.makeText(AttendanceModule.this, MsgNotification, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }


                    pDialog.dismiss();

                } catch (JSONException e) {
                    Log.e("checking json excption" , e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Login", "Error: " + error.getMessage());
                // Log.e("checking now ",error.getMessage());

                Toast.makeText(AttendanceModule.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("AdminID",AdminID);
                params.put("AuthCode",AuthCode);
                params.put("Lang",Lang);
                params.put("Lat",Lat);
                params.put("LocationAddress",LocationAddress);
                params.put("Remark",Remark);
                params.put("FileExtension",FileExtension);
                params.put("FileJson",FileJson);


                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }

    //get current time
    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        Calendar cal = Calendar.getInstance();
        String sdf = new SimpleDateFormat("LLL", Locale.getDefault()).format(cal.getTime());
        //sdf = new DateFormatSymbols().getShortMonths()[month];

        return dateFormat.format(cal.getTime());
    }


    //check if you have a front Camera
    public static boolean checkCameraFront(Context context) {
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            return true;
        } else {
            return false;
        }
    }

    //Check if you have Camera in your device 
    public static boolean checkCameraRear() {
        int numCamera = Camera.getNumberOfCameras();
        if(numCamera > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int setPhotoOrientation(Activity activity, int cameraId) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        // do something for phones running an SDK before lollipop
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }

    /*public static boolean checkCameraRear() {
        int numCamera = Camera.getNumberOfCameras();
        if(numCamera > 0) {
            return true;
        } else {
            return false;
        }
    }*/
    /*public static boolean checkCameraRear() {
        int numCamera = Camera.getNumberOfCameras();
        if(numCamera > 0) {
            return true;
        } else {
            return false;
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS)
        {
            selectImage();
        }

    }

    int getFrontCameraId() {
        Camera.CameraInfo ci = new Camera.CameraInfo();
        for (int i = 0 ; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, ci);
            if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) return i;
        }
        return -1; // No front-facing camera found
    }

    //uploadImage work
    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceModule.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                //saveToInternalStorage(photo);
                profileSelectImg.setImageBitmap(photo);
            }
            else if (requestCode == 2) {

                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    profileSelectImg.setImageBitmap(bitmap);
                    //   uploadImage(imageBase64,userId);

                    //    getCustomerDetail(userId);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        double innerlat = gpsTracker.getLatitude();
        double inerlog = gpsTracker.getLongitude();

        LatLng sydney = new LatLng(innerlat, inerlog);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Demo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));




    }
    // get address with the help of lat log
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {


            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");

                    break;
                default:
                    locationAddress = null;
            }

            Log.e("checking address", locationAddress);
            //set Current Address
            addTxt.setText(locationAddress);

        }
    }

    //convert bitmap to base64
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);

    }

}

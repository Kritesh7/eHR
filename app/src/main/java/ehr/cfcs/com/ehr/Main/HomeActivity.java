package ehr.cfcs.com.ehr.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import ehr.cfcs.com.ehr.Fragment.AssestDetailsFragment;
import ehr.cfcs.com.ehr.Fragment.AttendaceListFragment;
import ehr.cfcs.com.ehr.Fragment.AttendanceFragment;
import ehr.cfcs.com.ehr.Fragment.ContactPhoneFragment;
import ehr.cfcs.com.ehr.Fragment.ContactsDetailsFragment;
import ehr.cfcs.com.ehr.Fragment.DashBoardFragment;
import ehr.cfcs.com.ehr.Fragment.DependnetsFragment;
import ehr.cfcs.com.ehr.Fragment.DocumentListFragment;
import ehr.cfcs.com.ehr.Fragment.EducationDetailsFragment;
import ehr.cfcs.com.ehr.Fragment.EmergencyContactsFragment;
import ehr.cfcs.com.ehr.Fragment.ChnagePasswordFragment;
import ehr.cfcs.com.ehr.Fragment.HolidayListFragment;
import ehr.cfcs.com.ehr.Fragment.HotelBookingListFragment;
import ehr.cfcs.com.ehr.Fragment.LanguagesFragment;
import ehr.cfcs.com.ehr.Fragment.LeaveManagementFragment;
import ehr.cfcs.com.ehr.Fragment.LeaveSummarryFragment;
import ehr.cfcs.com.ehr.Fragment.MedicalAndEnsuranceFragment;
import ehr.cfcs.com.ehr.Fragment.MedicalDetailsFragment;
import ehr.cfcs.com.ehr.Fragment.OfficeallyDetailsFragment;
import ehr.cfcs.com.ehr.Fragment.PersonalDetailsFragment;
import ehr.cfcs.com.ehr.Fragment.PreviousExprienceFragment;
import ehr.cfcs.com.ehr.Fragment.ResumeFragment;
import ehr.cfcs.com.ehr.Fragment.ShortLeaveHistoryFragment;
import ehr.cfcs.com.ehr.Fragment.SkillsFragment;
import ehr.cfcs.com.ehr.Fragment.StationaryRequestFragment;
import ehr.cfcs.com.ehr.Fragment.TaxiListFragment;
import ehr.cfcs.com.ehr.Fragment.TrainingFragment;
import ehr.cfcs.com.ehr.Fragment.WeekOfListFragment;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

public class HomeActivity extends AppCompatActivity implements DashBoardFragment.OnFragmentInteractionListener {

    private Handler mHandeler;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public View navHeader;
    private static final String TAG_Dashboard = "Dashboard";
    private static final String TAG_Attendnace = "Attendance";
    private static final String TAG_Leave_Management = "LeaveMangement";
    private static final String TAG_Traning = "Traning";
    private static final String TAG_Asset_Details = "AssestDeatils";
    private static final String TAG_Employ_UpdateProfile = "UpdateProfile";
    private static final String TAG_Employ_PersonalDetails= "PersonalDetails";
    private static final String TAG_Employ_MedicalDetails = "MedicalDetails";
    private static final String TAG_Employ_OfficeallyDetails = "OfficeallyDetails";
    private static final String TAG_Employ_ContactsDetails = "ContactsDetails";
    private static final String TAG_Employ_Emergency_CntactDetails = "EmergencyContactsDetails";
    private static final String TAG_Employ_Dependents = "Dependents ";
    private static final String TAG_Employ_MedicalAndAnsurense = "MedicalAndAnsurense ";
    private static final String TAG_Employ_EducationDetails = "EducationDetails";
    private static final String TAG_Employ_PreviousExpreince = "PreviousExpreince";
    private static final String TAG_Employ_Languages = "Languages";
    private static final String TAG_Employ_Skills = "Skills";
    private static final String TAG_Employ_StationaryRequest = "StationaryRequest";
    private static final String TAG_Employ_DocumentList = "DocumentList";
    private static final String TAG_Employ_CabList = "CabList";
    private static final String TAG_Employ_HotelBooking = "HotelBooking";
    private static final String TAG_Employ_Logout = "Logout";
    private static final String TAG_Leave_Summrry = "Leave Summary";
    private static final String TAG_Short_Leave_History = "Short Leave History";
    private static final String TAG_WeekOf = "Week Of";
    private static final String TAG_Holiday_List = "Holiday List";
    private static final String TAG_Contact_Phone = "Contact Phone";
    public String userNameStr = "", photoStr = "", empIdStr = "", designationStr = "",companLogoStr = "";
    public static int navigationItemIndex = 0;
    public Toolbar toolbar;
    public static String CURRENT_TAG = TAG_Dashboard;
    private boolean shouldLoadHomeFragOnBackPress = true;
    public TextView titleTxt;
    public de.hdodenhof.circleimageview.CircleImageView proImg;
    public TextView nameTxt, designationTxt, empIdTxt;
    public ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        toolbar = (Toolbar) findViewById(R.id.hometollbar);
        setSupportActionBar(toolbar);
        titleTxt = (TextView)toolbar.findViewById(R.id.titletxt);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mHandeler = new Handler();

        drawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.home_navigation);


   //     headeLay = (RelativeLayout) navigationView.findViewById(R.id.view_container) ;


        navHeader = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        proImg = (de.hdodenhof.circleimageview.CircleImageView) navHeader.findViewById(R.id.pro_image);
        nameTxt = (TextView) navHeader.findViewById(R.id.name);
        empIdTxt = (TextView) navHeader.findViewById(R.id.empid);
        designationTxt = (TextView) navHeader.findViewById(R.id.designation);
        backImg = (ImageView) navHeader.findViewById(R.id.backimage);

        userNameStr = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserName(HomeActivity.this)));
        photoStr = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getEmpPhoto(HomeActivity.this)));
        empIdStr = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getEmpId(HomeActivity.this)));
        designationStr = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getDesignation(HomeActivity.this)));
        companLogoStr = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getCompanyLogo(HomeActivity.this)));

        nameTxt.setText(userNameStr);
        empIdTxt.setText(empIdStr);
        designationTxt.setText(designationStr);

        //set profile Image
        Picasso pic = Picasso.with(HomeActivity.this);
        pic.setIndicatorsEnabled(true);
        pic.with(HomeActivity.this).cancelRequest(proImg);
        pic.with(HomeActivity.this)
                .load(SettingConstant.DownloadUrl + photoStr)
                .placeholder(R.drawable.prf)
                .error(R.drawable.prf)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .tag(HomeActivity.this)
                .into(proImg);

        //set Back Image
        Picasso backImgLoad = Picasso.with(HomeActivity.this);
        backImgLoad.setIndicatorsEnabled(true);
        backImgLoad.with(HomeActivity.this).cancelRequest(backImg);
        backImgLoad.with(HomeActivity.this)
                .load(SettingConstant.DownloadUrl + companLogoStr)
                .placeholder(R.drawable.logomain)
                .error(R.drawable.logomain)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .tag(HomeActivity.this)
                .into(backImg);


        setUpNavigationView();
        if (savedInstanceState == null) {
            navigationItemIndex = 0;
            CURRENT_TAG = TAG_Dashboard;
            loadHomeFragment();
        }

        titleTxt.setText("Dashboard");

    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_dashboard:

                            navigationItemIndex = 0;
                            CURRENT_TAG = TAG_Dashboard;
                            titleTxt.setText("Dashboard");


                        break;

                    case R.id.nav_attendance:


                        navigationItemIndex = 1;
                        CURRENT_TAG = TAG_Attendnace;
                        titleTxt.setText("Attendance List");

                        break;

                    case R.id.nav_leavemanagment:

                        navigationItemIndex = 2;
                        CURRENT_TAG = TAG_Leave_Management;
                        titleTxt.setText("Leave History");

                        break;



                    case R.id.nav_Traning:

                        navigationItemIndex = 3;
                        CURRENT_TAG = TAG_Traning;
                        titleTxt.setText("Training");

                        break;

                    case R.id.nav_Asset_details:

                        navigationItemIndex = 4;
                        CURRENT_TAG = TAG_Asset_Details;
                        titleTxt.setText("Assets Details");

                        break;


                    case R.id.nav_change_password:

                        navigationItemIndex = 5;
                        CURRENT_TAG = TAG_Employ_UpdateProfile;
                        titleTxt.setText("Update Profile");


                        break;

                    case R.id.nav_personaldetails:

                        navigationItemIndex = 6;
                        CURRENT_TAG = TAG_Employ_PersonalDetails;
                        titleTxt.setText("Personal Details");

                        break;

                    case R.id.nav_medical_detials:

                        navigationItemIndex = 7;
                        CURRENT_TAG = TAG_Employ_MedicalDetails;
                        titleTxt.setText("Medical Details");

                        break;

                    case R.id.nav_officeally_details:

                        navigationItemIndex = 8;
                        CURRENT_TAG = TAG_Employ_OfficeallyDetails;
                        titleTxt.setText("Office Details");

                        break;

                    case R.id.nav_contact_details:

                        navigationItemIndex = 9;
                        CURRENT_TAG = TAG_Employ_ContactsDetails;
                        titleTxt.setText("Contact Address");

                        break;

                    case R.id.nav_emergency_contact_details:

                        navigationItemIndex = 10;
                        CURRENT_TAG = TAG_Employ_Emergency_CntactDetails;
                        titleTxt.setText("Emergency Contact Address");

                        break;

                    case R.id.nav_dependents:

                        navigationItemIndex = 11;
                        CURRENT_TAG = TAG_Employ_Dependents;
                        titleTxt.setText("Dependents");

                        break;

                    case R.id.nav_medical_and_anssurence:

                        navigationItemIndex = 12;
                        CURRENT_TAG = TAG_Employ_MedicalAndAnsurense;
                        titleTxt.setText("Medical and Insurance");

                        break;

                    case R.id.nav_education_details:

                        navigationItemIndex = 13;
                        CURRENT_TAG = TAG_Employ_EducationDetails;
                        titleTxt.setText("Education Details");

                        break;

                    case R.id.nav_previous_expreince:

                        navigationItemIndex = 14;
                        CURRENT_TAG = TAG_Employ_PreviousExpreince;
                        titleTxt.setText("Previous Experience");

                        break;

                    case R.id.nav_languages:

                        navigationItemIndex = 15;
                        CURRENT_TAG = TAG_Employ_Languages;
                        titleTxt.setText("Language");

                        break;

                    case R.id.nav_skills:

                        navigationItemIndex = 16;
                        CURRENT_TAG = TAG_Employ_Skills;
                        titleTxt.setText("Skills");

                        break;

                    case R.id.nav_leavesummary:

                        navigationItemIndex = 17;
                        CURRENT_TAG = TAG_Leave_Summrry;
                        titleTxt.setText("Leave Summary");

                        break;

                    case R.id.nav_stationary_request:

                        navigationItemIndex = 18;
                        CURRENT_TAG = TAG_Employ_StationaryRequest;
                        titleTxt.setText("Stationary Request");

                        break;

                    case R.id.nav_document_list:

                        navigationItemIndex = 19;
                        CURRENT_TAG = TAG_Employ_DocumentList;
                        titleTxt.setText("Document List");

                        break;

                    case R.id.nav_cab_list:

                        navigationItemIndex = 20;
                        CURRENT_TAG = TAG_Employ_CabList;
                        titleTxt.setText("Cab List");

                        break;

                    case R.id.nav_hotel_booking_list:

                        navigationItemIndex = 21;
                        CURRENT_TAG = TAG_Employ_HotelBooking;
                        titleTxt.setText("Hotel Booking List");

                        break;

                    case R.id.nav_short_leave_history:

                        navigationItemIndex = 23;
                        CURRENT_TAG = TAG_Short_Leave_History;
                        titleTxt.setText("Short Leave History");

                        break;

                    case R.id.nav_weekof:

                        navigationItemIndex = 24;
                        CURRENT_TAG = TAG_WeekOf;
                        titleTxt.setText("Week Off");

                        break;

                    case R.id.nav_holidayList:

                        navigationItemIndex = 25;
                        CURRENT_TAG = TAG_Holiday_List;
                        titleTxt.setText("Holiday List");

                        break;

                    case R.id.nav_contact_phone:

                        navigationItemIndex = 26;
                        CURRENT_TAG = TAG_Contact_Phone;
                        titleTxt.setText("Contact Phone");
                        break;



                    case R.id.nav_logout:

                        navigationItemIndex = 22;

                        Intent ik = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(ik);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_right_out);
                        finish();

                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setStatus(HomeActivity.this,
                                "")));
                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setAdminId(HomeActivity.this,
                                "")));
                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setAuthCode(HomeActivity.this,
                                "")));
                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setEmailId(HomeActivity.this,
                                "")));
                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setUserName(HomeActivity.this,
                                "")));
                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setEmpId(HomeActivity.this,
                                "")));

                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setEmpPhoto(HomeActivity.this,
                                "")));

                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setDesignation(HomeActivity.this,
                                "")));
                        UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setCompanyLogo(HomeActivity.this,
                                "")));




                        CURRENT_TAG = TAG_Employ_Logout;
                        titleTxt.setText("Log Out");

                        break;




                    default:
                        navigationItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        //   selectNavMenu();

        Log.e("check current tag",CURRENT_TAG+" null");
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {


            drawerLayout.closeDrawers();
            // show or hide the fab button
            //    toggleFab();
            return;
        }

        //Closing drawer on item click
        drawerLayout.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();

        getHomeFragment();
    }
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navigationItemIndex).setChecked(true);
    }
    private Fragment getHomeFragment() {

        Fragment newFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Log.e("checked postion",navigationItemIndex+" null");
        switch (navigationItemIndex) {
            case 0:
                // home
              /*  DashBoardFragment dashboard = new DashBoardFragment();
                return dashboard;*/

                newFragment = new DashBoardFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;



               /* DashBoardFragment dashboard = new DashBoardFragment();
                return dashboard;*/

            case 1:
                //drawer.closeDrawers();
                // movies fragment
              /*  PayoutsListFragment payoutsListFragment = new PayoutsListFragment();*/

                newFragment = new AttendaceListFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 2:

                newFragment = new LeaveManagementFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

               /* FollowUpFragment followUpFragment = new FollowUpFragment();
                return followUpFragment;*/

            case 3:
                //drawer.closeDrawers();
                // movies fragment
              /*  PayoutsListFragment payoutsListFragment = new PayoutsListFragment();*/

                newFragment = new TrainingFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;


           /* ChatFragment chatFragment = new ChatFragment();
                return chatFragment;*/

            case 4:

                newFragment = new AssestDetailsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 5:

                newFragment = new ChnagePasswordFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 6:

                newFragment = new PersonalDetailsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 7:

                newFragment = new MedicalDetailsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 8:

                newFragment = new OfficeallyDetailsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 9:

                newFragment = new ContactsDetailsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 10:

                newFragment = new EmergencyContactsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 11:

                newFragment = new DependnetsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 12:

                newFragment = new MedicalAndEnsuranceFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 13:

                newFragment = new EducationDetailsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 14:

                newFragment = new PreviousExprienceFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 15:

                newFragment = new LanguagesFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 16:

                newFragment = new SkillsFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 17:

                newFragment = new LeaveSummarryFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 18:

                newFragment = new StationaryRequestFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 19:

                newFragment = new DocumentListFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 20:

                newFragment = new TaxiListFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 21:

                newFragment = new HotelBookingListFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 23:

                newFragment = new ShortLeaveHistoryFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 24:

                newFragment = new WeekOfListFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 25:

                newFragment = new HolidayListFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;

            case 26:

                newFragment = new ContactPhoneFragment();
                transaction.replace(R.id.home_navigation_framelayout, newFragment);
                transaction.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
                transaction.addToBackStack(null);
                transaction.commit();
                return newFragment ;
            default:
                return new DashBoardFragment();
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navigationItemIndex != 0) {
                navigationItemIndex = 0;
                CURRENT_TAG = TAG_Dashboard;
                loadHomeFragment();
                titleTxt.setText("Dashboard");
                return;
            }
        }

        finish();
    }

    @Override
    public void onFragmentInteraction(int navigationCount, String Title) {

        navigationItemIndex = navigationCount;
        titleTxt.setText(Title);
    }
}

package ehr.cfcs.com.ehr.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ehr.cfcs.com.ehr.Main.AddOffieceallyDetailsActivity;
import ehr.cfcs.com.ehr.Main.HomeActivity;
import ehr.cfcs.com.ehr.Main.LoginActivity;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public String personalDetailsUrl = SettingConstant.BaseUrl + "AppEmployeePersonalData";
    public String personalDdlDetailsUrl = SettingConstant.BaseUrl + "AppddlEmployeePersonalData";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Button editBtn;
    public EditText nameTxt, fathernameTxt, dobTxt,designatiinTxt,childernTxt, emailTxt, joiningdateTxt, emplId, reporttoTxt,
                    prefrednameTxt, passportNameTxt, alternativeTxt, panNoTxt,passportnumberTxt,firstNameTxt,middleNameTxt;
    public Spinner materialStatusSpinner, natinalitySpinner, zoneSpinner,departmentSpinner,titleSpinner;
    public RadioGroup genderGroup;
    public RadioButton mailBtn, femailBtn;
    public ImageView dobImg, joingDateImg;
    public String userId = "", authcode = "";

    public ArrayList<String> materialList = new ArrayList<>();
    public ArrayList<String> nationalityList = new ArrayList<>();
    public ArrayList<String> zoneList = new ArrayList<>();
    public ArrayList<String> departmentList = new ArrayList<>();
    public ArrayList<String> titleList = new ArrayList<>();
    public ProgressDialog pDialog;

    public ArrayAdapter<String> materialAdapter, nationaolityAdapter,titleAdapter;

    private OnFragmentInteractionListener mListener;

    public PersonalDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalDetailsFragment newInstance(String param1, String param2) {
        PersonalDetailsFragment fragment = new PersonalDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personal_details, container, false);

        nameTxt = (EditText)rootView.findViewById(R.id.name);
        firstNameTxt = (EditText)rootView.findViewById(R.id.firstname);
        middleNameTxt = (EditText)rootView.findViewById(R.id.middlename);
        fathernameTxt = (EditText)rootView.findViewById(R.id.fathername);
        dobTxt = (EditText)rootView.findViewById(R.id.dob);
        dobImg = (ImageView)rootView.findViewById(R.id.dobimg);
        designatiinTxt = (EditText)rootView.findViewById(R.id.designation);
        childernTxt = (EditText)rootView.findViewById(R.id.childern);
        emailTxt = (EditText)rootView.findViewById(R.id.email);
        joiningdateTxt = (EditText)rootView.findViewById(R.id.joiningdate);
        joingDateImg = (ImageView)rootView.findViewById(R.id.joingimg);
        emplId = (EditText)rootView.findViewById(R.id.employeid);
        reporttoTxt = (EditText)rootView.findViewById(R.id.reportto);
        prefrednameTxt = (EditText)rootView.findViewById(R.id.prefredname);
        passportNameTxt = (EditText)rootView.findViewById(R.id.passportname);
        passportnumberTxt = (EditText)rootView.findViewById(R.id.passportnumber);
        alternativeTxt = (EditText)rootView.findViewById(R.id.alternativeemail);
        panNoTxt = (EditText)rootView.findViewById(R.id.panno);
        genderGroup = (RadioGroup)rootView.findViewById(R.id.genderradiogroup);
        mailBtn = (RadioButton)rootView.findViewById(R.id.mailradiobtn);
        femailBtn = (RadioButton)rootView.findViewById(R.id.femailradiobtn);
        materialStatusSpinner = (Spinner)rootView.findViewById(R.id.materialstatus);
        natinalitySpinner = (Spinner)rootView.findViewById(R.id.nationalityspinner);
        departmentSpinner = (Spinner)rootView.findViewById(R.id.departmentspinner);
        zoneSpinner = (Spinner)rootView.findViewById(R.id.zonespinner);
        titleSpinner = (Spinner)rootView.findViewById(R.id.titilespinner);
        editBtn = (Button)rootView.findViewById(R.id.editprofilebtn);

        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(getActivity())));
        authcode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(getActivity())));

        pDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);


        //material Spinner
       /* if (materialList.size()>0)
        {
            materialList.clear();
        }
        materialList.add("Please Select Material Status");
        materialList.add("Single");
        materialList.add("Married");*/


        //change spinner arrow color
        materialStatusSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        materialAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                materialList);
        materialAdapter.setDropDownViewResource(R.layout.customizespinner);
        materialStatusSpinner.setAdapter(materialAdapter);

        //Title Spinner
        titleSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        titleAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                titleList);
        titleAdapter.setDropDownViewResource(R.layout.customizespinner);
        titleSpinner.setAdapter(titleAdapter);


        //nationality Spiiner
      /*  if (nationalityList.size()>0)
        {
            nationalityList.clear();
        }
        nationalityList.add("Please Select Nationality");
        nationalityList.add("Indian");
        nationalityList.add("Othrt");*/


        //change spinner arrow color
        natinalitySpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        nationaolityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                nationalityList);
        nationaolityAdapter.setDropDownViewResource(R.layout.customizespinner);
        natinalitySpinner.setAdapter(nationaolityAdapter);


        //zone sPINNER
        if (zoneList.size()>0)
        {
            zoneList.clear();
        }
        zoneList.add("Please Select Zone");
        zoneList.add("IT-Noida");
        zoneList.add("IT-Delhi");


        //change spinner arrow color
        zoneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> zoneAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                zoneList);
        zoneAdapter.setDropDownViewResource(R.layout.customizespinner);
        zoneSpinner.setAdapter(zoneAdapter);

        //Department Spinner
        if (departmentList.size()>0)
        {
            departmentList.clear();
        }
        departmentList.add("Please Select Department");
        departmentList.add("Software Development");
        departmentList.add("Software Management");


        //change spinner arrow color
        departmentSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                departmentList);
        departmentAdapter.setDropDownViewResource(R.layout.customizespinner);
        departmentSpinner.setAdapter(departmentAdapter);


        //disable all widget
        firstNameTxt.setEnabled(false);
        middleNameTxt.setEnabled(false);
        nameTxt.setEnabled(false);
        fathernameTxt.setEnabled(false);
        dobImg.setEnabled(false);
        dobTxt.setEnabled(false);
        designatiinTxt.setEnabled(false);
       // mailBtn.setEnabled(false);
        genderGroup.setEnabled(false);
       // femailBtn.setEnabled(false);
        materialStatusSpinner.setEnabled(false);
        childernTxt.setEnabled(false);
        emailTxt.setEnabled(false);
        natinalitySpinner.setEnabled(false);
        joingDateImg.setEnabled(false);
        joiningdateTxt.setEnabled(false);
        zoneSpinner.setEnabled(false);
        departmentSpinner.setEnabled(false);
        emplId.setEnabled(false);
        reporttoTxt.setEnabled(false);
        prefrednameTxt.setEnabled(false);
        passportNameTxt.setEnabled(false);
        passportnumberTxt.setEnabled(false);
        alternativeTxt.setEnabled(false);
        panNoTxt.setEnabled(false);
        titleSpinner.setEnabled(false);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //change button name
                editBtn.setText("Save Personal Detail");

                //enable the widget
                titleSpinner.setEnabled(true);
                firstNameTxt.setEnabled(true);
                middleNameTxt.setEnabled(true);
                nameTxt.setEnabled(true);
                prefrednameTxt.setEnabled(true);
                dobTxt.setEnabled(true);
                dobImg.setEnabled(true);
                alternativeTxt.setEnabled(true);
                passportNameTxt.setEnabled(true);
                passportnumberTxt.setEnabled(true);
                panNoTxt.setEnabled(true);
                natinalitySpinner.setEnabled(true);
            }
        });




        //bind Details Api
        personalDdlDetails();




        return rootView;
    }


    public void personalDetails(final String AdminID  , final String AuthCode) {

       /* final ProgressDialog pDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, personalDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        // String status = jsonObject.getString("status");
                        if (jsonObject.has("MsgNotification"))
                        {
                            String MsgNotification = jsonObject.getString("MsgNotification");
                            Toast.makeText(getActivity(), MsgNotification, Toast.LENGTH_SHORT).show();

                        }else
                        {

                            String Title = jsonObject.getString("Title");
                            String FirstName = jsonObject.getString("FirstName");
                            String MiddleName = jsonObject.getString("MiddleName");
                            String LastName = jsonObject.getString("LastName");
                            String FatherName = jsonObject.getString("FatherName");
                            String EmpID = jsonObject.getString("EmpID");
                            String Email = jsonObject.getString("Email");
                            String GenderName = jsonObject.getString("GenderName");
                            String NationalityName = jsonObject.getString("NationalityName");
                            String MartialStatusName = jsonObject.getString("MartialStatusName");
                            String ZoneName = jsonObject.getString("ZoneName");
                            String DepartmentName = jsonObject.getString("DepartmentName");
                            String DesignationName = jsonObject.getString("DesignationName");
                            String JoiningDate = jsonObject.getString("JoiningDate");
                            String ReportTo = jsonObject.getString("ReportTo");
                            String PassportNo = jsonObject.getString("PassportNo");
                            String PAN = jsonObject.getString("PAN");
                            String PassportName = jsonObject.getString("PassportName");
                            String PreferredName = jsonObject.getString("PreferredName");
                            String AlternateEmail = jsonObject.getString("AlternateEmail");
                            String DOB = jsonObject.getString("DOB");
                            String Children = jsonObject.getString("Children");

                            //feed data in widget
                            nameTxt.setText(LastName);
                            firstNameTxt.setText(FirstName);
                            middleNameTxt.setText(MiddleName);
                            fathernameTxt.setText(FatherName);
                            dobTxt.setText(DOB);
                            designatiinTxt.setText(DesignationName);
                            childernTxt.setText(Children);
                            emailTxt.setText(Email);
                            joiningdateTxt.setText(JoiningDate);
                            emplId.setText(EmpID);
                            reporttoTxt.setText(ReportTo);
                            prefrednameTxt.setText(PreferredName);
                            passportNameTxt.setText(PassportName);
                            passportnumberTxt.setText(PassportNo);
                            alternativeTxt.setText(AlternateEmail);
                            panNoTxt.setText(PAN);

                            //material selected Spinner
                            for (int j = 0; j<materialList.size(); j++)
                            {
                                if (materialList.get(j).equalsIgnoreCase(MartialStatusName))
                                {
                                    materialStatusSpinner.setSelection(j);
                                }
                            }

                            //nationality selected Spiiner
                            for (int k=0; k<nationalityList.size(); k++)
                            {
                                if (nationalityList.get(k).equalsIgnoreCase(NationalityName))
                                {
                                    natinalitySpinner.setSelection(k);
                                }
                            }

                            //Zone selected Spinner
                            for (int l=0; l<zoneList.size(); l++)
                            {
                                if (zoneList.get(l).equalsIgnoreCase(ZoneName))
                                {
                                    zoneSpinner.setSelection(l);
                                }
                            }

                            //Department Selected Spinner
                            for (int m = 0; m<departmentList.size(); m++)
                            {
                                if (departmentList.get(m).equalsIgnoreCase(DepartmentName))
                                {
                                    departmentSpinner.setSelection(m);
                                }
                            }

                            //title Spinner
                            for (int n =0; n<titleList.size(); n++)
                            {
                                if (titleList.get(n).equalsIgnoreCase(Title))
                                {
                                    titleSpinner.setSelection(n);
                                }
                            }

                            //gender selected
                            if (GenderName.equalsIgnoreCase("Male"))
                            {
                                mailBtn.setChecked(true);
                            }else
                                {
                                    femailBtn.setChecked(true);
                                }




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

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AdminID", AdminID);
                params.put("AuthCode",AuthCode);

                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }

    //bind all spiiner data
    public void personalDdlDetails() {


        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, personalDdlDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                    //bind material List
                    if (materialList.size()>0)
                    {
                        materialList.clear();
                    }
                    materialList.add("Please Select Material Status");
                    JSONArray materialObj = jsonObject.getJSONArray("MartialStatus");
                    for (int i =0; i<materialObj.length(); i++)
                    {
                        JSONObject object = materialObj.getJSONObject(i);

                        String MartialStatusName = object.getString("MartialStatusName");
                        materialList.add(MartialStatusName);

                    }

                    //bind Nationality List
                    if (nationalityList.size()>0)
                    {
                        nationalityList.clear();
                    }
                    nationalityList.add("Please Select Nationality");
                    JSONArray nationalityObj = jsonObject.getJSONArray("NationalityMaster");
                    for (int j=0; j<nationalityObj.length(); j++)
                    {
                        JSONObject object = nationalityObj.getJSONObject(j);

                        String NationalityName = object.getString("NationalityName");
                        nationalityList.add(NationalityName);
                    }

                    //bind Title Spinner Data
                    if (titleList.size()>0)
                    {
                        titleList.clear();
                    }
                    titleList.add("Please Select Title");
                    JSONArray titleObj = jsonObject.getJSONArray("TitleMaster");
                    for (int k=0; k<titleObj.length(); k++)
                    {
                        JSONObject object = titleObj.getJSONObject(k);

                        String TitleName = object.getString("TitleName");
                        titleList.add(TitleName);
                    }

                    materialAdapter.notifyDataSetChanged();
                    nationaolityAdapter.notifyDataSetChanged();
                    titleAdapter.notifyDataSetChanged();


                    personalDetails(userId,authcode);






                   // pDialog.dismiss();

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

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        })/*{
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AdminID", AdminID);
                params.put("AuthCode",AuthCode);

                Log.e("Parms", params.toString());
                return params;
            }

        }*/;
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }
   /* // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

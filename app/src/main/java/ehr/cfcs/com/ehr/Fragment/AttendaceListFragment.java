package ehr.cfcs.com.ehr.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ehr.cfcs.com.ehr.Adapter.AttendanceListAdapter;
import ehr.cfcs.com.ehr.Adapter.LeaveMangementAdapter;
import ehr.cfcs.com.ehr.Main.AttendanceModule;
import ehr.cfcs.com.ehr.Main.NewAddLeaveMangementActivity;
import ehr.cfcs.com.ehr.Model.AttendanceListModel;
import ehr.cfcs.com.ehr.Model.LeaveManagementModel;
import ehr.cfcs.com.ehr.Model.MonthModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AttendaceListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttendaceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendaceListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public AttendanceListAdapter adapter;
    public ArrayList<AttendanceListModel> list = new ArrayList<>();
    public RecyclerView ateendanceListRecy;
    public FloatingActionButton fab;
    public String userId = "", authCode = "";
    public ConnectionDetector conn;
    public String attendnaceListUrl = SettingConstant.BaseUrl + "AppEmployeeAttendanceList";
    public Spinner monthSpinner, yearSpinner;
    public ArrayList<String> yearList = new ArrayList<>();
    public ArrayList<MonthModel> monthList = new ArrayList<>();
    public ArrayAdapter<MonthModel> monthAdapter;
    public ArrayAdapter<String> yearAdapter;
    public ImageView serchBtn;
    public String  yearString ="";
    public int monthString =0;
    public TextView noRecordFoundTxt;

    private OnFragmentInteractionListener mListener;

    public AttendaceListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttendaceListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttendaceListFragment newInstance(String param1, String param2) {
        AttendaceListFragment fragment = new AttendaceListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_attendace_list, container, false);

        String strtext = getArguments().getString("Count");
        Log.e("checking count",strtext + " null");

        mListener.onFragmentInteraction(strtext);

        ateendanceListRecy = (RecyclerView) rootView.findViewById(R.id.attendace_list_recycler);
       // fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        monthSpinner = (Spinner)rootView.findViewById(R.id.monthspinner);
        yearSpinner = (Spinner) rootView.findViewById(R.id.yearspinner);
        serchBtn = (ImageView)rootView.findViewById(R.id.serchresult);
        noRecordFoundTxt = (TextView)rootView.findViewById(R.id.norecordfound);

        conn = new ConnectionDetector(getActivity());

        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(getActivity())));
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(getActivity())));

        adapter = new AttendanceListAdapter(getActivity(),list,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ateendanceListRecy.setLayoutManager(mLayoutManager);
        ateendanceListRecy.setItemAnimator(new DefaultItemAnimator());
        ateendanceListRecy.setAdapter(adapter);

        ateendanceListRecy.getRecycledViewPool().setMaxRecycledViews(0, 0);

      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AttendanceModule.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);

            }
        });*/

        //current month and year
        Calendar c= Calendar.getInstance();
        final int cyear = c.get(Calendar.YEAR);//calender year starts from 1900 so you must add 1900 to the value recevie.i.e., 1990+112 = 2012
        final int cmonth = c.get(Calendar.MONTH);//this is april so you will receive  3 instead of 4.
        int rearYear = cyear-2;

        Log.e("current Year", rearYear + "");
        Log.e("current Month", cmonth + "");

        //Month spinner work
        if (monthList.size()>0)
        {
            monthList.clear();
        }

        monthList.add(new MonthModel(1,"Jan"));
        monthList.add(new MonthModel(2,"Feb"));
        monthList.add(new MonthModel(3,"Mar"));
        monthList.add(new MonthModel(4,"Apr"));
        monthList.add(new MonthModel(5,"May"));
        monthList.add(new MonthModel(6,"Jun"));
        monthList.add(new MonthModel(7,"July"));
        monthList.add(new MonthModel(8,"Aug"));
        monthList.add(new MonthModel(9,"Sep"));
        monthList.add(new MonthModel(10,"Oct"));
        monthList.add(new MonthModel(11,"Nov"));
        monthList.add(new MonthModel(12,"Dec"));

        monthSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        monthAdapter = new ArrayAdapter<MonthModel>(getActivity(), R.layout.customizespinner,
                monthList);
        monthAdapter.setDropDownViewResource(R.layout.customizespinner);
        monthSpinner.setAdapter(monthAdapter);

        //select the current Month First Time
        for (int i=0; i<monthList.size(); i++)
        {
            if (cmonth+1 == monthList.get(i).getMonthId())
            {
                monthSpinner.setSelection(i);
            }
        }

        //year Spinner Work
        if (yearList.size()>0)
        {
            yearList.clear();
        }

        yearList.add(cyear+ "");
        yearList.add(cyear-1 + "");
        yearList.add(cyear-2 + "");

        yearSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        yearAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                yearList);
        yearAdapter.setDropDownViewResource(R.layout.customizespinner);
        yearSpinner.setAdapter(yearAdapter);

        //first Time Call API
        if (conn.getConnectivityStatus()>0) {

            attendaceList(authCode, userId, cmonth+1 +"", cyear + "");

        }else
        {
            conn.showNoInternetAlret();
        }

        //selected spinner Data then call API
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                monthString = monthList.get(i).getMonthId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                yearString = yearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //search Result
        serchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (monthString == 0 || yearString.equalsIgnoreCase(""))
                {
                    monthString = cmonth+1;
                    yearString = cyear + "";

                }
                attendaceList(authCode, userId, monthString + "", yearString);
            }
        });


        return rootView;
    }


    //Attendace List
    public void attendaceList(final String AuthCode , final String AdminID, final String Month, final String year) {

        final ProgressDialog pDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, attendnaceListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    if (list.size()>0)
                    {
                        list.clear();
                    }
                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String AttendanceLogID = jsonObject.getString("AttendanceLogID");
                        String AttendanceDateText = jsonObject.getString("AttendanceDateText");
                        String InTime = jsonObject.getString("InTime");
                        String OutTime = jsonObject.getString("OutTime");
                        String WorkTime = jsonObject.getString("InOutDuration");
                        String Halfday = jsonObject.getString("Halfday");
                        String LateArrivalText = jsonObject.getString("LateArrival");
                        String EarlyLeavingText = jsonObject.getString("EarlyLeaving");
                        String StatusText = jsonObject.getString("StatusText");
                        String Name = jsonObject.getString("Name");


                        list.add(new AttendanceListModel(Name,AttendanceLogID,AttendanceDateText,InTime,OutTime,WorkTime
                                ,Halfday,LateArrivalText,EarlyLeavingText,StatusText,""));



                    }

                    if (list.size() == 0)
                    {
                        noRecordFoundTxt.setVisibility(View.VISIBLE);
                        ateendanceListRecy.setVisibility(View.GONE);
                    }else
                    {
                        noRecordFoundTxt.setVisibility(View.GONE);
                        ateendanceListRecy.setVisibility(View.VISIBLE);
                    }

                    adapter.notifyDataSetChanged();
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
                params.put("AuthCode",AuthCode);
                params.put("LoginAdminID",AdminID);
                params.put("EmployeeID",AdminID);
                params.put("Month",Month);
                params.put("Year",year);



                Log.e("Parms", params.toString());
                return params;
            }

        };
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
    }*/


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


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
        void onFragmentInteraction(String count);
    }
}

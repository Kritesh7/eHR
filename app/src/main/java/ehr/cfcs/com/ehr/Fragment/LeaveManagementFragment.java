package ehr.cfcs.com.ehr.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import ehr.cfcs.com.ehr.Adapter.LeaveMangementAdapter;
import ehr.cfcs.com.ehr.Main.HomeActivity;
import ehr.cfcs.com.ehr.Main.NewAddLeaveMangementActivity;
import ehr.cfcs.com.ehr.Model.LeaveManagementModel;
import ehr.cfcs.com.ehr.Model.LeaveSummarryModel;
import ehr.cfcs.com.ehr.R;
import ehr.cfcs.com.ehr.Source.AppController;
import ehr.cfcs.com.ehr.Source.ConnectionDetector;
import ehr.cfcs.com.ehr.Source.SettingConstant;
import ehr.cfcs.com.ehr.Source.SharedPrefs;
import ehr.cfcs.com.ehr.Source.UtilsMethods;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeaveManagementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeaveManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaveManagementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecyclerView leaveMangementRecy;
    public LeaveMangementAdapter adapter;
    public String leaveListUrl = SettingConstant.BaseUrl + "AppEmployeeLeaveList";

    public ArrayList<LeaveManagementModel> list = new ArrayList<>();
    public FloatingActionButton fab;
    public String userId = "", authCode = "";
    public ConnectionDetector conn;

    public LeaveManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaveManagementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaveManagementFragment newInstance(String param1, String param2) {
        LeaveManagementFragment fragment = new LeaveManagementFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_leave_management, container, false);

        leaveMangementRecy = (RecyclerView)rootView.findViewById(R.id.leave_management_recycler);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);

        conn = new ConnectionDetector(getActivity());

        userId =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAdminId(getActivity())));
        authCode =  UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(getActivity())));

        adapter = new LeaveMangementAdapter(getActivity(),list,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        leaveMangementRecy.setLayoutManager(mLayoutManager);
        leaveMangementRecy.setItemAnimator(new DefaultItemAnimator());
        leaveMangementRecy.setAdapter(adapter);

        leaveMangementRecy.getRecycledViewPool().setMaxRecycledViews(0, 0);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), NewAddLeaveMangementActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);

            }
        });
        //

        if (conn.getConnectivityStatus()>0) {

            leaveManagementData(authCode, userId, "", "", "-1");

        }else
            {
                conn.showNoInternetAlret();
            }

        return rootView;
    }

     private void prepareInsDetails() {

        LeaveManagementModel model = new LeaveManagementModel("Full Time","28-03-2017","03-09-2017","02-01-2017","Approved");
        list.add(model);
         model = new LeaveManagementModel("Full Time","28-03-2017","03-09-2017","02-01-2017","Approved");
         list.add(model);
         model = new LeaveManagementModel("Full Time","28-03-2017","03-09-2017","02-01-2017","Approved");
         list.add(model);
         model = new LeaveManagementModel("Full Time","28-03-2017","03-09-2017","02-01-2017","Approved");
         list.add(model);
         model = new LeaveManagementModel("Full Time","28-03-2017","03-09-2017","02-01-2017","Approved");
         list.add(model);


        adapter.notifyDataSetChanged();

    }

    public void leaveManagementData(final String AuthCode , final String AdminID, final String FromDate, final String ToDate,
                                 final String Status) {

        final ProgressDialog pDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, leaveListUrl, new Response.Listener<String>() {
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
                        String LeaveTypeName = jsonObject.getString("LeaveTypeName");
                        String StartDateText = jsonObject.getString("StartDateText");
                        String EndDateText = jsonObject.getString("EndDateText");
                        String AppliedDate = jsonObject.getString("AppliedDate");
                        String StatusText = jsonObject.getString("StatusText");
                        //String LeaveBalance = jsonObject.getString("LeaveBalance");

                        list.add(new LeaveManagementModel(LeaveTypeName,StartDateText,EndDateText,AppliedDate,StatusText));



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
                params.put("AdminID",AdminID);
                params.put("FromDate",FromDate);
                params.put("ToDate",ToDate);
                params.put("Status",Status);


                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
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

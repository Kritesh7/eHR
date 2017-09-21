package ehr.cfcs.com.ehr.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.Adapter.LeaveMangementAdapter;
import ehr.cfcs.com.ehr.Main.HomeActivity;
import ehr.cfcs.com.ehr.Main.NewAddLeaveMangementActivity;
import ehr.cfcs.com.ehr.Model.LeaveManagementModel;
import ehr.cfcs.com.ehr.R;

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
    public ArrayList<LeaveManagementModel> list = new ArrayList<>();
    public FloatingActionButton fab;

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

        prepareInsDetails();

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

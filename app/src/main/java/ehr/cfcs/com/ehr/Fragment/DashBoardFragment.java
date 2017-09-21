package ehr.cfcs.com.ehr.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ehr.cfcs.com.ehr.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashBoardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public LinearLayout leaverequsLay, attendanceLay, stationaryLay,docsLay,cabLay,hotelLay,appreceationLay,warningLay;

    public OnFragmentInteractionListener mListener;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);

        leaverequsLay = (LinearLayout)rootView.findViewById(R.id.leavereq);
        attendanceLay = (LinearLayout)rootView.findViewById(R.id.attendance_lay);
        stationaryLay = (LinearLayout)rootView.findViewById(R.id.stationary_lay);
        docsLay = (LinearLayout)rootView.findViewById(R.id.docs_lay);
        cabLay = (LinearLayout)rootView.findViewById(R.id.cab_lay);
        hotelLay = (LinearLayout)rootView.findViewById(R.id.hotel_lay);
        appreceationLay = (LinearLayout)rootView.findViewById(R.id.appre_lay);
        warningLay = (LinearLayout)rootView.findViewById(R.id.warning_lay);

        attendanceLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onFragmentInteraction(1);

                FragmentManager fragmentManager = getFragmentManager();
                Fragment frag = new AttendanceFragment();

                /*fragmentManager.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);*/
                // update the main content by replacing fragments
                fragmentManager.beginTransaction()
                        .replace(R.id.container, frag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

            }
        });

        stationaryLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onFragmentInteraction(18);

                FragmentManager fragmentManager = getFragmentManager();
                Fragment frag = new StationaryRequestFragment();

                /*fragmentManager.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);*/
                // update the main content by replacing fragments
                fragmentManager.beginTransaction()
                        .replace(R.id.container, frag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });

        docsLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onFragmentInteraction(19);

                FragmentManager fragmentManager = getFragmentManager();
                Fragment frag = new DocumentListFragment();

                /*fragmentManager.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);*/
                // update the main content by replacing fragments
                fragmentManager.beginTransaction()
                        .replace(R.id.container, frag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

            }
        });

        cabLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onFragmentInteraction(20);

                FragmentManager fragmentManager = getFragmentManager();
                Fragment frag = new TaxiListFragment();

                /*fragmentManager.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);*/
                // update the main content by replacing fragments
                fragmentManager.beginTransaction()
                        .replace(R.id.container, frag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

            }
        });

        hotelLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onFragmentInteraction(21);

                FragmentManager fragmentManager = getFragmentManager();
                Fragment frag = new HotelBookingListFragment();

                /*fragmentManager.setCustomAnimations(
                        R.anim.push_right_in,
                        R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);*/
                // update the main content by replacing fragments
                fragmentManager.beginTransaction()
                        .replace(R.id.container, frag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });

        appreceationLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        warningLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int navigationCount);
    }
}

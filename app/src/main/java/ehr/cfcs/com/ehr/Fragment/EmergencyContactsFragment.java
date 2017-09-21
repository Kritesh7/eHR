package ehr.cfcs.com.ehr.Fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import ehr.cfcs.com.ehr.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmergencyContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmergencyContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LinearLayout primoryLay, secondaryLay,mainLay;
    public RadioGroup emergencyGroup;
    public RadioButton primoryBtn, secondaryBtn;
    public Spinner relationShipSpinner,citySpinner,stateSpinner;
    boolean visible;

    public ArrayList<String> relationshipList = new ArrayList<>();
    public ArrayList<String> cityList = new ArrayList<>();
    public ArrayList<String> stateList = new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    public EmergencyContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmergencyContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergencyContactsFragment newInstance(String param1, String param2) {
        EmergencyContactsFragment fragment = new EmergencyContactsFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_emergency_contacts, container, false);

        primoryLay = (LinearLayout)rootView.findViewById(R.id.primory_layout);
        secondaryLay = (LinearLayout)rootView.findViewById(R.id.secondary_layout);
        mainLay = (LinearLayout)rootView.findViewById(R.id.mainlay);

        primoryBtn = (RadioButton)rootView.findViewById(R.id.primory_radiobtn);
        secondaryBtn = (RadioButton)rootView.findViewById(R.id.secondary_radiobtn);

        emergencyGroup = (RadioGroup)rootView.findViewById(R.id.emergency_radiogroup);

        relationShipSpinner = (Spinner)rootView.findViewById(R.id.relationshipspinner);
        citySpinner = (Spinner)rootView.findViewById(R.id.cityspinner);
        stateSpinner = (Spinner)rootView.findViewById(R.id.statespinner);

        if (relationshipList.size()>0)
        {
            relationshipList.clear();
        }

        relationshipList.add("Please Select Relationship");
        relationshipList.add("Father");
        relationshipList.add("Mother");
        relationshipList.add("Brother");
        relationshipList.add("Freind");


        //change spinner arrow color

        relationShipSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> assignToAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                relationshipList);
        assignToAdapter.setDropDownViewResource(R.layout.customizespinner);
        relationShipSpinner.setAdapter(assignToAdapter);

        if (cityList.size()>0)
        {
            cityList.clear();
        }

        cityList.add("Please Select City");
        cityList.add("Delhi");
        cityList.add("Agra");
        cityList.add("Mathura");
        cityList.add("Haryana");


        //change spinner arrow color

        citySpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                cityList);
        cityAdapter.setDropDownViewResource(R.layout.customizespinner);
        citySpinner.setAdapter(cityAdapter);


        if (stateList.size()>0)
        {
            stateList.clear();
        }

        stateList.add("Please Select State");
        stateList.add("UP");
        stateList.add("Punjab");
        stateList.add("MP");
        stateList.add("Haryana");


        //change spinner arrow color

        stateSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.customizespinner,
                stateList);
        stateAdapter.setDropDownViewResource(R.layout.customizespinner);
        stateSpinner.setAdapter(stateAdapter);




        primoryBtn.setChecked(true);

        emergencyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                if (checkedId == R.id.primory_radiobtn)
                {
                    TransitionManager.beginDelayedTransition(mainLay);
                    visible = !visible;
                    primoryLay.setVisibility(View.VISIBLE);
                    secondaryLay.setVisibility(View.GONE);
                }
                else if (checkedId == R.id.secondary_radiobtn)
                {
                    TransitionManager.beginDelayedTransition(mainLay);
                    visible = !visible;
                    primoryLay.setVisibility(View.GONE);
                    secondaryLay.setVisibility(View.VISIBLE);
                }
            }
        });


        return rootView;
    }

    /*// TODO: Rename method, update argument and hook method into UI event
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

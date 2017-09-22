package ehr.cfcs.com.ehr.Fragment;

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

import ehr.cfcs.com.ehr.Adapter.EducationDetailsAdapter;
import ehr.cfcs.com.ehr.Adapter.OfficelyAdapter;
import ehr.cfcs.com.ehr.Main.AddCabActivity;
import ehr.cfcs.com.ehr.Main.AddQualificationActivity;
import ehr.cfcs.com.ehr.Model.EducationModel;
import ehr.cfcs.com.ehr.Model.OfficealyModel;
import ehr.cfcs.com.ehr.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EducationDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EducationDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EducationDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EducationDetailsAdapter adapter;
    public ArrayList<EducationModel> list = new ArrayList<>();
    public RecyclerView educationRecycler;
    public FloatingActionButton fab;

    private OnFragmentInteractionListener mListener;

    public EducationDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EducationDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EducationDetailsFragment newInstance(String param1, String param2) {
        EducationDetailsFragment fragment = new EducationDetailsFragment();
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


        View rootView = inflater.inflate(R.layout.fragment_edication_details, container, false);

        educationRecycler = (RecyclerView)rootView.findViewById(R.id.education_recycler);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);

        adapter = new EducationDetailsAdapter(getActivity(),list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        educationRecycler.setLayoutManager(mLayoutManager);
        educationRecycler.setItemAnimator(new DefaultItemAnimator());
        educationRecycler.setAdapter(adapter);

        educationRecycler.getRecycledViewPool().setMaxRecycledViews(0, 0);

        prepareInsDetails();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AddQualificationActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });

        return rootView;
    }

    private void prepareInsDetails() {

        EducationModel model = new EducationModel("M.C.A","Computer Sceince","03-09-2017","GLA","Full Time","M.C.A");
        list.add(model);
        model = new EducationModel("M.C.A","Computer Sceince","03-09-2017","GLA","Full Time","M.C.A");
        list.add(model);
        model = new EducationModel("M.C.A","Computer Sceince","03-09-2017","GLA","Full Time","M.C.A");
        list.add(model);
        model = new EducationModel("M.C.A","Computer Sceince","03-09-2017","GLA","Full Time","M.C.A");
        list.add(model);
        model = new EducationModel("M.C.A","Computer Sceince","03-09-2017","GLA","Full Time","M.C.A");
        list.add(model);


        adapter.notifyDataSetChanged();

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

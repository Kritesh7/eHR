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

import ehr.cfcs.com.ehr.Adapter.LangaugeAdapter;
import ehr.cfcs.com.ehr.Adapter.TraningAdapter;
import ehr.cfcs.com.ehr.Main.AddMedicalandAnssuranceActivity;
import ehr.cfcs.com.ehr.Main.AddNewLnaguageActivity;
import ehr.cfcs.com.ehr.Model.LanguageModel;
import ehr.cfcs.com.ehr.Model.TraningModel;
import ehr.cfcs.com.ehr.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LanguagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LanguagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanguagesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LangaugeAdapter adapter;
    public ArrayList<LanguageModel> list = new ArrayList<>();
    public RecyclerView langauageRecy;
    public FloatingActionButton fab;

    private OnFragmentInteractionListener mListener;

    public LanguagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanguagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LanguagesFragment newInstance(String param1, String param2) {
        LanguagesFragment fragment = new LanguagesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_languages, container, false);

        langauageRecy = (RecyclerView)rootView.findViewById(R.id.lanagauage_recycler);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);

        adapter = new LangaugeAdapter(getActivity(),list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        langauageRecy.setLayoutManager(mLayoutManager);
        langauageRecy.setItemAnimator(new DefaultItemAnimator());
        langauageRecy.setAdapter(adapter);

        langauageRecy.getRecycledViewPool().setMaxRecycledViews(0, 0);

        prepareInsDetails();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AddNewLnaguageActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });

        return rootView;
    }

    private void prepareInsDetails() {

        LanguageModel model = new LanguageModel("English","Yes","Yes","Yes");
        list.add(model);
        model = new LanguageModel("English","Yes","Yes","Yes");
        list.add(model);
        model = new LanguageModel("English","Yes","Yes","Yes");
        list.add(model);
        model = new LanguageModel("English","Yes","Yes","Yes");
        list.add(model);
        model = new LanguageModel("English","Yes","Yes","Yes");
        list.add(model);


        adapter.notifyDataSetChanged();

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

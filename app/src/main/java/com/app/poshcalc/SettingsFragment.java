package com.app.poshcalc;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    private static final String ARG_TAX = "tax";
    private static final String ARG_PROF = "profit";
    private static final String ARG_CAP = "capital";
    private static final String ARG_FEE = "fees";

    // TODO: Rename and change types of parameters
    private float tax, profit, capital, fees;
    private Button save;
    private TextView taxView, profitView, capitalView, feeView;

    private SettingsFragment.OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * @param _tax     The user's local tax rate to be paid on sales profit.
     * @param _profit  The desired profit from each item.
     * @param _capital The desired re-investment capital from each item.
     * @param _fees    Service fees associated with the selling platform.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(float _tax, float _profit, float _capital, float _fees) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putFloat(ARG_TAX, _tax);
        args.putFloat(ARG_PROF, _profit);
        args.putFloat(ARG_CAP, _capital);
        args.putFloat(ARG_FEE, _fees);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tax = getArguments().getFloat(ARG_TAX) / 100;
            profit = getArguments().getFloat(ARG_PROF);
            capital = getArguments().getFloat(ARG_CAP);
            fees = getArguments().getFloat(ARG_FEE) / 100;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        }
        else {
            throw new RuntimeException(context.toString()
                                       + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onFragmentInteraction(Uri uri);
    }
}

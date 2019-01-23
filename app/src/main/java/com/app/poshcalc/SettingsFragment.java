package com.app.poshcalc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSettingsInteractionListener} interface
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
    private ImageButton saveButton;
    private TextView taxView, profitView, capitalView, feeView;

    private OnSettingsInteractionListener mListener;

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

        taxView = (TextView) view.findViewById(R.id.taxView);
        profitView = (TextView) view.findViewById(R.id.profitView);
        capitalView = (TextView) view.findViewById(R.id.capitalView);
        feeView = (TextView) view.findViewById(R.id.feeView);
        taxView.setText(String.valueOf(Math.round(tax * 100)));
        profitView.setText(String.valueOf(Math.round(profit)));
        capitalView.setText(String.valueOf(Math.round(capital)));
        feeView.setText(String.valueOf(Math.round(fees * 100)));

        saveButton = (ImageButton) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(taxView.getText()) || TextUtils.isEmpty(profitView.getText()) || TextUtils.isEmpty(capitalView.getText()) || TextUtils.isEmpty(feeView.getText())) {
                    Toast.makeText(getContext(), "Please fill all fields before saving", Toast.LENGTH_SHORT).show();
                    return;
                }

                float vTax = Float.parseFloat(taxView.getText().toString());
                float vProfit = Float.parseFloat(profitView.getText().toString());
                float vCapital = Float.parseFloat(capitalView.getText().toString());
                float vFees = Float.parseFloat(feeView.getText().toString());
                OnSettingsInteractionListener listener = (OnSettingsInteractionListener) getActivity();
                listener.onSettingsSaved(vTax, vProfit, vCapital, vFees);
            }
        });

        if (getActivity().getCurrentFocus() != null)
            getActivity().getCurrentFocus().clearFocus();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingsInteractionListener) {
            mListener = (OnSettingsInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                                       + " must implement OnSettingsInteractionListener");
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
    public interface OnSettingsInteractionListener {
        void onSettingsSaved(float taxes, float profit, float capital, float fees);
    }
}

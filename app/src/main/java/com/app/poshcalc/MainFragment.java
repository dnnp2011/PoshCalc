package com.app.poshcalc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TAX = "tax";
    private static final String ARG_PROF = "profit";
    private static final String ARG_CAP = "capital";
    private static final String ARG_FEE = "fees";
    private static final String ARG_DICT = "dictionary";

    // TODO: Rename and change types of parameters
    private float tax, profit, capital, fees;
    private ArrayList<String> priceCodeDictionary;
    private Button calculateButton;
    private TextView purchasePriceView, minPriceView, targetPriceView, priceCodeView;
    private CardView resultsCardViewLayout;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * @param _tax The user's local tax rate to be paid on sales profit.
     * @param _profit The desired profit from each item.
     * @param _capital The desired re-investment capital from each item.
     * @param _fees Service fees associated with the selling platform.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(float _tax, float _profit, float _capital, float _fees, ArrayList<String> _priceCodeDictionary) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putFloat(ARG_TAX, _tax);
        args.putFloat(ARG_PROF, _profit);
        args.putFloat(ARG_CAP, _capital);
        args.putFloat(ARG_FEE, _fees);
        args.putStringArrayList(ARG_DICT, _priceCodeDictionary);
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
            priceCodeDictionary = getArguments().getStringArrayList(ARG_DICT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        purchasePriceView = (TextView) view.findViewById(R.id.purchasePriceInput);
        minPriceView = (TextView) view.findViewById(R.id.minSellPriceView);
        targetPriceView = (TextView) view.findViewById(R.id.targetSellPriceView);
        priceCodeView = (TextView) view.findViewById(R.id.priceCodeView);
        resultsCardViewLayout = (CardView) view.findViewById(R.id.resultsCardViewLayout);
        resultsCardViewLayout.setVisibility(View.GONE);

        calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float purchasePrice = Float.parseFloat(purchasePriceView.getText().toString());
                float minPrice = MainActivity.roundToScale(getMinSellPrice(purchasePrice), 2);
                float targetPrice = MainActivity.roundToScale(getTargetSellPrice(purchasePrice), 2);
                String priceCode = getItemPriceCode(minPrice, targetPrice);
                minPriceView.setText(String.valueOf(minPrice));
                targetPriceView.setText(String.valueOf(targetPrice));
                priceCodeView.setText(priceCode);

//                TODO: Create fade in animation for results CardView
                resultsCardViewLayout.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private float getTargetSellPrice(float pPrice) {
        float pTax = (profit + capital) * tax;
        float pFee = (pPrice + profit + capital + pTax) * fees;
        return pPrice + profit + capital + pTax + pFee;
    }

    private float getMinSellPrice(float pPrice) {
        float pFee = (pPrice + 1) * fees;
        return pPrice + pFee;
    }

    private String getItemPriceCode(float mPrice, float tPrice) {
        String _mPrice = String.valueOf(Math.round(mPrice));
        String _tPrice = String.valueOf(Math.round(tPrice));
        char[] mPriceDigits = _mPrice.toCharArray();
        char[] tPriceDigits = _tPrice.toCharArray();
        StringBuilder priceCode = new StringBuilder();
        for (char mPriceDigit : mPriceDigits) {
            String digit = String.valueOf(mPriceDigit);
            int intDigit = Integer.parseInt(digit);
            priceCode.append(priceCodeDictionary.get(intDigit));
        }
        priceCode.append(" - ");
        for (char tPriceDigit : tPriceDigits) {
            String digit = String.valueOf(tPriceDigit);
            int intDigit = Integer.parseInt(digit);
            priceCode.append(priceCodeDictionary.get(intDigit));
        }

        if (mPriceDigits.length + tPriceDigits.length != (priceCode.length() - 3)) {
            throw new RuntimeException(getContext().toString()
               + " getItemPriceCode() length of price code does not equal the length of the input");
        }
        return priceCode.toString();
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

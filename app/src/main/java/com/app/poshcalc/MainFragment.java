package com.app.poshcalc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


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
     * @param _tax     The user's local tax rate to be paid on sales profit.
     * @param _profit  The desired profit from each item.
     * @param _capital The desired re-investment capital from each item.
     * @param _fees    Service fees associated with the selling platform.
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        purchasePriceView = (TextView) view.findViewById(R.id.purchasePriceInput);
        purchasePriceView.setImeActionLabel("Calculate", EditorInfo.IME_ACTION_DONE);
        purchasePriceView.clearFocus();
        purchasePriceView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND
                                                                                                                                                && event.getAction() == KeyEvent.ACTION_DOWN) {
                    MainActivity.hideSoftKeyboard(getActivity());
                    v.clearFocus();
                    onClickCalculate();
                }
                return true;
            }
        });
        minPriceView = (TextView) view.findViewById(R.id.minSellPriceView);
        targetPriceView = (TextView) view.findViewById(R.id.targetSellPriceView);
        priceCodeView = (TextView) view.findViewById(R.id.priceCodeView);
        resultsCardViewLayout = (CardView) view.findViewById(R.id.resultsCardViewLayout);
        resultsCardViewLayout.setAlpha(0f);

        calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickCalculate();
            }
        });

        return view;
    }

    private void onClickCalculate() {
        if (TextUtils.isEmpty(purchasePriceView.getText())) {
            Toast.makeText(getContext(), "You didn't enter a purchase price", Toast.LENGTH_SHORT).show();
            return;
        }

        tax = getArguments().getFloat(ARG_TAX) / 100;
        profit = getArguments().getFloat(ARG_PROF);
        capital = getArguments().getFloat(ARG_CAP);
        fees = getArguments().getFloat(ARG_FEE) / 100;

        float purchasePrice = Float.parseFloat(purchasePriceView.getText().toString());
        float minPrice = MainActivity.roundToScale(getMinSellPrice(purchasePrice), 2);
        float targetPrice = MainActivity.roundToScale(getTargetSellPrice(purchasePrice), 2);
        String priceCode = getItemPriceCode(minPrice, targetPrice);
        minPriceView.setText(String.valueOf(minPrice));
        targetPriceView.setText(String.valueOf(targetPrice));
        priceCodeView.setText(priceCode);

//                resultsCardViewLayout.setVisibility(View.VISIBLE);
        resultsCardViewLayout.setTranslationY(resultsCardViewLayout.getHeight());
        resultsCardViewLayout.animate()
                             .translationY(0)
                             .alpha(1f)
                             .setDuration(300);
    }

    private float getTargetSellPrice(float pPrice) {
        float pTax = (profit + capital) * tax;
        float pFee = (pPrice + profit + capital + pTax) * fees;
        return pPrice + profit + capital + pTax + pFee;
    }

    private float getMinSellPrice(float pPrice) {
        float pFee = pPrice * fees;
        return pPrice + pFee + 1;
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
                                       + " must implement OnSettingsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

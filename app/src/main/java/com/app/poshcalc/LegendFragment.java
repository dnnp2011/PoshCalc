package com.app.poshcalc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LegendFragment extends Fragment implements SwipeAdapter.FragmentLifecycle {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DICT = "dictionary";

    private ArrayList<String> priceCodeDictionary;
    private TextView letter0, letter1, letter2, letter3, letter4, letter5, letter6, letter7, letter8, letter9;
    private Button updateButton;

    private OnLegendFragmentInteractionListener mListener;

    public LegendFragment() {
        // Required empty public constructor
    }

    public static LegendFragment newInstance(ArrayList<String> _priceCodeDictionary) {
        LegendFragment fragment = new LegendFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_DICT, _priceCodeDictionary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            priceCodeDictionary = getArguments().getStringArrayList(ARG_DICT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_legend, container, false);

        letter0 = view.findViewById(R.id.letter0);
        letter0.setText(priceCodeDictionary.get(0));
        letter1 = view.findViewById(R.id.letter1);
        letter1.setText(priceCodeDictionary.get(1));
        letter2 = view.findViewById(R.id.letter2);
        letter2.setText(priceCodeDictionary.get(2));
        letter3 = view.findViewById(R.id.letter3);
        letter3.setText(priceCodeDictionary.get(3));
        letter4 = view.findViewById(R.id.letter4);
        letter4.setText(priceCodeDictionary.get(4));
        letter5 = view.findViewById(R.id.letter5);
        letter5.setText(priceCodeDictionary.get(5));
        letter6 = view.findViewById(R.id.letter6);
        letter6.setText(priceCodeDictionary.get(6));
        letter7 = view.findViewById(R.id.letter7);
        letter7.setText(priceCodeDictionary.get(7));
        letter8 = view.findViewById(R.id.letter8);
        letter8.setText(priceCodeDictionary.get(8));
        letter9 = view.findViewById(R.id.letter9);
        letter9.setText(priceCodeDictionary.get(9));

        updateButton = view.findViewById(R.id.updateLegendButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(letter0.getText()) || TextUtils.isEmpty(letter1.getText()) || TextUtils.isEmpty(letter2.getText()) || TextUtils.isEmpty(letter3.getText()) || TextUtils.isEmpty(letter4.getText())
                    || TextUtils.isEmpty(letter5.getText()) || TextUtils.isEmpty(letter6.getText()) || TextUtils.isEmpty(letter7.getText()) || TextUtils.isEmpty(letter8.getText()) || TextUtils.isEmpty(letter9.getText())) {
                    Toast.makeText(getContext(), "You cannot leave empty fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isDigitsOnly(letter0.getText()) || TextUtils.isDigitsOnly(letter1.getText()) || TextUtils.isDigitsOnly(letter2.getText())  || TextUtils.isDigitsOnly(letter3.getText())  || TextUtils.isDigitsOnly(letter4.getText())
                    || TextUtils.isDigitsOnly(letter5.getText()) || TextUtils.isDigitsOnly(letter6.getText()) || TextUtils.isDigitsOnly(letter7.getText()) || TextUtils.isDigitsOnly(letter8.getText()) || TextUtils.isDigitsOnly(letter9.getText())) {
                    Toast.makeText(getContext(), "Values must be letters or special characters", Toast.LENGTH_LONG).show();
                    return;
                }

                ArrayList<String> tmp = priceCodeDictionary;
                tmp.add(letter0.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter1.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter2.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter3.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter4.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter5.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter6.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter7.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter8.getText().toString().substring(0, 1).toUpperCase());
                tmp.add(letter9.getText().toString().substring(0, 1).toUpperCase());

                /*if (tmp.toArray() == priceCodeDictionary.toArray()) {
                    Toast.makeText(getContext(), "You haven't made any valid changes", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                for (int i = 0; i < tmp.size(); i++) {
                    String tmpChar = tmp.get(i);
                    if (tmp.indexOf(tmpChar) != tmp.lastIndexOf(tmpChar)) {
                        Toast.makeText(getContext(), "There cannot be duplicate letters. Remove the duplicate '" + tmpChar + "'", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                priceCodeDictionary = tmp;
                mListener.onUpdateLegend(priceCodeDictionary);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLegendFragmentInteractionListener) {
            mListener = (OnLegendFragmentInteractionListener) context;
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

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
        Bundle args = getArguments();
        assert args != null;
        priceCodeDictionary = args.getStringArrayList(ARG_DICT);
        assert priceCodeDictionary != null;
        letter0.setText(priceCodeDictionary.get(0));
        letter1.setText(priceCodeDictionary.get(1));
        letter2.setText(priceCodeDictionary.get(2));
        letter3.setText(priceCodeDictionary.get(3));
        letter4.setText(priceCodeDictionary.get(4));
        letter5.setText(priceCodeDictionary.get(5));
        letter6.setText(priceCodeDictionary.get(6));
        letter7.setText(priceCodeDictionary.get(7));
        letter8.setText(priceCodeDictionary.get(8));
        letter9.setText(priceCodeDictionary.get(9));
    }

    public interface OnLegendFragmentInteractionListener {
        void onUpdateLegend(ArrayList<String> priceCodeDictionary);
    }
}

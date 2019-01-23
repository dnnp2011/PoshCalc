package com.app.poshcalc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LegendFragment extends Fragment implements SwipeAdapter.FragmentLifecycle {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DICT = "dictionary";

    private ArrayList<String> priceCodeDictionary;
    private TextView letter0, letter1, letter2, letter3, letter4, letter5, letter6, letter7, letter8, letter9;

    private OnFragmentInteractionListener mListener;

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

        return view;
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

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

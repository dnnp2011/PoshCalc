package com.app.poshcalc;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.app.FragmentManager;
import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends Activity implements MainFragment.OnFragmentInteractionListener, LegendFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener{

    private float Taxes, Profit, Capital, Fees;
    private SharedPreferences sharedPreferences;

    private static final String PREF_TAX = "tax";
    private static final String PREF_PROF = "profit";
    private static final String PREF_CAP = "capital";
    private static final String PREF_FEE = "fees";

    ArrayList<String> PriceCodeDictionary = new ArrayList<>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        Taxes = sharedPreferences.getFloat(PREF_TAX, 9);
        Profit = sharedPreferences.getFloat(PREF_PROF, 10);
        Capital = sharedPreferences.getFloat(PREF_CAP, 10);
        Fees = sharedPreferences.getFloat(PREF_FEE, 20);
        System.out.print("Successfully loaded sharedPreferences from memory");
        PriceCodeDictionary.add("P");
        PriceCodeDictionary.add("O");
        PriceCodeDictionary.add("S");
        PriceCodeDictionary.add("H");
        PriceCodeDictionary.add("U");
        PriceCodeDictionary.add("L");
        PriceCodeDictionary.add("A");
        PriceCodeDictionary.add("T");
        PriceCodeDictionary.add("E");
        PriceCodeDictionary.add("R");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

//        loadMainFragment();
//        loadNextFragment(LegendFragment.newInstance(PriceCodeDictionary));
        loadNextFragment(SettingsFragment.newInstance(Taxes, Profit, Capital, Fees));
    }

    @SuppressLint("ResourceType")
    private void loadMainFragment() {
        FragmentManager manager = getFragmentManager();
        MainFragment mainFragment = (MainFragment) manager.findFragmentById(R.id.fragmentContainer);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance(Taxes, Profit, Capital, Fees, PriceCodeDictionary);
            manager.beginTransaction()
                   .add(R.id.fragmentContainer, mainFragment)
                   .commit();
        }
    }

    public void loadNextFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
               .addToBackStack(null)
               .replace(R.id.fragmentContainer, fragment)
               .commit();
    }

    public void saveUserPreferences(float _tax, float _profit, float _capital, float _fees) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(PREF_TAX, _tax);
        editor.putFloat(PREF_PROF, _profit);
        editor.putFloat(PREF_CAP, _capital);
        editor.putFloat(PREF_FEE, _fees);
        editor.commit();
    }

    public static float roundToScale(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

package com.app.poshcalc;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements MainFragment.OnFragmentInteractionListener, LegendFragment.OnFragmentInteractionListener, SettingsFragment.OnSettingsInteractionListener {

//    TODO: Add slide gesture and animations for moving between fragments
//    TODO: Add animations for menu item navigation
//    TODO: Temporarily store calculate view state for popping backstack (until app close)
//    TODO: Add calculate results enter and exit animations
//    TODO: Add isEmpty check for all inputs. Alert if empty.
//    TODO: Hitting enter on calculate pag keyboard auto clicks calculate button
//    TODO: Fine tune settings page calculation
//    TODO: Fix empty activity on backstack
//    TODO: Add vector animation for sell price range

    private float Taxes, Profit, Capital, Fees;
    private SharedPreferences sharedPreferences;

    private CoordinatorLayout coordinatorLayout;
    private static final String PREF_TAX = "tax";
    private static final String PREF_PROF = "profit";
    private static final String PREF_CAP = "capital";
    private static final String PREF_FEE = "fees";

    private ArrayList<String> PriceCodeDictionary = new ArrayList<>(10);
    private ViewPager viewPager;

    public static Fragment mainFragment, legendFragment, settingsFragment;

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
        coordinatorLayout = findViewById(R.id.rootCoordinatorLayout);

        mainFragment = MainFragment.newInstance(Taxes, Profit, Capital, Fees, PriceCodeDictionary);
        legendFragment = LegendFragment.newInstance(PriceCodeDictionary);
        settingsFragment = SettingsFragment.newInstance(Taxes, Profit, Capital, Fees);

        viewPager = findViewById(R.id.fragmentContainer);
        viewPager.setOffscreenPageLimit(1);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(1);

//        loadMainFragment();
    }

    private void loadMainFragment() {
        FragmentManager manager = getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) manager.findFragmentById(R.id.fragmentContainer);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance(Taxes, Profit, Capital, Fees, PriceCodeDictionary);
            manager.beginTransaction()
                   .add(R.id.fragmentContainer, mainFragment)
                   .commit();
        }
    }

    public void showSnackbar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Settings saved", Snackbar.LENGTH_SHORT);

        TextView text = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbar.getView().setBackgroundColor(getColor(R.color.colorPrimaryDark));
        text.setTextColor(getColor(R.color.colorAccent));
        text.setGravity(Gravity.CENTER);
        text.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_check_circle_orange_24dp), null);
        snackbar.show();
    }

    public void loadNextFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
               .addToBackStack(String.valueOf(fragment.getId()))
               .replace(R.id.fragmentContainer, fragment)
               .commit();
    }

    public static float roundToScale(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ((float) ((int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp))) / pow;
    }

    private void updatePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(PREF_TAX, Taxes);
        editor.putFloat(PREF_PROF, Profit);
        editor.putFloat(PREF_CAP, Capital);
        editor.putFloat(PREF_FEE, Fees);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem mainMenu = menu.getItem(0);
        MenuItem legendMenu = menu.getItem(1);
        legendMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                viewPager.setCurrentItem(0);
                return true;
            }
        });
        mainMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                viewPager.setCurrentItem(1);
                return true;
            }
        });
        MenuItem settingsMenu = menu.getItem(2);
        settingsMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                viewPager.setCurrentItem(2);
                return true;
            }
        });
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

    @Override
    public void onSettingsSaved(float _taxes, float _profit, float _capital, float _fees) {
        Taxes = _taxes;
        Profit = _profit;
        Capital = _capital;
        Fees = _fees;
        updatePreferences();
        showSnackbar();
    }
}

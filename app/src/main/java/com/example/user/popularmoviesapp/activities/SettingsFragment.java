package com.example.user.popularmoviesapp.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.StringDef;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

import com.example.user.popularmoviesapp.R;

/**
 * Created by user on 7/15/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat
{
    private static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    /*Setting the preference Summary */
    private void setPreferenceSummary(Preference preference,Object value)
    {
        String stringValue = value.toString();
        ListPreference listPreference = (ListPreference) preference;
        int prefIndex = listPreference.findIndexOfValue(stringValue);
        if(prefIndex >= 0)
        {
            preference.setSummary(listPreference.getEntries()[prefIndex]);
        }else{
            Log.d(LOG_TAG,"Preference Summary Error");
        }
    }



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.pref_general);
        SharedPreferences sharedPreferences  = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        Preference p = prefScreen.getPreference(0);
        if(p instanceof ListPreference){
        String value = sharedPreferences.getString(p.getKey(),"");
        setPreferenceSummary(p,value);
        }else
        {
            Log.d(LOG_TAG,"Invalid preference instance");
        }
    }

}

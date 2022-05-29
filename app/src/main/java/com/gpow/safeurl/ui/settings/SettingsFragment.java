package com.gpow.safeurl.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gpow.safeurl.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private boolean flagSafeBrowser;
    private boolean flagFileAccess;
    private boolean flagJavascript;
    private boolean flagGeolocationData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        SettingsViewModel settingsViewModel =
//                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        setupSettingSwitches();
        return root;
    }

    private void setupSettingSwitches() {

        flagSafeBrowser = getData("safeurl.SafeBrowser",true);
        binding.safebrowsingSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData("safeurl.SafeBrowser", binding.safebrowsingSwitch.isChecked());
            }
        });
        binding.safebrowsingSwitch.setChecked(flagSafeBrowser);

        flagFileAccess = getData("safeurl.FileAccess",false);
        binding.fileaccessSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData("safeurl.FileAccess", binding.fileaccessSwitch.isChecked());
            }
        });
        binding.fileaccessSwitch.setChecked(flagFileAccess);

        flagJavascript = getData("safeurl.Javascript",false);
        binding.javascriptSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData("safeurl.Javascript", binding.javascriptSwitch.isChecked());
            }
        });
        binding.javascriptSwitch.setChecked(flagJavascript);

        flagGeolocationData = getData("safeurl.GeolocationData",false);
        binding.geolocationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData("safeurl.GeolocationData", binding.geolocationSwitch.isChecked());
            }
        });
        binding.geolocationSwitch.setChecked(flagGeolocationData);
    }


    private Boolean getData(String key, Boolean def_value) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(key, def_value);
    }


    private void setData(String key, Boolean value) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(key, value).apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
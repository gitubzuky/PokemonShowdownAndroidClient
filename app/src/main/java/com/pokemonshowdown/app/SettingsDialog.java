package com.pokemonshowdown.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.pokemonshowdown.data.Onboarding;

public class SettingsDialog extends DialogFragment {
    public static final String STAG = SettingsDialog.class.getName();

    public SettingsDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_settings, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Switch languageSwitch = (Switch) view.findViewById(R.id.chinese_switch);
        languageSwitch.setChecked(Onboarding.get(getActivity()).isChineseEnable());
        languageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Onboarding.get(SettingsDialog.this.getActivity())
                        .setChineseEnable(isChecked);
            }
        });

        Switch animationSwitch = (Switch) view.findViewById(R.id.animation_switch);
        animationSwitch.setChecked(Onboarding.get(getActivity()).isAnimation());
        animationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Onboarding.get(SettingsDialog.this.getActivity())
                        .setAnimation(isChecked);
            }
        });

        Switch advertiseSwitch = (Switch) view.findViewById(R.id.advertise_switch);
        advertiseSwitch.setChecked(Onboarding.get(getActivity()).isAdvertising());
        advertiseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Onboarding.get(SettingsDialog.this.getActivity())
                        .setAdvertising(isChecked);
            }
        });

        Switch bugreportSwitch = (Switch) view.findViewById(R.id.bugreport_switch);
        bugreportSwitch.setChecked(Onboarding.get(getActivity()).isBugReporting());
        bugreportSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Onboarding.get(SettingsDialog.this.getActivity())
                        .setBugReporting(isChecked);
            }
        });


        return view;
    }
}
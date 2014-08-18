/*
 * This file is a part of Mame Launcher.
 * Mame Launcher is a fork of Michael Howell's Null-Launcher
 * Copyright 2014 Ryan Bailey <rbwaredev@gmail.com>
 *
 * Null-Launcher
 * Copyright 2013 Michael Howell <michael@notriddle.com>
 *
 * Mame Launcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Null Launcher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mame Launcher. If not, see <http://www.gnu.org/licenses/>.
 */

package com.rbware.mamelauncher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Settings extends Activity implements View.OnClickListener {

    private PackageManager mPackageManager;
    private ArrayList<ApplicationInfo> mApplications;
    private SharedPreferences mPreferences;
    private static final String PREFS_FILE = "appPreferences";

	@Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_settings);

        // Advanced Options
        findViewById(R.id.settings_button_reboot).setOnClickListener(this);
        findViewById(R.id.settings_button_launch_application).setOnClickListener(this);

        // Standard Options
        findViewById(R.id.settings_button_change_atari).setOnClickListener(this);
        findViewById(R.id.settings_button_change_gameboy).setOnClickListener(this);
        findViewById(R.id.settings_button_change_genesis).setOnClickListener(this);
        findViewById(R.id.settings_button_change_mame).setOnClickListener(this);
        findViewById(R.id.settings_button_change_n64).setOnClickListener(this);
        findViewById(R.id.settings_button_change_nintendo).setOnClickListener(this);
        findViewById(R.id.settings_button_change_snes).setOnClickListener(this);

        loadApplicationData();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.settings_button_reboot:
                showRebootDialog();
                break;
            case R.id.settings_button_launch_application:
                showApplicationListDialog();
                break;
            case R.id.settings_button_change_atari:
                showUpdateHandlerDialog("atari");
                break;
            case R.id.settings_button_change_gameboy:
                showUpdateHandlerDialog("gba");
                break;
            case R.id.settings_button_change_genesis:
                showUpdateHandlerDialog("genesis");
                break;
            case R.id.settings_button_change_mame:
                showUpdateHandlerDialog("mame");
                break;
            case R.id.settings_button_change_n64:
                showUpdateHandlerDialog("n64");
                break;
            case R.id.settings_button_change_nintendo:
                showUpdateHandlerDialog("nes");
                break;
            case R.id.settings_button_change_snes:
                showUpdateHandlerDialog("snes");
                break;
            default:
                // Nothing
                break;
        }
    }

    private void loadApplicationData(){
        mPackageManager = getPackageManager();

        List<ApplicationInfo> packages = mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        mApplications = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo packageInfo : packages) {
            if (!isSystemPackage(packageInfo))
                mApplications.add(packageInfo);
        }
    }

    private void showApplicationListDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                this);
        builderSingle.setIcon(R.drawable.ic_launcher);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_item);

        for (ApplicationInfo packageInfo : mApplications) {
                arrayAdapter.add(packageInfo.loadLabel(mPackageManager).toString());
        }
        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = getPackageManager().getLaunchIntentForPackage(mApplications.get(which).packageName);
                        Settings.this.startActivity(intent);
                    }
                });
        builderSingle.show();
    }

    private void showUpdateHandlerDialog(final String key){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                this);
        builderSingle.setIcon(R.drawable.ic_launcher);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_item);

        for (ApplicationInfo packageInfo : mApplications) {
            arrayAdapter.add(packageInfo.loadLabel(mPackageManager).toString());
        }
        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateApplicationHandler(key, mApplications.get(which).packageName);
                    }
                });
        builderSingle.show();
    }

    private void showRebootDialog(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        try {
                            Runtime.getRuntime().exec(new String[]{"su","-c","reboot now"});
                        } catch (Exception ex) {
                            Log.i("Settings", "Could not reboot", ex);
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to reboot the system?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    private void updateApplicationHandler(String key, String packageName){
        mPreferences = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        if (mPreferences != null){
            SharedPreferences.Editor edit = mPreferences.edit();
            edit.putString(key, packageName);
            edit.apply();
        }
    }

    private boolean isSystemPackage(ApplicationInfo pkgInfo) {

        // We still want this one
        if(pkgInfo.packageName.equals("com.amazon.tv.launcher"))
            return false;

        return ((pkgInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }
}

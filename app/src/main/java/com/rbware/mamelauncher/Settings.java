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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Settings extends Activity implements View.OnClickListener {

    private PackageManager mPackageManager;
    private ArrayList<ApplicationInfo> mApplications;

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
                //TODO Confirmation Dialog
                break;
            case R.id.settings_button_launch_application:
                showApplicationListDialog();
                break;
            // TODO - Other buttons
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

    private boolean isSystemPackage(ApplicationInfo pkgInfo) {

        // We still want this one
        if(pkgInfo.packageName.equals("com.amazon.tv.launcher"))
            return false;

        return ((pkgInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }
}

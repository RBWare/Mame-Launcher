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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ScaleGestureDetector;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class Home extends Activity implements View.OnClickListener {

    private SharedPreferences mPreferences;
    private static final String PREFS_FILE = "appPreferences";

    private String MAME_PACKAGE;
    private String NES_PACKAGE;
    private String SNES_PACKAGE;
    private String GENESIS_PACKAGE;
    private String ATARI_PACKAGE;
    private String GAMEBOY_PACKAGE;
    private String N64_PACKAGE;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_home);

        findViewById(R.id.main_button_mame).setOnClickListener(this);
        findViewById(R.id.main_button_nes).setOnClickListener(this);
        findViewById(R.id.main_button_snes).setOnClickListener(this);
        findViewById(R.id.main_button_genesis).setOnClickListener(this);
        findViewById(R.id.main_button_atari).setOnClickListener(this);
        findViewById(R.id.main_button_gameboy).setOnClickListener(this);
        findViewById(R.id.main_button_n64).setOnClickListener(this);
        findViewById(R.id.main_button_settings).setOnClickListener(this);

        if (isFirstRun())
            setupDefaultSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadEmulatorHandlers();
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){
            case R.id.main_button_mame:
                intent = getPackageManager().getLaunchIntentForPackage(MAME_PACKAGE);
                break;
            case R.id.main_button_nes:
                intent = getPackageManager().getLaunchIntentForPackage(NES_PACKAGE);
                break;
            case R.id.main_button_snes:
                intent = getPackageManager().getLaunchIntentForPackage(SNES_PACKAGE);
                break;
            case R.id.main_button_genesis:
                intent = getPackageManager().getLaunchIntentForPackage(GENESIS_PACKAGE);
                break;
            case R.id.main_button_atari:
                intent = getPackageManager().getLaunchIntentForPackage(ATARI_PACKAGE);
                break;
            case R.id.main_button_gameboy:
                intent = getPackageManager().getLaunchIntentForPackage(GAMEBOY_PACKAGE);
                break;
            case R.id.main_button_n64:
                intent = getPackageManager().getLaunchIntentForPackage(N64_PACKAGE);
                break;
            case R.id.main_button_settings:
                intent = new Intent(this, Settings.class);
                break;
            default:
                intent = null;
                break;
        }

        if (intent != null)
            startActivity(intent);
    }

    private boolean isFirstRun(){
        File prefsFileFile = new File("/data/data/" + getPackageName() +  "/shared_prefs/" + PREFS_FILE);
        return prefsFileFile.exists();
    }

    private void setupDefaultSettings(){
        mPreferences = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        if (mPreferences == null){
            SharedPreferences.Editor edit = mPreferences.edit();

            edit.putString("mame", "com.seleuco.mame4all");
            edit.putString("nes", "com.explusalpha.NesEmu");
            edit.putString("snes", "com.bubblezapgames.supergnes_lite");
            edit.putString("genesis", "com.explusalpha.MdEmu");
            edit.putString("atari", "com.explusalpha.A2600Emu");
            edit.putString("gba", "com.fastemulator.gbafree");
            edit.putString("n64", "paulscode.android.mupen64plusae");

            edit.apply();
        }

        loadEmulatorHandlers();
    }

    private void loadEmulatorHandlers(){
        if (mPreferences == null)
            mPreferences = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        MAME_PACKAGE = mPreferences.getString("mame", "com.seleuco.mame4all");
        NES_PACKAGE = mPreferences.getString("nes", "com.explusalpha.NesEmu");
        SNES_PACKAGE = mPreferences.getString("snes", "com.bubblezapgames.supergnes_lite");
        GENESIS_PACKAGE = mPreferences.getString("genesis", "com.explusalpha.MdEmu");
        ATARI_PACKAGE = mPreferences.getString("atari", "com.explusalpha.A2600Emu");
        GAMEBOY_PACKAGE = mPreferences.getString("gba", "com.fastemulator.gbafree");
        N64_PACKAGE = mPreferences.getString("n64", "paulscode.android.mupen64plusae");
    }
}

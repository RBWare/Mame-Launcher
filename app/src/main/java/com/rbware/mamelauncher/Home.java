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
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ScaleGestureDetector;
import android.widget.ImageButton;
import android.widget.Toast;

public class Home extends Activity implements View.OnClickListener {

    private ImageButton mArcadeButton;
    private ImageButton mNintendoButton;
    private ImageButton mSuperNintendoButton;
    private ImageButton mSegaGenesisButton;
    private ImageButton mAtariButton;
    private ImageButton mGameboyButton;
    private ImageButton mNintendo64Button;
    private ImageButton mSettingsButton;

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
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        // TODO - these all need to be accessed via sharedprefs for the exact package names, which can be changed via the settings menu

        switch(view.getId()){
            case R.id.main_button_mame:
                Toast.makeText(this, "Mame", Toast.LENGTH_SHORT).show();
                intent = getPackageManager().getLaunchIntentForPackage("com.package.address");
                break;
            case R.id.main_button_nes:
                Toast.makeText(this, "main_button_nes", Toast.LENGTH_SHORT).show();
                intent = getPackageManager().getLaunchIntentForPackage("com.package.address");

                break;
            case R.id.main_button_snes:
                intent = getPackageManager().getLaunchIntentForPackage("com.bubblezapgames.supergnes_lite");
                break;
            case R.id.main_button_genesis:
                Toast.makeText(this, "main_button_genesis", Toast.LENGTH_SHORT).show();
                intent = getPackageManager().getLaunchIntentForPackage("com.package.address");

                break;
            case R.id.main_button_atari:
                Toast.makeText(this, "main_button_atari", Toast.LENGTH_SHORT).show();
                intent = getPackageManager().getLaunchIntentForPackage("com.package.address");

                break;
            case R.id.main_button_gameboy:
                Toast.makeText(this, "main_button_gameboy", Toast.LENGTH_SHORT).show();
                intent = getPackageManager().getLaunchIntentForPackage("com.package.address");

                break;
            case R.id.main_button_n64:
                Toast.makeText(this, "main_button_n64", Toast.LENGTH_SHORT).show();
                intent = getPackageManager().getLaunchIntentForPackage("com.package.address");

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
}

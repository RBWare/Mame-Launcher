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
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class Settings extends Activity {

	@Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_settings);

    }
}

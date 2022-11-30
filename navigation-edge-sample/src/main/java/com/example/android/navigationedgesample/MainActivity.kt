/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationedgesample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.jailedbird.edgeutils.EdgeUtils
import cn.jailedbird.edgeutils.marginBottomSystemWindowInsets

/**
 * An activity that inflates a layout that has a NavHostFragment.
 */
class MainActivity : AppCompatActivity() {

    lateinit var layoutRoot: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EdgeUtils.setEdgeToEdge(this)
        setContentView(R.layout.activity_main)
        layoutRoot = findViewById(R.id.layout_root)

        layoutRoot.marginBottomSystemWindowInsets()

    }
}

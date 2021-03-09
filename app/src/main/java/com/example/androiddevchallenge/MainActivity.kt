/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyTheme {
                MyApp(viewModel)
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun MyApp(viewModel: MainViewModel = MainViewModel()) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Counter(viewModel.time)
            AnimatedVisibility(visible = !viewModel.counterVisible) {
                SetTime(viewModel)
            }
        }
    }
}

@Composable
fun SetTime(viewModel: MainViewModel = MainViewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 10.dp),
            value = viewModel.timeTextValue,
            onValueChange = {
                viewModel.timeTextValue = it
                if (it.text.isNotEmpty())
                    viewModel.setTime(it.text.toInt())
            },
            label = { Text("Set time to countdown in seconds") })

        Button(onClick = {
            if (viewModel.timeTextValue.text.isNotEmpty())
                viewModel.startTimer()
        }) {
            Text(text = "Start timer")
        }
    }
}

@Composable
fun Counter(time: Time) {
    MaterialTheme(typography = Typography(defaultFontFamily = FontFamily(Font(R.font.anton_regular))))
    {
        Text("$time", fontSize = 50.sp)
    }
}

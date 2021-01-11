/*
 * Copyright 2020 The Android Open Source Project
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

package androidx.compose.foundation.layout.demos

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.LayoutDirectionAmbient
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun RtlDemo() {
    Column(verticalArrangement = Arrangement.SpaceEvenly) {
        Text("TEXT", Modifier.align(Alignment.CenterHorizontally))
        testText()
        Text("ROW", Modifier.align(Alignment.CenterHorizontally))
        testRow()
        Text("ROW WITH LTR ROW IN BETWEEN", Modifier.align(Alignment.CenterHorizontally))
        testRow_modifier()
        Text("RELATIVE TO SIBLINGS", Modifier.align(Alignment.CenterHorizontally))
        testSiblings()
        Text(
            "PLACE WITH AUTO RTL SUPPORT IN CUSTOM LAYOUT",
            Modifier.align(Alignment.CenterHorizontally)
        )
        CustomLayout(true)
        Text(
            "PLACE WITHOUT RTL SUPPORT IN CUSTOM LAYOUT",
            Modifier.align(Alignment.CenterHorizontally)
        )
        CustomLayout(false)
        Text("WITH CONSTRAINTS", Modifier.align(Alignment.CenterHorizontally))
        Providers(LayoutDirectionAmbient provides LayoutDirection.Ltr) {
            LayoutWithConstraints("LD: set LTR via ambient")
        }
        Providers(LayoutDirectionAmbient provides LayoutDirection.Rtl) {
            LayoutWithConstraints("LD: set RTL via ambient")
        }
        LayoutWithConstraints(text = "LD: locale")
        Text("STACK EXAMPLE", Modifier.align(Alignment.CenterHorizontally))
        StackExample()
    }
}

@Composable
fun StackExample() {
    Box(Modifier.fillMaxSize().background(Color.LightGray)) {
        Box(Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
            Box(boxSize.then(Modifier.background(Color.Red))) {}
        }
        Box(Modifier.fillMaxSize().wrapContentHeight(Alignment.CenterVertically)) {
            Box(boxSize.then(Modifier.background(Color.Green))) {}
        }
        Box(Modifier.fillMaxSize().wrapContentSize(Alignment.BottomEnd)) {
            Box(boxSize.then(Modifier.background(Color.Blue))) {}
        }
    }
}

private val boxSize = Modifier.preferredSize(50.dp, 20.dp)
private val size = Modifier.preferredSize(10.dp, 10.dp)

@Composable
private fun testRow() {
    Row {
        Box(boxSize.background(color = Color.Red)) {}
        Box(boxSize.background(color = Color.Green)) {}
        Row {
            Box(boxSize.background(color = Color.Magenta)) {}
            Box(boxSize.background(color = Color.Yellow)) {}
            Box(boxSize.background(color = Color.Cyan)) {}
        }
        Box(boxSize.background(color = Color.Blue)) {}
    }
}

@Composable
private fun testRow_modifier() {
    Row {
        Box(boxSize.background(Color.Red)) {}
        Box(boxSize.background(Color.Green)) {}
        Providers(LayoutDirectionAmbient provides LayoutDirection.Ltr) {
            Row {
                Box(boxSize.background(Color.Magenta)) {}
                Box(boxSize.background(Color.Yellow)) {}
                Box(boxSize.background(Color.Cyan)) {}
            }
        }
        Box(boxSize.background(color = Color.Blue)) {}
    }
}

@Composable
private fun testText() {
    Column {
        Text("Text.")
        Text("Text filling max width.", Modifier.fillMaxWidth())
        Text("שלום!")
        Text("שלום!", Modifier.fillMaxWidth())
        Text("-->")
        Text("-->", Modifier.fillMaxWidth())
    }
}

@Composable
private fun testSiblings() {
    Column {
        Box(
            boxSize.background(color = Color.Red).alignBy { p -> p.width }
        ) {}
        Box(
            boxSize.background(color = Color.Green).alignBy { p -> p.width / 2 }
        ) {}
        Box(
            boxSize.background(color = Color.Blue).alignBy { p -> p.width / 4 }
        ) {}
    }
}

@Composable
private fun CustomLayout(rtlSupport: Boolean) {

}

@Composable
private fun LayoutWithConstraints(text: String) {
    WithConstraints {
        val w = maxWidth / 3
        val color = if (LayoutDirectionAmbient.current == LayoutDirection.Ltr) {
            Color.Red
        } else {
            Color.Magenta
        }
        Box(Modifier.preferredSize(w, 20.dp).background(color)) {
            Text(text, Modifier.align(Alignment.Center))
        }
    }
}
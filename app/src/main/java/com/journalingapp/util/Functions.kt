package com.journalingapp.util

import android.content.Context
import android.widget.Toast

fun toast(context: Context, str: String, howLong: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, str, howLong).show()
}
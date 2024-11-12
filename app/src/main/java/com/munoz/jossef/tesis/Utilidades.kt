package com.munoz.jossef.tesis

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Utilidades {

    @Throws(IOException::class)
    fun leerJson(context: Context, fileName: String): String {
        val reader = BufferedReader(InputStreamReader(context.assets.open(fileName), "UTF-8"))
        val content = StringBuilder()
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            content.append(line)
        }
        reader.close()
        return content.toString()
    }
}
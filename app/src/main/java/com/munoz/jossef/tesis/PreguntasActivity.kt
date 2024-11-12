package com.munoz.jossef.tesis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PreguntasActivity : AppCompatActivity() {
    private lateinit var txt_NumPregunta: TextView
    private lateinit var txt_Pregunta: TextView
    private lateinit var rdb_Opcion1: RadioButton
    private lateinit var rdb_Opcion2: RadioButton
    private lateinit var rdb_Opcion3: RadioButton
    private lateinit var btn_Siguiente: Button
    private lateinit var btn_Volver: Button

    private var contador = 1
    private var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas)

        // Asigna los elementos del layout a las variables
        txt_NumPregunta = findViewById(R.id.txt_NumPregunta)
        txt_Pregunta = findViewById(R.id.txt_Pregunta)
        rdb_Opcion1 = findViewById(R.id.rdb_Opcion1)
        rdb_Opcion2 = findViewById(R.id.rdb_Opcion2)
        rdb_Opcion3 = findViewById(R.id.rdb_Opcion3)
        btn_Siguiente = findViewById(R.id.btn_Siguiente)
        btn_Volver = findViewById(R.id.btn_Volver)

        // Llenar datos de la primera pregunta y deshabilitar el botón Volver en la primera pregunta
        llenarDatos(contador)
        actualizarEstadoBotonVolver()

        btn_Siguiente.setOnClickListener {
            if (rdb_Opcion1.isChecked || rdb_Opcion2.isChecked || rdb_Opcion3.isChecked) {
                check = true
            } else {
                Toast.makeText(applicationContext, "Selecciona una respuesta", Toast.LENGTH_SHORT).show()
                check = false
            }

            if (check) {
                if (contador < 5) {
                    contador++
                    llenarDatos(contador)
                    actualizarEstadoBotonVolver()
                } else {
                    mostrarMensajeFinal()
                }
            }
        }

        btn_Volver.setOnClickListener {
            if (contador > 1) {
                contador--
                llenarDatos(contador)
                actualizarEstadoBotonVolver()
            }
        }
    }

    private fun llenarDatos(numeroPregunta: Int) {
        try {
            val jsonFileContent = Utilidades().leerJson(applicationContext, "preguntas.json")
            val jsonObject = JSONObject(jsonFileContent)
            val jsonArray = jsonObject.getJSONArray("arrayPreguntas")

            for (i in 0 until jsonArray.length()) {
                val preguntaJson = jsonArray.getJSONObject(i)
                val idPregunta = preguntaJson.getInt("id_pregunta")

                if (idPregunta == numeroPregunta) {
                    val pregunta = preguntaJson.getString("Pregunta")
                    val respuesta = preguntaJson.getString("respuesta")
                    val alternativa1 = preguntaJson.getString("alternativa1")
                    val alternativa2 = preguntaJson.getString("alternativa2")

                    txt_NumPregunta.text = "Pregunta #$idPregunta"
                    txt_Pregunta.text = pregunta

                    val opciones = mutableListOf(alternativa1, alternativa2, respuesta)
                    opciones.shuffle()

                    rdb_Opcion1.text = opciones[0]
                    rdb_Opcion2.text = opciones[1]
                    rdb_Opcion3.text = opciones[2]
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun mostrarMensajeFinal() {
        txt_NumPregunta.text = "Cuestionario completado"
        txt_Pregunta.text = "Gracias por responder las preguntas."

        rdb_Opcion1.visibility = View.INVISIBLE
        rdb_Opcion2.visibility = View.INVISIBLE
        rdb_Opcion3.visibility = View.INVISIBLE
        btn_Siguiente.visibility = View.INVISIBLE
        btn_Volver.visibility = View.INVISIBLE

        btn_Siguiente.postDelayed({
            val intent = Intent(this, contenedor_fragment_Activity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    private fun actualizarEstadoBotonVolver() {
        btn_Volver.isEnabled = contador > 1
    }
}
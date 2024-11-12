package com.munoz.jossef.tesis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.munoz.jossef.tesis.databinding.FragmentPreguntasBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PreguntasFragment : Fragment() {

    private var _binding: FragmentPreguntasBinding? = null
    private val binding get() = _binding!!

    private var contador = 1
    private var check = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreguntasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llenarDatos(contador)
        actualizarEstadoBotonVolver()

        // Configurar el botón Siguiente
        binding.btnSiguiente.setOnClickListener {
            if (binding.rdbOpcion1.isChecked || binding.rdbOpcion2.isChecked || binding.rdbOpcion3.isChecked) {
                check = true
            } else {
                Toast.makeText(requireContext(), "Selecciona una respuesta", Toast.LENGTH_SHORT).show()
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

        // Configurar el botón Volver
        binding.btnVolver.setOnClickListener {
            if (contador > 1) {
                contador--
                llenarDatos(contador)
                actualizarEstadoBotonVolver()
            }
        }
    }

    private fun llenarDatos(numeroPregunta: Int) {
        try {
            val jsonFileContent = Utilidades().leerJson(requireContext(), "preguntas.json")
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

                    binding.txtNumPregunta.text = "Pregunta #$idPregunta"
                    binding.txtPregunta.text = pregunta

                    val opciones = mutableListOf(alternativa1, alternativa2, respuesta)
                    opciones.shuffle()

                    binding.rdbOpcion1.text = opciones[0]
                    binding.rdbOpcion2.text = opciones[1]
                    binding.rdbOpcion3.text = opciones[2]
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
        // Cambia el texto para agradecer la respuesta
        binding.txtNumPregunta.text = "Cuestionario completado"
        binding.txtPregunta.text = "Gracias por responder las preguntas de esta semana."

        // Oculta las opciones de respuesta y los botones
        binding.rdbOpcion1.visibility = View.INVISIBLE
        binding.rdbOpcion2.visibility = View.INVISIBLE
        binding.rdbOpcion3.visibility = View.INVISIBLE
        binding.btnSiguiente.visibility = View.INVISIBLE
        binding.btnVolver.visibility = View.INVISIBLE
    }

    private fun actualizarEstadoBotonVolver() {
        binding.btnVolver.isEnabled = contador > 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
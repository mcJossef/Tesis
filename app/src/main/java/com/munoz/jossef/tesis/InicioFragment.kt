package com.munoz.jossef.tesis

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.munoz.jossef.tesis.databinding.FragmentInicioBinding
import com.munoz.jossef.tesis.databinding.ItemExerciseBinding
class InicioFragment : Fragment() {
    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDoctorChips()
        setupRecyclerView()
        loadExercises()
    }

    private fun setupDoctorChips() {
        val doctors = listOf("Oscar", "Jimena", "Jheysin")
        doctors.forEach { doctorName ->
            val chip = Chip(requireContext()).apply {
                text = doctorName
                isCheckable = true
                setTextColor(Color.WHITE)
                chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.chip_background)
                )
            }
            binding.doctorChipGroup.addView(chip)
        }
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter()
        binding.exercisesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseAdapter
        }
    }

    private fun loadExercises() {
        val exercises = listOf(
            Exercise(
                doctorName = "Dr. Oscar López",
                title = "Yoga for Relaxation",
                description = "Practice yoga daily to improve flexibility and mental relaxation.",
                method = "Metodo de relajacion avanzado"
            ),
            Exercise(
                doctorName = "Dra. Jimena García",
                title = "Meditation Basics",
                description = "Meditate for 10 minutes each day to reduce stress and improve focus.",
                method = "Metodo de meditacion consciente"
            ),
            Exercise(
                doctorName = "Dr. Jheysin Martínez",
                title = "Deep Breathing Exercises",
                description = "Use breathing techniques to calm the mind and reduce anxiety.",
                method = "Metodo de respiracion profunda"
            ),
            Exercise(
                doctorName = "Dr. Ana Torres",
                title = "Stretching Routine",
                description = "Stretch daily to improve blood flow and muscle flexibility.",
                method = "Metodo de estiramiento diario"
            ),
            Exercise(
                doctorName = "Dr. Carlos Méndez",
                title = "Mindfulness Practice",
                description = "Practice mindfulness to stay present and focused throughout the day.",
                method = "Metodo de atencion plena"
            )
        )
        exerciseAdapter.submitList(exercises)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Clase Exercise
    data class Exercise(
        val doctorName: String,
        val title: String,
        val description: String,
        val method: String
    )

    // Clase ExerciseAdapter
    inner class ExerciseAdapter : ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(
        object : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) =
                oldItem == newItem
            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) =
                oldItem == newItem
        }
    ) {
        inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(exercise: Exercise) {
                binding.apply {

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
            return ExerciseViewHolder(
                ItemExerciseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }
}
package com.munoz.jossef.tesis

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.munoz.jossef.tesis.databinding.FragmentFavoritoBinding
import com.munoz.jossef.tesis.databinding.ItemExerciseBinding


class FavoritoFragment : Fragment() {
    private var _binding: FragmentFavoritoBinding?=null
    private val binding get() = _binding!!
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadExercises()
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
                doctorName = "Doctor 1",
                title = "Exercise",
                description = "Get some exercise in each day to get your body moving",
                method = "Metodo de relajacion 1"
            ),
            Exercise(
                doctorName = "Doctor 2",
                title = "Exercise",
                description = "Get some exercise in each day to get your body moving",
                method = "Metodo de relajacion 1"
            ),
            Exercise(
                doctorName = "Doctor 3",
                title = "Exercise",
                description = "Get some exercise in each day to get your body moving",
                method = "Metodo de relajacion 1"
            ),
            Exercise(
                doctorName = "Doctor 4",
                title = "Exercise",
                description = "Get some exercise in each day to get your body moving",
                method = "Metodo de relajacion 1"
            ),
            Exercise(
                doctorName = "Doctor 5",
                title = "Exercise",
                description = "Get some exercise in each day to get your body moving",
                method = "Metodo de relajacion 1"
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
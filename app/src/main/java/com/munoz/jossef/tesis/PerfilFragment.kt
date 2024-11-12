package com.munoz.jossef.tesis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.munoz.jossef.tesis.databinding.FragmentPerfilBinding
import de.hdodenhof.circleimageview.CircleImageView

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileImage: CircleImageView
    private lateinit var profileName: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root

        profileImage = binding.profileImage
        profileName = binding.nameText

        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            Glide.with(this)
                .load(it.photoUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(profileImage)

            loadUserNameFromFirestore(it)
        }

        binding.signOutButton.setOnClickListener {
            signOut()
        }

        return view
    }

    private fun loadUserNameFromFirestore(user: FirebaseUser) {
        val db = Firebase.firestore
        val userDocRef = db.collection("users").document(user.uid)
        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val nombre = document.getString("nombre")
                    val apellido = document.getString("apellido")
                    if (nombre != null && apellido != null) {
                        profileName.text = "$nombre $apellido"
                    } else {
                        Log.d("Firestore", "Campos de nombre o apellido están vacíos")
                        profileName.text = "Usuario"
                    }
                } else {
                    Log.d("Firestore", "No existe el documento en Firestore")
                    profileName.text = "Usuario"
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "Error al obtener el documento: ", exception)
                profileName.text = "Usuario"
            }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

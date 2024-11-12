package com.munoz.jossef.tesis

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth


fun Context.logout(){
    FirebaseAuth.getInstance().signOut()
    val intent = Intent(this, PerfilFragment::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    }
    startActivity(intent)
}
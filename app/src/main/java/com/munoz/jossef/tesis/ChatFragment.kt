package com.munoz.jossef.tesis

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class ChatFragment : Fragment() {
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val composeView = view.findViewById<ComposeView>(android.R.id.content)
        composeView.setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Chatpage(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = chatViewModel
                )
            }
        }
    }
}

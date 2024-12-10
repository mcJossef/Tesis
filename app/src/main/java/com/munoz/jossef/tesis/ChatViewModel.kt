package com.munoz.jossef.tesis

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val messageList: MutableList<MessageModel> by lazy {
        mutableListOf<MessageModel>()
    }

    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constants.apiKey
    )

    fun sendMessage(question: String) {
        viewModelScope.launch {
            try{
                val chat = generativeModel.startChat(
                    history = messageList.map { it.message
                        content(it.role) { text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(question, "user"))

                val response = chat.sendMessage(question)
                messageList.add(MessageModel(response.text.toString(), "model"))
            }catch (e: Exception){
                messageList.add(MessageModel("Error : "+e.message.toString(),"model"))
            }
        }
    }
}

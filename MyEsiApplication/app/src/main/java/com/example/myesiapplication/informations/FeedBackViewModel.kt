package com.example.myesiapplication.informations

import androidx.lifecycle.ViewModel

class FeedbackViewModel : ViewModel() {
    var feedbackText: String = ""
    var emailText: String = ""

    fun sendFeedback(onComplete: () -> Unit) {
        println("Feedback sent: $feedbackText")
        if (emailText.isNotEmpty()) {
            println("Email: $emailText")
        }
        onComplete()
    }
}

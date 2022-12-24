package com.example.socialmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    var email = ""
    var password = ""
    var loginData = MutableLiveData<String>()

    fun login() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                loginData.value = it.toString()
            }.addOnFailureListener {
                loginData!!.value = null
            }
    }
}
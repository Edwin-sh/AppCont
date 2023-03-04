package com.example.appcont.controller

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.appcont.MainActivity
import com.example.appcont.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import lombok.*
import java.io.Serializable

@Data
@AllArgsConstructor
class UserController(
    @NonNull var firebaseAuth: FirebaseAuth,
    @NonNull var firebaseFirestore: FirebaseFirestore,
    @NonNull var activity: Activity
) {
    private val userRef =
        firebaseFirestore.collection("users").document(firebaseAuth.currentUser!!.email!!)

    fun createUser() {
        userRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                userRef.set(hashMapOf<String, String>()).addOnSuccessListener {
                    Log.d("User Controller", "Se creó el documento")
                }.addOnFailureListener { e ->
                    Log.e("User Controller", "Ocurrió un error al crear el documento", e)
                }
            } else {
                Log.w("User Controller", "Ya existía el documento")
            }
        }
    }

    fun registreUser() {

    }

    fun getUser(): User? {
        var user: User? = null
        userRef.get().addOnSuccessListener { document ->
            if (document.data != null && document.data!!.isNotEmpty()) {
                user = User(
                    document.id,
                    document["name"].toString(),
                    document["lastname"].toString(),
                    document["phoneNumber"].toString(),
                    document["identificationNumber"].toString()
                )
            } else {
                Log.w("User Controller", "No tiene datos")
            }
        }.addOnFailureListener {
            Log.e("User Controller", "Error al obtener User")
        }
        return user
    }

    fun updateUser() {

    }

    fun signOut() {
        firebaseAuth!!.signOut()
        GoogleSignIn.getClient(activity, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}
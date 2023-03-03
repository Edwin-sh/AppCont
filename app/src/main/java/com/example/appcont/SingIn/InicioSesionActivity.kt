package com.example.appcont.SingIn

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appcont.MainActivity
import com.example.appcont.databinding.ActivityInicioSesionBinding
import com.example.appcont.model.UserActive
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.io.Serializable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class InicioSesionActivity : AppCompatActivity() {
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var binding: ActivityInicioSesionBinding
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true
    private lateinit var auth:FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var userMagnement:UserMagnement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)

        setContentView(binding.root)
        userMagnement= UserMagnement(this, Firebase.auth)
        auth= userMagnement.firebaseAuth!!
        googleSignInClient=userMagnement.googleSignInClient!!

       if (auth.currentUser != null) {
            showPantallaInicial()
        }
        binding.signInAppCompatButton.setOnClickListener() {
            val con = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = con.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                signIn()
            } else {
                Toast.makeText(this, "No estás conectado a internet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("succes", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("error", "Google sign in failed", e)
            }
            userMagnement=UserMagnement(this, Firebase.auth)
            val userActive: UserActive? = userMagnement.obtieneUsuario(this)
            if (userActive!=null){
                showPantallaInicial()
            }else{
                userMagnement.userActive=UserActive(userMagnement.firebaseUser!!.email!!)
                showPantallaSignUp()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001)
    }

    private fun updateUI(user: FirebaseUser?) {
        val userActive: UserActive? = userMagnement.obtieneUsuario(this)
        if (userActive!=null){
            showPantallaInicial()
        }else{
            userMagnement.userActive=UserActive(userMagnement.firebaseUser!!.email!!)
            showPantallaSignUp()
        }
    }

    private fun showPantallaInicial() {
        startActivity(Intent(this, MainActivity::class.java).putExtra("user",userMagnement as Serializable))
        finish()
    }

    private fun showPantallaSignUp() {
        startActivity(Intent(this, SingUpActivity::class.java).putExtra("user",userMagnement as Serializable))
        finish()
    }
}
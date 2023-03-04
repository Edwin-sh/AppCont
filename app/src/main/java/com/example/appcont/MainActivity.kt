package com.example.appcont


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appcont.SingIn.InicioSesionActivity
import com.example.appcont.SingIn.UserMagnement
import com.example.appcont.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding
    private lateinit var userMagnement: UserMagnement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.mibarra))
        var userMagnement:UserMagnement=UserMagnement(this, Firebase.auth)
        auth = userMagnement.firebaseAuth

        //googleSignInClient = userMagnement.googleSignInClient!!

        val tv=findViewById<TextView>(R.id.tvcorregoogle)
        tv.text=googleSignInClient.toString()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.nav_View)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragContainer) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.nav_Cultivos, R.id.nav_Trabajadores), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

        val vt=findViewById<Button>(R.id.btnCierraSesion)
        vt.setOnClickListener(){
            try {
                Firebase.auth.signOut()
                GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
                Log.d("SingUpActivity", "Se cerró sesión")
            }catch (e:Exception){
                Log.e("SingUpActivity", "Error cerrando sesión",e)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragContainer) as NavHostFragment
        val navController = navHostFragment.navController
        var nombre = findViewById<TextView>(R.id.tvcombreusu)
        var correo = findViewById<TextView>(R.id.tvcorreo)

        nombre.text = auth.currentUser?.displayName
        correo.text = auth.currentUser?.email
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
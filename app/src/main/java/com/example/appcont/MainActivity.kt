package com.example.appcont


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appcont.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
   lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.mibarra))

        val drawerLayout:DrawerLayout=findViewById(R.id.drawerLayout)
        val navigationView:NavigationView=findViewById(R.id.nav_View)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragContainer) as NavHostFragment
        val navController=navHostFragment.navController

        appBarConfiguration=AppBarConfiguration(setOf(R.id.nav_Cultivos, R.id.nav_Trabajadores),drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment:NavHostFragment=supportFragmentManager.findFragmentById(R.id.fragContainer) as NavHostFragment
        val navController=navHostFragment.navController
        return navController.navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()
    }
}
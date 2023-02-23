package com.example.appcont


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appcont.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db:FirebaseFirestore = FirebaseFirestore.getInstance()

        /*binding.btnConsultar.setOnClickListener{
            var data=""
            db.collection("Cultivos")
                .get().addOnSuccessListener { res->
                    for (documento in res){
                        data += "${documento.id} : ${documento.data} \n"
                    }
                    binding.tvNombre.text=data
                }
                .addOnFailureListener{ error->
                    binding.tvNombre.text="error de conexión"
                }
        }*/
    }
}
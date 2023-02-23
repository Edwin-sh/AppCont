package com.example.appcont

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class tareasFragment:Fragment() {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnConsultar.setOnClickListener{
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
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cultivos_init, container, false)
    }
}
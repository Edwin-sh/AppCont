package com.example.appcont.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.appcont.Classes.Cultivo
import com.example.appcont.R
import com.example.appcont.adapter.MyItemRecyclerViewAdapter
import com.example.appcont.databinding.FragmentCultivoInitBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A fragment representing a list of Items.
 */
class InitCultivosFragment : Fragment() {

    private var columnCount = 2
    private lateinit var db: FirebaseFirestore
    private lateinit var lista: MutableList<Cultivo>

    private lateinit var binding: FragmentCultivoInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cultivo_init, container, false)
        binding = FragmentCultivoInitBinding.bind(view)


        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                llenarRecycler(context)
            }
        }
        return view
    }

    fun llenarRecycler(context: Context) {
        lista = ArrayList()
        db.collection("Cultivos")
            .get().addOnSuccessListener { res ->
                for (documento in res) {
                    val culItem = documento.toObject(Cultivo::class.java)
                    culItem.idCultivo=documento.id
                    culItem.nombre=documento["nombre"].toString()
                    lista.add(culItem)
                }
                val c=Cultivo()
                c.nombre="uno"
                lista.add(c)
                binding.recyclerCultivosInit.adapter = MyItemRecyclerViewAdapter(lista.toList())


            }
            .addOnFailureListener { error ->
                Toast.makeText(this.context, "error de conexion", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            InitCultivosFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
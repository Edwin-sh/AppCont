package com.example.appcont.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.appcont.R
import com.example.appcont.databinding.FragmentInicialCultivosBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InicialCultivosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicialCultivosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentInicialCultivosBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_inicial_cultivos, container, false)
        binding = FragmentInicialCultivosBinding.bind(view)
        binding.btnConsultar.setOnClickListener {
            binding.tvNombre.text = "Presiono"
        }

        var fragmentManager: FragmentManager = childFragmentManager
        //var frag1: Fragment = fragmentManager.findFragmentById(R.id.recyclerCOntainerCultivosInit)!!


        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(
                R.id.recyclerCOntainerCultivosInit,
                InitCultivosFragment::class.java,
                null,
                "Tarea"
            )
            .commit()


        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InicialCultivosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InicialCultivosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
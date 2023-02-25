package com.example.appcont.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.appcont.Classes.Cultivo
import com.example.appcont.databinding.FragmentCultivoInitBinding

import com.example.appcont.placeholder.PlaceholderContent.PlaceholderItem
import com.example.appcont.databinding.FragmentItemCultivoBinding
import kotlin.coroutines.coroutineContext

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(private val values: List<Cultivo>) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemCultivoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ct=values[position]
        holder.contentView.text = ct.nombre
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemCultivoBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.textname

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}
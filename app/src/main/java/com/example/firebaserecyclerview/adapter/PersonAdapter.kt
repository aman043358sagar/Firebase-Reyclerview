package com.example.firebaserecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserecyclerview.databinding.LayoutPersonBinding
import com.example.firebaserecyclerview.model.Person
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class PersonAdapter(options: FirebaseRecyclerOptions<Person?>) :
    FirebaseRecyclerAdapter<Person, PersonAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, person: Person) {
        holder.bind(position, person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutPersonBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    inner class ViewHolder(private val binding: LayoutPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, person: Person) {
            binding.tvName.text = person.name
            binding.tvAge.text = person.age
            binding.tvOccupation.text = person.occupation
            binding.ivDelete.setOnClickListener {
                getRef(position).removeValue()
            }
        }

    }
}
package com.example.firebaserecyclerview

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaserecyclerview.adapter.PersonAdapter
import com.example.firebaserecyclerview.databinding.ActivityMainBinding
import com.example.firebaserecyclerview.databinding.LayoutAddPersonBinding
import com.example.firebaserecyclerview.model.Person
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadRecycleView()

        binding.btnAdd.setOnClickListener {
            showAddPersonDialog()
        }

    }

    private fun showAddPersonDialog() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        val binding = LayoutAddPersonBinding.inflate(layoutInflater)
        alertDialog.setTitle("Person Detail")
        alertDialog.setView(binding.root)

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Save") { dialogInterface, which ->
            val person = Person(
                binding.etName.text.toString(),
                binding.etAge.text.toString(),
                binding.etOccupation.text.toString()
            )

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("person")

            myRef.push().setValue(person)
        }

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel") { dialogInterface, which ->
            alertDialog.dismiss()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun loadRecycleView() {
        val query: Query = FirebaseDatabase.getInstance()
            .reference
            .child("person")

        val options = FirebaseRecyclerOptions.Builder<Person>()
            .setQuery(query, Person::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = PersonAdapter(options)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
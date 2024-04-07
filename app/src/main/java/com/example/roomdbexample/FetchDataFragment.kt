package com.example.roomdbexample

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FetchDataFragment : Fragment() {
    private lateinit var contactDao: ContactDao
    private lateinit var recyclerView: RecyclerView
    private var contacts: List<Contact> = emptyList() // Initialize with an empty list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fetch_data, container, false)




        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = ContactAdapter(emptyList()) // Initially empty list
        recyclerView.adapter = adapter


        val myImageButton = view.findViewById<ImageButton>(R.id.myImageButton)
        myImageButton.setOnClickListener {
            showSortingDialog()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactDao = App.database.contactDao()
        contactDao.getAllContacts().observe(viewLifecycleOwner, Observer { contacts ->
            recyclerView.adapter = ContactAdapter(contacts)
        })
    }


    private fun showSortingDialog() {
        val dialogView = layoutInflater.inflate(R.layout.sort_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.sortFirstName).setOnClickListener {
            // Sort by first name

            sortContactsByName()
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.sortLastName).setOnClickListener {
            // Sort by last name
            sortContactsByLastName()
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.sortPhoneNumber).setOnClickListener {
            // Sort by phone number
            sortContactsByPhoneNumber()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun sortContactsByName() {
        val adapter = recyclerView.adapter as? ContactAdapter
        adapter?.let {
            val sortedContacts = contacts.sortedBy { it.firstName }
            it.updateContacts(sortedContacts)
        }
    }

    private fun sortContactsByLastName() {
        val adapter = recyclerView.adapter as? ContactAdapter
        adapter?.let {
            val sortedContacts = contacts.sortedBy { it.lastName }
            it.updateContacts(sortedContacts)
        }
    }

    private fun sortContactsByPhoneNumber() {
        val adapter = recyclerView.adapter as? ContactAdapter
        adapter?.let {
            val sortedContacts = contacts.sortedBy { it.phoneNumber }
            it.updateContacts(sortedContacts)
        }
    }



}

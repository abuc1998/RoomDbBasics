package com.example.roomdbexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddDataFragment : Fragment() {
    private lateinit var contactDao: ContactDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactDao = App.database.contactDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_data, container, false)

        val firstNameEditText = view.findViewById<EditText>(R.id.firstNameEditText)
        val lastNameEditText = view.findViewById<EditText>(R.id.lastNameEditText)
        val phoneEditText = view.findViewById<EditText>(R.id.phoneEditText)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val phoneNumber = phoneEditText.text.toString()

            if (isValidForm(firstName, lastName, phoneNumber)) {
                val contact =
                    Contact(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber)
                insertContact(contact)
                Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
            } else {
                // Show error message or handle invalid input
                Toast.makeText(context, "Invalid input. Please check the form.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return view
    }

    private fun isValidForm(firstName: String, lastName: String, phoneNumber: String): Boolean {
        return firstName.isNotEmpty() && lastName.isNotEmpty() && isValidPhoneNumber(phoneNumber)
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // You can implement your own validation logic here
        // For example, check if the phone number is a valid format
        val pattern = Regex("\\d{10}") // Assumes phone number is 10 digits
        return pattern.matches(phoneNumber)
    }

    private fun insertContact(contact: Contact) {
        GlobalScope.launch {
            contactDao.insert(contact)

            // Show a toast message on the main thread
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Contact saved successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


package com.example.crud//package com.example.crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("User")

        val nameInput = findViewById<EditText>(R.id.username_input)
        val ageInput = findViewById<EditText>(R.id.Age_input)
        val submitButton = findViewById<Button>(R.id.submit_button)
        val clearButton = findViewById<Button>(R.id.clear_button)

        // Handle Submit Button Click
        submitButton.setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString()

            if (name.isEmpty() || age.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                val user = mapOf("name" to name, "age" to age)

                userRef.push().setValue(user).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Data saved to Firebase", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Handle Clear Button Click
        clearButton.setOnClickListener {
            nameInput.text.clear()
            ageInput.text.clear()
            Toast.makeText(this, "All fields cleared", Toast.LENGTH_SHORT).show()
        }
    }
}


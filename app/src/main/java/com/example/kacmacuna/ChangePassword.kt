package com.example.kacmacuna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kacmacuna.R.layout.activity_change_password
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_change_password)

        auth = FirebaseAuth.getInstance()

        btn_change_password.setOnClickListener {
            changePassword()
        }

        backBtn.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun changePassword() {

        if (et_current_password.text.isNotEmpty() &&
            et_new_password.text.isNotEmpty() &&
            et_confirm_password.text.isNotEmpty()
        ) {

            if (et_new_password.text.toString() == et_confirm_password.text.toString()) {

                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, et_current_password.text.toString())

                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Re-Authentication success.", Toast.LENGTH_SHORT).show()
                                user.updatePassword(et_new_password.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_SHORT).show()
                                            auth.signOut()
                                            startActivity(Intent(this, MainActivity::class.java))
                                            finish()
                                        }
                                    }

                            } else {
                                Toast.makeText(this, "Re-Authentication failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            } else {
                Toast.makeText(this, "Password mismatching.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
        }

    }
}

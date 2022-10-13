package com.optimum.optimumreport

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.google.gson.JsonObject
import com.optimum.optimumreport.databinding.ActivityLoginBinding
import com.optimum.optimumreport.interfaces.OnInternetCheckListener
import com.optimum.optimumreport.utility.Constants
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: CafePosViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this@LoginActivity)
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val checkLoginStatus = sharedPreferences.getBoolean(Constants.LOGIN_STATUS, false)
        if (checkLoginStatus) {
            Log.e("TAG", "LOGIN STATUS : " + "TRUE")
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        viewModel = ViewModelProvider(this@LoginActivity)[CafePosViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            val userId = binding.edtUserid.editText?.text.toString()
            val password = binding.edtPassword.editText?.text.toString()

            if (userId == "") {
                Utility.showToast(this@LoginActivity, "Please Enter UserId")
            } else if (password == "") {
                Utility.showToast(this@LoginActivity, "Please Enter Password")
            } else {

                 if (Utility.isAppOnLine(this@LoginActivity, object : OnInternetCheckListener {
                         override fun onInternetAvailable() {
                             loginRequest(userId, password)
                         }
                     }))
                     loginRequest(userId, password)
            }
        }
    }

    private fun loginRequest(userId: String, password: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", userId)
        jsonObject.addProperty("password", password)
        viewModel.loginRequest(jsonObject).observe(this@LoginActivity) { response ->

            when (response) {
                is NetworkResult.Success -> {
                    if (response.data!!.status) {
                        try {
                             saveDataInPreference(
                                 userId,
                                 password,
                                 response.data.data.userCode
                             )

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
                is NetworkResult.Error -> {
                    Utility.showToast(this@LoginActivity, response.data!!.message)
                 }
            }
        }
    }

    private fun saveDataInPreference(userId: String, password: String, userCode: Int) {
        val myEdit = sharedPreferences.edit()
        myEdit.putString(Constants.USERID, userId)
        myEdit.putString(Constants.PASSWORD, password)
        myEdit.putInt(Constants.USER_CODE, userCode)
        myEdit.putBoolean(Constants.LOGIN_STATUS, true)
        myEdit.apply()

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
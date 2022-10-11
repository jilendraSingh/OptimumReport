package com.optimum.optimumreport.utility

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.optimum.optimumreport.R
import com.optimum.optimumreport.interfaces.OnInternetCheckListener


class Utility {
    companion object{

        fun showToast(context: Context,message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
        fun isAppOnLine(context: Context, internetCheckListener: OnInternetCheckListener): Boolean {
            var isInternetAvailable = false
            if (isNetworkConnected(context)) {
                isInternetAvailable = true
                return isInternetAvailable
            } else {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.no_internet_connection_dialog)
                dialog.setCancelable(false)
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
                val button = dialog.findViewById<Button>(R.id.retry_button)
                button.setOnClickListener {
                    if (isNetworkConnected(context)) {
                        dialog.dismiss()
                        isInternetAvailable = true
                        internetCheckListener.onInternetAvailable()
                        return@setOnClickListener
                    }
                }
                try {
                    dialog.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return isInternetAvailable
            }
        }

        private fun isNetworkConnected(context: Context): Boolean {

            // register activity with the connectivity manager service
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // if the android version is equal to M
            // or greater we need to use the
            // NetworkCapabilities to check what type of
            // network has the internet connection
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Returns a Network object corresponding to
                // the currently active default data network.
                val network = connectivityManager.activeNetwork ?: return false

                // Representation of the capabilities of an active network.
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    // Indicates this network uses a Wi-Fi transport,
                    // or WiFi has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    // Indicates this network uses a Cellular transport. or
                    // Cellular has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    // else return false
                    else -> false
                }
            } else {
                // if the android version is below M
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }
    }
}
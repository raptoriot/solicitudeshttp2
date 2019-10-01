package com.example.solicitudeshttp

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

class Network{

companion object{

    fun hayREd(activity: AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkinfo=connectivityManager.activeNetworkInfo
        return  networkinfo !=null && networkinfo.isConnected
    }
}

}
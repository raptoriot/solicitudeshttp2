package com.example.solicitudeshttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    val bValidarRed=findViewById<Button>(R.id.bValidarRed)



    bValidarRed.setOnClickListener{
        if(Network.hayREd(this)){
            Toast.makeText(this,"Si hay red", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this,"Asegurate que exista la red a Internet",Toast.LENGTH_LONG).show()
        }
    }

    }
}

package com.example.solicitudeshttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.Toast
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    val bValidarRed=findViewById<Button>(R.id.bValidarRed)
        val bsolicitudHTTP=findViewById<Button>(R.id.bSolicitudHTTPGET)
        val bsolicitudHTTPPOST=findViewById<Button>(R.id.bSolicitudHTTPPOST)


    bValidarRed.setOnClickListener{
        if(Network.hayREd(this)){
            Toast.makeText(this,"Si hay red", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this,"Asegurate que exista la red a Internet",Toast.LENGTH_LONG).show()
        }
    }

        bsolicitudHTTP.setOnClickListener{

            if(Network.hayREd(this)){
                val mensaje:String?=descargarDatos("http://192.168.1.100:5002/store")
                Log.d("bSolicitudOnClick",""+mensaje)
                val json= JSONArray(mensaje)
                val jsonobject=json.getJSONObject(0)
                Log.d("nuevo",""+jsonobject)
                val items =jsonobject.getString("items")
                Log.d("nuevo2",""+items)
                val mercaderia= JSONArray(items)
                val jsonobject2=mercaderia.getJSONObject(0)
                val nombre1=jsonobject2.getString("name")
                Log.d("nombre",""+nombre1)





            }else{
                Toast.makeText(this,"Asegurate que exista la red a Internet",Toast.LENGTH_LONG).show()
            }

        }

        bsolicitudHTTPPOST.setOnClickListener {

            if(Network.hayREd(this)){
                val mensaje:String?=descargarDatos("http://192.168.1.100:5002/store")

            }else{
                Toast.makeText(this,"Asegurate que exista la red a Internet",Toast.LENGTH_LONG).show()
            }

        }

    }




@Throws(IOException::class)
    private fun descargarDatos(url:String):String?{

        val policy=StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var inputStream: InputStream? =null
        try{
            val url= URL(url)
            val conn=url.openConnection() as HttpURLConnection
            conn.requestMethod="GET"
            conn.connect()

            inputStream=conn.inputStream

//Aca covierte el inpuStream en String

            val sb=StringBuilder()
            var line:String?

            val br=BufferedReader(InputStreamReader(inputStream))
            line=br.readLine()

            while (line !=null){
                sb.append(line)
                line=br.readLine()
            }
            br.close()
            Log.i("mensaje",""+sb)
            return sb.toString()



        }finally {
            if(inputStream != null){
                inputStream.close()
            }

        }
    }

}

package com.example.solicitudeshttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.*
import org.json.JSONArray
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject





import java.io.*
import java.lang.Exception
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    val bValidarRed=findViewById<Button>(R.id.bValidarRed)
        val bsolicitudHTTP=findViewById<Button>(R.id.bSolicitudHTTPGET)
        val bsolicitudHTTPPOST=findViewById<Button>(R.id.bSolicitudHTTPPOST)
        val bsolicitudGetOkhttp=findViewById<Button>(R.id.bgetOkHTTP)
        val bpostok =findViewById<Button>(R.id.bpostokhttp)

        val bgetvolley=findViewById<Button>(R.id.bGetVolley)


        bgetvolley.setOnClickListener {
            if(Network.hayREd(this)){
                Log.d("app","si entro al botn")
                //solicitudHTTPVolley("http://192.168.1.100:5002/store")
                //sendPostRequest("mensaje","hola")



                val url = "http://192.168.1.100:5002/store"

                val texto="hola"
                // Post parameters
                // Form fields and values

                val params = HashMap<String,String>()
                params["name"] = "prueba2"
                params["item"] = "1234"
                val jsonObject = JSONObject(params as Map<*, *>)

                val request = JsonObjectRequest(Request.Method.POST,url,jsonObject,
                    Response.Listener { response ->
                        // Process the json


                    }, Response.ErrorListener{
                        // Error in request

                    })
                request.retryPolicy = DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    // 0 means no retry
                    0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                    1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                VolleySingleton.getInstance(this).addToRequestQueue(request)











            }else{
                Toast.makeText(this,"Asegurate que exista la red a Internet",Toast.LENGTH_LONG).show()
            }

        }



        bpostok.setOnClickListener {
            if(Network.hayREd(this)){
                Log.d("app","si entro al botn")


            }else{
                Toast.makeText(this,"Asegurate que exista la red a Internet",Toast.LENGTH_LONG).show()
            }

        }

        bsolicitudGetOkhttp.setOnClickListener {
            if(Network.hayREd(this)){
                solicitudHTTPOkHTTP("http://192.168.1.100:5002/store")

            }else{
                Toast.makeText(this,"Asegurate que exista la red a Internet",Toast.LENGTH_LONG).show()
            }
        }


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
//POST CON NORMAL
        bsolicitudHTTPPOST.setOnClickListener {

            if(Network.hayREd(this)){
                sendPostRequest("alvaro","1234")

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

    @Throws(IOException::class)
    private fun descargarDatosPOST(url:String,msj:String):String?{

        val policy=StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        var inputStream: InputStream? =null
        try{
            val url= URL(url)
            val dato=msj
            val conn=url.openConnection() as HttpURLConnection
            conn.requestMethod="POST"
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json")
            conn.connect()

            val outputStream = conn.getOutputStream()
            val writer = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
            writer.write(dato);
            writer.flush()
            writer.close()
            outputStream.close()


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
    //Metodo para okHttp


    private fun solicitudHTTPOkHTTP(url:String){
        val cliente =OkHttpClient()
        val solicitud=okhttp3.Request.Builder().url(url).build()

        cliente.newCall(solicitud).enqueue(object :okhttp3.Callback{

            override fun onFailure(call: Call?, e: IOException?){
                //lo que hace al error

            }

            override fun onResponse(call: Call?, response:okhttp3.Response){
                val resul=response.body().string()
                this@MainActivity.runOnUiThread {
                    try{
                        Log.d("solicitudHttpok",resul)
                    }catch(e:Exception){

                    }
                }

            }
        })

    }

    fun sendPostRequest(userName:String, password:String) {

        var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
        val mURL = URL("http://192.168.1.100:5002/store")

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "POST"

            val wr = OutputStreamWriter(getOutputStream());
            wr.write(reqParam);
            wr.flush();

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")
            }


        }
    }


//METODO GET PARA VOLLEY

    private fun solicitudHTTPVolley(url: String) {
        Log.d("app","si entro al botn5")
        val queue = Volley.newRequestQueue(this)

        val solicitud = StringRequest(Request.Method.GET, url,Response.Listener<String> {
                response ->
            Log.d("solicitudHTTPVolley", response)

            try{
                Log.d("solicitudVolley",response)


            }catch(e:Exception){
                Log.d("solicitudHTTPVolley", "entro al catch")
            }


        }, Response.ErrorListener {

        })
        queue.add(solicitud)
    }





}

package com.example.kotlin_httpurlconnection

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        //WebAPIオブジェクトの生成
        val api = Api()
        resultTextView.text = api.get("departments")

        getButton.setOnClickListener {
            resultTextView.text = api.get("departments")
        }
        postButton.setOnClickListener {
            val json = """
                {"ID":1,"Name":"abc","DepartmentLocations":[]}
            """.trimIndent()
            resultTextView.text = api.post("departments", json)
        }
        putButton.setOnClickListener {
            val json = """
                {"ID":10,"Name":"aaa","DepartmentLocations":[]}
            """.trimIndent()
            resultTextView.text = api.put("departments", 10, json)
        }
        deleteButton.setOnClickListener {
            resultTextView.text = api.delete("departments", 11)
        }

//        getButton.setOnClickListener {
//            val url = URL("http://192.168.11.7/api/departments")
//            val connection = url.openConnection() as HttpURLConnection
//            connection.requestMethod = "GET"
//            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
//                val br = BufferedReader(InputStreamReader(connection.inputStream))
//                val responseBody = br.use { it.readText() }
//                resultTextView.text = responseBody
//                Log.d("TAG", responseBody)
//            }
//        }
//
//        postButton.setOnClickListener {
//            val json = """
//                {"ID":1,"Name":"abc","DepartmentLocations":[]}
//            """.trimIndent()
//            val byteData = json.toByteArray()
//            val url = URL("http://192.168.11.7/api/departments")
//            val connection = url.openConnection() as HttpURLConnection
//            connection.requestMethod = "POST"
//            connection.doOutput = true
//            connection.setRequestProperty("Content-type", "application/json; charset=utf8")
////            val outputStream = connection.outputStream
////            outputStream.write(byteData)
////            outputStream.flush()
////            outputStream.close()
//
//            val bw = BufferedWriter(OutputStreamWriter(connection.outputStream, "UTF-8"))
//            bw.write(json)
//            bw.close()
//
//            if (connection.responseCode == HttpURLConnection.HTTP_CREATED) {
//                val br = BufferedReader(InputStreamReader(connection.inputStream))
//                val responseBody = br.use { it.readText() }
//                resultTextView.text = responseBody
//                Log.d("TAG", responseBody)
//            }
//        }
//
//        putButton.setOnClickListener {
//            val json = """
//                {"ID":10,"Name":"aaa","DepartmentLocations":[]}
//            """.trimIndent()
//            val url = URL("http://192.168.11.7/api/departments/10")
//            val connection = url.openConnection() as HttpURLConnection
//            connection.requestMethod = "PUT"
//            connection.doOutput = true
//            connection.setRequestProperty("Content-type", "application/json; charset=utf8")
//
//            val bw = BufferedWriter(OutputStreamWriter(connection.outputStream, "UTF-8"))
//            bw.write(json)
//            bw.close()
//
//            if (connection.responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
//                val br = BufferedReader(InputStreamReader(connection.inputStream))
//                val responseBody = br.use { it.readText() }
//                resultTextView.text = responseBody
//                Log.d("TAG", responseBody)
//            }
//        }
//
//        deleteButton.setOnClickListener {
//            val url = URL("http://192.168.11.7/api/departments/11")
//            val connection = url.openConnection() as HttpURLConnection
//            connection.requestMethod = "DELETE"
//
//            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
//                val br = BufferedReader(InputStreamReader(connection.inputStream))
//                val responseBody = br.use { it.readText() }
//                resultTextView.text = responseBody
//                Log.d("TAG", responseBody)
//            }
//        }

    }
}

package com.example.kotlin_httpurlconnection

import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class Api {
    //HttpUrlConnectionオブジェクトの生成
    //HttpUrlConnectionの共通設定はここで行う
    private fun connection(name: String): HttpURLConnection {
        //URLオブジェクトの生成
        val baseUrl = URL("http://192.168.11.71/api/$name")
        val connection = baseUrl.openConnection() as HttpURLConnection
        //接続タイムアウト。接続できなかったら、例外スロー
        connection.connectTimeout = 3000
        //POSTとPUTようにコンテンツタイプを指定
        connection.setRequestProperty("Content-type", "application/json; charset=utf8")
        return connection
    }

    //HttpURLConnectionオブジェクトを受け取り、WebAPIに接続後、応答結果を返す
    //PUTの応答は204(NO_CONTENT)であるため、応答ボディにデータが空
    private fun check(connection: HttpURLConnection): String {
        var responseBody = "FAILED"
        try {
            when (connection.responseCode) {
                //GET,POST,DELETEの正常結果
                HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED -> {
                    val br = BufferedReader(InputStreamReader(connection.inputStream))
                    responseBody = br.use { it.readText() }
                }
                //PUTの正常結果
                HttpURLConnection.HTTP_NO_CONTENT -> {
                    responseBody = "UPDATED"
                }
            }
            connection.disconnect()
            Log.d("TAG", responseBody)
        } catch (e: Exception) {
            //接続できなかった時、例外スローされる
            e.printStackTrace()
        } finally {
            connection.disconnect()
            return responseBody
        }
    }

    //GET用のメソッド
    fun get(name: String): String {
        val connection = connection(name)
        connection.requestMethod = "GET"
        return check(connection)
    }

    //POST用のメソッド
    fun post(name: String, json: String): String {
        val connection = connection(name)
        connection.requestMethod = "POST"
        try {
            //JSONデータの要求本文への書き込み
            BufferedWriter(OutputStreamWriter(connection.outputStream, "UTF-8")).use { it.write(json) }
        } catch (e: Exception) {
            //接続できなかった時、例外スローされる
            e.printStackTrace()
            return "FAILED"
        }
        return check(connection)
    }

    //PUT用のメソッド
    fun put(name: String, id: Int, json: String): String {
        val connection = connection("$name/$id")
        connection.requestMethod = "PUT"

        BufferedWriter(OutputStreamWriter(connection.outputStream, "UTF-8")).use { it.write(json) }
        try {
            //JSONデータの要求本文への書き込み
            BufferedWriter(OutputStreamWriter(connection.outputStream, "UTF-8")).use { it.write(json) }
        } catch (e: Exception) {
            //接続できなかった時、例外スローされる
            e.printStackTrace()
            return "FAILED"
        }
        return check(connection)
    }

    //DELETE用のメソッド
    fun delete(name: String, id: Int): String {
        val connection = connection("$name/$id")
        connection.requestMethod = "DELETE"
        return check(connection)
    }


}
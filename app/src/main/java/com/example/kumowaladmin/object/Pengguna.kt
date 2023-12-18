package com.example.kumowaladmin.`object`

import android.util.Log
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONException

object Pengguna {
    var id : String? = null
    var username : String? = null
    var nama : String? = null
    var alamat : String? = null
    var idAlamatPengiriman : String? = null
    var alamatPengiriman : String? = null
    var jenisAlamatPengiriman : String? = null

    fun getDetailFromDb(user_id : String, callback : (Boolean) -> Unit){
        val url = "https://kumowal.my.id/api_admin/user_get_detail.php?admin_id=$user_id"
        Log.d("url2",url)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val respon = response.getJSONObject(0)
                    id = respon.getString("admin_id")
                    username = respon.getString("username")
                    nama = respon.getString("name")
                    callback(true)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    callback(false)
                }
            },
            { error ->
                Log.d("err",error.toString())
                callback(false)
            })

        RQ.getRQ().add(jsonArrayRequest)
    }

    fun login (username : String, password : String, loading : ProgressBar, callback: (Boolean) -> Unit){

    }

    fun logout (){
        id = null
        username = null
        nama = null
        alamat = null
    }
}
package com.example.kumowaladmin.`object`

import android.view.View
import android.widget.LinearLayout

object StatusPesanan {

    fun getStatus(id : String) : String{
        var status = ""
        when (id){
            "1" -> {
                status = "Perlu di Konfirmasi"
            }
            "2" -> {
                status = "Perlu di Kirim"
            }
            "3" -> {
                status = "Dalam Pengiriman"
            }
            "4" -> {
                status = "Selesai"
            }
        }
        return status
    }

}
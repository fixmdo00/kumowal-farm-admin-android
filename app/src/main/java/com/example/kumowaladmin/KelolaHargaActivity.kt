package com.example.kumowaladmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.kumowaladmin.databinding.ActivityKelolaHargaBinding
import com.example.kumowaladmin.`object`.RQ
import java.text.NumberFormat
import java.util.Locale

class KelolaHargaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKelolaHargaBinding
    var harga_babi = ""
    var harga_ayam = ""
    var harga_telur = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaHargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ambilHarga("babi"){
            if (it.isNotBlank()){
                harga_babi = it
                binding.tvHargaBabi.text = "Rp. "+NumberFormat.getNumberInstance(Locale.getDefault())
                    .format(harga_babi.toInt())
            }
        }

        ambilHarga("ayam"){
            if (it.isNotBlank()){
                harga_ayam = it
                binding.tvHargaAyam.text = "Rp. "+NumberFormat.getNumberInstance(Locale.getDefault())
                    .format(harga_ayam.toInt())
            }
        }

        ambilHarga("telur"){
            if (it.isNotBlank()){
                harga_telur = it
                binding.tvHargaTelur.text = "Rp. "+NumberFormat.getNumberInstance(Locale.getDefault())
                    .format(harga_telur.toInt())
            }
        }


        binding.btnUbahBabi.setOnClickListener {
            val intent = Intent(this, DialogUbahHargaActivity::class.java)
            intent.putExtra("nama_produk","babi")
            startActivity(intent)
        }

        binding.btnUbahAyam.setOnClickListener {
            val intent = Intent(this, DialogUbahHargaActivity::class.java)
            intent.putExtra("nama_produk","ayam")
            startActivity(intent)
        }

        binding.btnUbahTelur.setOnClickListener {
            val intent = Intent(this, DialogUbahHargaActivity::class.java)
            intent.putExtra("nama_produk","telur")
            startActivity(intent)
        }


    }

    private fun ambilHarga(nama_produk : String, callback: (String) -> Unit) {
        binding.loadingProgressBar.visibility = View.VISIBLE
        val url = "https://kumowal.my.id/api/harga_get.php?item=$nama_produk"
        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener { response ->
                binding.loadingProgressBar.visibility = View.GONE
                val resp = response.trim()
                if (resp == "gagal"){
                    Toast.makeText(this, "Gagal mendapatkan harga", Toast.LENGTH_LONG).show()
                    callback("")
                } else {
                    callback(resp)
                }
            },
            Response.ErrorListener { error ->
                binding.loadingProgressBar.visibility = View.GONE
                Toast.makeText(this, "eroor", Toast.LENGTH_LONG).show()
                callback("")
            }){
        }
        RQ.getRQ().add(stringRequest)
    }
}
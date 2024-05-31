package com.example.kumowaladmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.kumowaladmin.databinding.ActivityDialogUbahHargaBinding
import com.example.kumowaladmin.`object`.RQ

class DialogUbahHargaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDialogUbahHargaBinding
    private lateinit var nama_produk : String
    private lateinit var harga : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogUbahHargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra("nama_produk") != null){
            nama_produk = intent.getStringExtra("nama_produk")!!
        }

        ambilHarga(nama_produk){
            when(it){
                true -> {
                    binding.itHarga.setText(harga)
                }
                false -> {
                    Toast.makeText(this, "Gagal mengambil data harga", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnSimpan.setOnClickListener {
            if(binding.itHarga.text.toString().isBlank()){
                Toast.makeText(this,"Tidak boleh kosong", Toast.LENGTH_LONG).show()
            } else {
                ubahHargaProduk(nama_produk, binding.itHarga.text.toString()) {
                    when (it) {
                        true -> {
                            val intent = Intent(this, KelolaHargaActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }

                        false -> {
                            Toast.makeText(this, "Gagal", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }

    private fun ubahHargaProduk(nama_produk: String, harga_baru: String, callback: (Boolean) -> Unit) {
        binding.loadingProgressBar.visibility = View.VISIBLE
        val url = "https://kumowal.my.id/api/harga_ubah.php?nama_produk=$nama_produk&harga_baru=$harga_baru"
        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener { response ->
                binding.loadingProgressBar.visibility = View.GONE
                val resp = response.trim()
                if (resp == "gagal"){
                    Toast.makeText(this, "Gagal mendapatkan harga", Toast.LENGTH_LONG).show()
                    callback(false)
                } else {
                    harga = resp
                    callback(true)
                }
            },
            Response.ErrorListener { error ->
                binding.loadingProgressBar.visibility = View.GONE
                Toast.makeText(this, "eroor", Toast.LENGTH_LONG).show()
                callback(false)
            }){
        }
        RQ.getRQ().add(stringRequest)
    }

    private fun ambilHarga(nama_produk : String, callback: (Boolean) -> Unit) {
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
                    callback(false)
                } else {
                    harga = resp
                    callback(true)
                }
            },
            Response.ErrorListener { error ->
                binding.loadingProgressBar.visibility = View.GONE
                Toast.makeText(this, "eroor", Toast.LENGTH_LONG).show()
                callback(false)
            }){
        }
        RQ.getRQ().add(stringRequest)
    }
}
package com.example.kumowaladmin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.kumowaladmin.DetailAkunActivity
import com.example.kumowaladmin.DetailPesananActivity
import com.example.kumowaladmin.R
import com.example.kumowaladmin.dataClass.AkunData
import com.example.kumowaladmin.dataClass.PesananData
import com.example.kumowaladmin.`object`.SatuanProduk
import com.example.kumowaladmin.`object`.StatusPesanan
import java.text.NumberFormat
import java.util.Locale

class AkunAdapater (private val productList:ArrayList<AkunData>, val lifecycleOwner : LifecycleOwner, val loadingBar : ProgressBar, val context : Context) : RecyclerView.Adapter<AkunAdapater.ProductsViewHolder>() {

    var onItemClick : ((Array<String>) -> Unit)? = null

    var list = productList //kalau mau gunakan notifyDataSetChanged, yang di ubah variabel ini

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_akun,parent,false)
        return ProductsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        var currentItem = list[position]

        holder.tvNama.text = currentItem.name
        holder.tvAlamat.text = currentItem.address
        holder.btnLihatDetail.setOnClickListener {
            val intent = Intent(context, DetailAkunActivity::class.java)
            intent.putExtra("id_akun", currentItem.id)
            context.startActivity(intent)
        }
    }

    class ProductsViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvNama : TextView = itemView.findViewById(R.id.itemAkuntvNama)
        val tvAlamat : TextView = itemView.findViewById(R.id.itemAkuntvAlamat)
        val btnLihatDetail : Button = itemView.findViewById(R.id.itemAkunDetailAkun)
    }
}
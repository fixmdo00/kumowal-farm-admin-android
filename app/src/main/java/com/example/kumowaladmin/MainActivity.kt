package com.example.kumowaladmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kumowaladmin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BerandaFragment.OnFragmentInteractionListener {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(BerandaFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.beranda-> replaceFragment(BerandaFragment())
                R.id.pesanan-> replaceFragment(PesananFragment())
                R.id.profil-> replaceFragment(ProfilFragment())
            }
            true
        }

    }
    fun replaceFragment (fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()

    }

    override fun onChangeBottomNavigationItem(itemId: Int) {
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.selectedItemId = itemId
    }

}
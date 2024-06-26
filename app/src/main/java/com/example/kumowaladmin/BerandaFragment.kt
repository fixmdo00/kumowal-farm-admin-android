package com.example.kumowaladmin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kumowaladmin.databinding.FragmentBerandaBinding
import com.example.kumowaladmin.databinding.FragmentPesananBinding
import com.example.kumowaladmin.`object`.Pengguna

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BerandaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BerandaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _binding : FragmentBerandaBinding
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(layoutInflater, container, false)
         binding.tvNamaAdmin.text = Pengguna.nama

        binding.btnKelolaPesanan.setOnClickListener {
            (activity as? OnFragmentInteractionListener)?.onChangeBottomNavigationItem(R.id.pesanan)
            replaceFragment(PesananFragment())
        }

        binding.btnUbahHarga.setOnClickListener {
            val intent = Intent(context, KelolaHargaActivity::class.java)
            startActivity(intent)
        }

        binding.btnKelolaAkun.setOnClickListener {
            val intent = Intent(context, KelolaAkunActivity::class.java)
            startActivity(intent)
        }

        return (binding.root)
    }

    fun replaceFragment (fragment: Fragment){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BerandaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BerandaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    interface OnFragmentInteractionListener {
        fun onChangeBottomNavigationItem(itemId: Int)
    }
}
package com.example.kumowaladmin

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kumowaladmin.adapter.PesananAdapater
import com.example.kumowaladmin.dataClass.PesananData
import com.example.kumowaladmin.databinding.FragmentPesananBinding
import com.example.kumowaladmin.`object`.Pesanan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PesananFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PesananFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _binding : FragmentPesananBinding
    val binding get() = _binding
    private lateinit var recyclerView: RecyclerView
    private lateinit var pesananAdapter : PesananAdapater
    private var pesananArrayList = ArrayList<PesananData>()
    private lateinit var colorSelected : ColorDrawable
    private lateinit var colorNotSelected : ColorDrawable

    private var refreshing = false
    private var refreshJob: Job? = null

    private var activeStatusWindow = "1"

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
        _binding = FragmentPesananBinding.inflate(layoutInflater, container, false)

        Pesanan.getFromDB(binding.loadingProgressBar){}

        val color1 = ContextCompat.getColor(requireContext(), R.color.primaryBackground)
        val color2 = ContextCompat.getColor(requireContext(), R.color.white)
        colorSelected = ColorDrawable(color2)
        colorNotSelected = ColorDrawable(color1)

        recyclerView = binding.RVPesanan
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        pesananAdapter = PesananAdapater(pesananArrayList, this, binding.loadingProgressBar, requireContext())
        recyclerView.adapter = pesananAdapter
        Pesanan.livePesanan.observe(viewLifecycleOwner){
            pesananAdapter.list = Pesanan.getPesananArrayListByStatus(activeStatusWindow)
            if(pesananAdapter.list.size == 0){
                binding.tvPesananKosong.visibility = View.VISIBLE
            } else {
                binding.tvPesananKosong.visibility = View.INVISIBLE
            }
            pesananAdapter.notifyDataSetChanged()
            setActiveWindow(activeStatusWindow)
        }

        binding.topBtn1.setOnClickListener {
            activeStatusWindow = "1"
            pesananAdapter.list = Pesanan.getPesananArrayListByStatus("1")
            if(pesananAdapter.list.size == 0){
                binding.tvPesananKosong.visibility = View.VISIBLE
            } else {
                binding.tvPesananKosong.visibility = View.INVISIBLE
            }
            pesananAdapter.notifyDataSetChanged()
            setActiveWindow("1")
        }
        binding.topBtn2.setOnClickListener {
            activeStatusWindow = "2"
            pesananAdapter.list = Pesanan.getPesananArrayListByStatus("2")
            if(pesananAdapter.list.size == 0){
                binding.tvPesananKosong.visibility = View.VISIBLE
            } else {
                binding.tvPesananKosong.visibility = View.INVISIBLE
            }
            pesananAdapter.notifyDataSetChanged()
            setActiveWindow("2")
        }

        binding.topBtn3.setOnClickListener {
            activeStatusWindow = "3"
            pesananAdapter.list = Pesanan.getPesananArrayListByStatus("3")
            if(pesananAdapter.list.size == 0){
                binding.tvPesananKosong.visibility = View.VISIBLE
            } else {
                binding.tvPesananKosong.visibility = View.INVISIBLE
            }
            pesananAdapter.notifyDataSetChanged()
            setActiveWindow("3")
        }

        binding.topBtn4.setOnClickListener {
            activeStatusWindow = "4"
            pesananAdapter.list = Pesanan.getPesananArrayListByStatus("4")
            if(pesananAdapter.list.size == 0){
                binding.tvPesananKosong.visibility = View.VISIBLE
            } else {
                binding.tvPesananKosong.visibility = View.INVISIBLE
            }
            pesananAdapter.notifyDataSetChanged()
            setActiveWindow("4")
        }

        binding.topBtn5.setOnClickListener {
            activeStatusWindow = "0"
            pesananAdapter.list = Pesanan.getPesananArrayListByStatus("0")
            if(pesananAdapter.list.size == 0){
                binding.tvPesananKosong.visibility = View.VISIBLE
            } else {
                binding.tvPesananKosong.visibility = View.INVISIBLE
            }
            pesananAdapter.notifyDataSetChanged()
            setActiveWindow("0")
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            Pesanan.getFromDB(){
                pesananAdapter.list = Pesanan.getPesananArrayListByStatus(activeStatusWindow)
                if(pesananAdapter.list.size == 0){
                    binding.tvPesananKosong.visibility = View.VISIBLE
                } else {
                    binding.tvPesananKosong.visibility = View.INVISIBLE
                }
                pesananAdapter.notifyDataSetChanged()
                setActiveWindow(activeStatusWindow)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        autoRefresh(true)



        return (binding.root)
    }

    fun setActiveWindow (status : String) {
        when (status){
            "0" -> {
                unselect(binding.topBtn1,binding.topBtn1Tv)
                unselect(binding.topBtn2, binding.topBtn2Tv)
                unselect(binding.topBtn3, binding.topBtn3Tv)
                unselect(binding.topBtn4, binding.topBtn4Tv)
                select(binding.topBtn5, binding.topBtn5Tv)
            }
            "1" -> {
                select(binding.topBtn1,binding.topBtn1Tv)
                unselect(binding.topBtn2, binding.topBtn2Tv)
                unselect(binding.topBtn3, binding.topBtn3Tv)
                unselect(binding.topBtn4, binding.topBtn4Tv)
                unselect(binding.topBtn5, binding.topBtn5Tv)
            }
            "2" -> {
                unselect(binding.topBtn1,binding.topBtn1Tv)
                select(binding.topBtn2, binding.topBtn2Tv)
                unselect(binding.topBtn3, binding.topBtn3Tv)
                unselect(binding.topBtn4, binding.topBtn4Tv)
                unselect(binding.topBtn5, binding.topBtn5Tv)
            }
            "3" -> {
                unselect(binding.topBtn1,binding.topBtn1Tv)
                unselect(binding.topBtn2, binding.topBtn2Tv)
                select(binding.topBtn3, binding.topBtn3Tv)
                unselect(binding.topBtn4, binding.topBtn4Tv)
                unselect(binding.topBtn5, binding.topBtn5Tv)
            }
            "4" -> {
                unselect(binding.topBtn1,binding.topBtn1Tv)
                unselect(binding.topBtn2, binding.topBtn2Tv)
                unselect(binding.topBtn3, binding.topBtn3Tv)
                select(binding.topBtn4, binding.topBtn4Tv)
                unselect(binding.topBtn5, binding.topBtn5Tv)
            }
        }

    }

    private fun select(view : LinearLayout, text : TextView){
        view.background = colorSelected
        text.setTypeface(null, Typeface.BOLD)
    }

    private fun unselect(view : LinearLayout, text : TextView){
        view.background = colorNotSelected
        text.setTypeface(null, Typeface.NORMAL)
    }

    private fun autoRefresh(isRefreshing: Boolean) {
        refreshing = isRefreshing

        if (isRefreshing) {
            refreshJob = CoroutineScope(Dispatchers.Main).launch {
                while (refreshing) {
                    Log.d("Coroutine", "Refresh")
                    Pesanan.getFromDB(){
                        pesananAdapter.list = Pesanan.getPesananArrayListByStatus(activeStatusWindow)
                        if(pesananAdapter.list.size == 0){
                            binding.tvPesananKosong.visibility = View.VISIBLE
                        } else {
                            binding.tvPesananKosong.visibility = View.INVISIBLE
                        }
                        pesananAdapter.notifyDataSetChanged()
                        setActiveWindow(activeStatusWindow)
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    delay(5000)
                }
            }
        } else {
            refreshJob?.cancel()
        }
    }

    override fun onPause() {
        super.onPause()
        // Hentikan autoRefresh ketika fragment berhenti
        autoRefresh(false)
    }

    // Atau jika Anda lebih suka menghentikan autoRefresh saat view dihancurkan
    override fun onDestroyView() {
        super.onDestroyView()
        // Hentikan autoRefresh ketika view dari fragment dihancurkan
        autoRefresh(false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PesananFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PesananFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
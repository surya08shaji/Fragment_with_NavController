package com.example.navartlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navartlist.data.ApiInterface
import com.example.navartlist.data.Urls
import com.example.navartlist.databinding.FragmentListBinding
import com.example.navartlist.interfaces.UserSelection
import com.example.navartlist.model.DataModelItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentList : Fragment() {

    private lateinit var adapter: Adapter
    var data = ArrayList<DataModelItem>()
    var isLoading = false

    var currentPage = 1
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        initScrollListener()
        loadPageList()


        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager


        return view

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadPageList() {

        val apiCall = Urls.getInstance().create(ApiInterface::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val data = apiCall.getData(10, "Latest", currentPage)
            if (data.isSuccessful) {
                val dataList = data.body()
                if (dataList != null) {
                    adapter = Adapter(object : UserSelection {
                        override fun onClick(id: Int) {
                            val key = id
                            val action =
                                FragmentListDirections.actionFragmentListToFragmentDetails(key)
                            findNavController().navigate(action)
                        }
                    }, dataList)
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }

    private fun initScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager
                            .findLastCompletelyVisibleItemPosition() == data.size - 1
                    ) {
                        currentPage += 1
                        loadPageList()
                    }
                }
            }
        })
    }
}

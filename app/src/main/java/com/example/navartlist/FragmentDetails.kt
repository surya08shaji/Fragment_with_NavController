package com.example.navartlist

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.navartlist.data.ApiInterface
import com.example.navartlist.data.Urls
import com.example.navartlist.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentDetails : Fragment() {

    var value: Int? = null

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: FragmentDetailsArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        val key = args.key

        val apiCall = Urls.getInstance().create(ApiInterface::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val value = apiCall.getDetails(key)
            if (value.isSuccessful) {
                val dataList = value.body()
                if (dataList != null) {
                    binding.title.text = dataList.title
                    binding.author.text = dataList.author
                    Picasso.with(binding.image.context).load(dataList.image).into(binding.image)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        binding.content.text =
                            Html.fromHtml(dataList.content, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        binding.content.text = Html.fromHtml(dataList.content)
                    }
                }
            }
        }
        return view

    }

}
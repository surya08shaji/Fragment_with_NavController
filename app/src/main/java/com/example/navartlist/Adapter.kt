package com.example.navartlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navartlist.databinding.ListRowBinding
import com.example.navartlist.interfaces.UserSelection
import com.example.navartlist.model.DataModelItem
import com.squareup.picasso.Picasso

class Adapter(
    private val userSelection: UserSelection,
    private var mList: ArrayList<DataModelItem>?
) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(val binding: ListRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(mList!![position]) {
                binding.title.text = this.title
                binding.author.text = this.author

                Picasso.with(binding.image.context).load(this.image).into(binding.image)

                binding.cardView.setOnClickListener { userSelection.onClick(mList!![adapterPosition].id) }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }
}
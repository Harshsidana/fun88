package com.fun88.testapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fun88.testapplication.databinding.LayoutItemViewBinding
import com.fun88.testapplication.model.DataItems

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var dataItemList = mutableListOf<DataItems>()
    lateinit var binding: LayoutItemViewBinding
    fun setItems(itemList: MutableList<DataItems>) {
        dataItemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = LayoutItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataItemList[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return dataItemList.size
    }

    class MyViewHolder(val binding: LayoutItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: DataItems) {
            binding.category.text = data.category
            binding.description.text = data.desc
            binding.name.text = data.name
            binding.ivImage.loadImage(binding.root.context, data.imageUrl)
        }

    }
}
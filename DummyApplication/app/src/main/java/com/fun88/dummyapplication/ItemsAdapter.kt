package com.fun88.dummyapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hadi.retrofitmvvm.model.DataItem

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

//    lateinit var  binding:ActivityMainBinding
    var dataItems= listOf<DataItem>()
    fun setItems(list: List<DataItem>) {
        this.dataItems = list
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        binding= ActivityMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//       return ViewHolder(binding.root)
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindData(dataItems[position])

    }

    override fun getItemCount(): Int {
      return dataItems.size
    }

    open class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        fun bindData(item:DataItem)
        {



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }
}
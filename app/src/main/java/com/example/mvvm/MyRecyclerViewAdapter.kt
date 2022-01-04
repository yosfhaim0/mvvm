package com.example.mvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.databinding.ListItemBinding

class MyRecyclerViewAdapter(private val clickListener: (Parcel) -> Unit) :
        RecyclerView.Adapter<MyViewHolder>() {
    private val ParcelsList = ArrayList<Parcel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ParcelsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(ParcelsList[position], clickListener)
    }

    fun setList(subscribers: List<Parcel>) {
        ParcelsList.clear()
        ParcelsList.addAll(subscribers)

    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(parcel: Parcel, clickListener: (Parcel) -> Unit) {
        binding.nameTextView.text = parcel.owner_address
        binding.emailTextView.text = parcel.owner
        binding.listItemLayout.setOnClickListener {
            clickListener(parcel)
        }
    }
}
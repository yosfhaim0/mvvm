package com.example.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var parcelViewModel: ParcelViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = ParcelDatabase.getInstance(application).parcelDAO
        val repository = ParcelRepository(dao)
        val factory = ParcelViewModelFactory(repository)
        parcelViewModel = ViewModelProvider(this, factory).get(ParcelViewModel::class.java)
        binding.myViewModel = parcelViewModel
        binding.lifecycleOwner = this

        parcelViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.parcelRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({ selectedItem: Parcel -> listItemClicked(selectedItem) })
        binding.parcelRecyclerView.adapter = adapter
        displayParcelList()
    }

    private fun displayParcelList() {
        parcelViewModel.getSavedParcel().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(parcel: Parcel) {
        parcelViewModel.initUpdateAndDelete(parcel)
    }

}
package com.meseems.mereach.server.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.meseems.mereach.R
import com.meseems.mereach.base.adapter.ObservableRecyclerViewAdapter
import com.meseems.mereach.databinding.ItemServerBinding

class ServersAdapter(list: ObservableList<ServerItemView>)
    : ObservableRecyclerViewAdapter<ServerItemView, ServerViewHolder>(list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {

        val binding: ItemServerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_server,
                parent,
                false)
        return ServerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ServerViewHolder (private val binding: ItemServerBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ServerItemView) {
        binding.server = item
    }
}

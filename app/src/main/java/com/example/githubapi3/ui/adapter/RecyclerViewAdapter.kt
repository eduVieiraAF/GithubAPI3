package com.example.githubapi3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapi3.R
import com.example.githubapi3.ui.model.RepoVO

class RecyclerViewAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private var listData = mutableListOf<RepoVO>()

    fun addUpdatedList(listData: List<RepoVO>) {

        val position = this.listData.size
        this.listData.addAll(listData)
        notifyItemRangeInserted(position, listData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_list_row, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount() = listData.size

    fun resetList() {

        val size = listData.size
        this.listData.clear()
        notifyItemRangeRemoved(0, size)
    }
}




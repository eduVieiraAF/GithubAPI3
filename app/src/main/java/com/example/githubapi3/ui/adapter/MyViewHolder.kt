package com.example.githubapi3.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapi3.R
import com.example.githubapi3.ui.model.RepoVO

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imageAvatar = view.findViewById<ImageView>(R.id.iv_user)
    private val repoTitle = view.findViewById<TextView>(R.id.tv_repo_title)
    private val author = view.findViewById<TextView>(R.id.tv_author)
    private val description = view.findViewById<TextView>(R.id.tv_description)
    private val starCount = view.findViewById<TextView>(R.id.tv_star_count)
    private val forkCount = view.findViewById<TextView>(R.id.tv_fork_count)

    fun bind(data: RepoVO) {
        repoTitle.text = data.name
        author.text = data.ownerLogin
        description.text = data.description
        starCount.text = data.stargazerCount
        forkCount.text = data.forksCount

        if (data.ownerAvatar.isEmpty()) imageAvatar.setImageResource(R.drawable.github)
        else {
            Glide.with(imageAvatar)
                .load(data.ownerAvatar)
                .apply(RequestOptions.centerCropTransform())
                .into(imageAvatar)
        }
    }
}

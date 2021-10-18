package com.example.trendinggitrepos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.trendinggitrepos.R
import com.example.trendinggitrepos.adapters.RepositoryAdapter.RepositoryViewHolder
import com.example.trendinggitrepos.data.model.Repository
import com.example.trendinggitrepos.data.model.RepositoryItem
import com.example.trendinggitrepos.databinding.RepoListItemBinding

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryViewHolder>() {

    inner class RepositoryViewHolder(val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<RepositoryItem>() {
        override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitChatList(list: List<RepositoryItem>) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryViewHolder {
        return RepositoryViewHolder(
            RepoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val view = holder.binding
        val item = differ.currentList[position]

        Glide.with(context).load(item.avatar)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(view.ivRepoAvatar)

        view.tvAuthorName.text = item.author
        view.tvRepoName.text = item.name
        view.tvRepoDesc.text = item.description

        Glide.with(context).load(item.languageColor)
            .into(view.ivLanguageColor)
        view.tvLanguageName.text = item.language

        view.tvStarCount.text = item.stars.toString()

        view.tvForkCount.text = item.forks.toString()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
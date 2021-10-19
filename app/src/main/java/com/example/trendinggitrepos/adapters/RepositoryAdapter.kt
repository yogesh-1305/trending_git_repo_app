package com.example.trendinggitrepos.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.trendinggitrepos.R
import com.example.trendinggitrepos.adapters.RepositoryAdapter.RepositoryViewHolder
import com.example.trendinggitrepos.data.model.RepoApiResponseItem
import com.example.trendinggitrepos.databinding.RepoListItemBinding
import com.example.trendinggitrepos.util.UtilityMethods.gone
import com.example.trendinggitrepos.util.UtilityMethods.show

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryViewHolder>() {

    inner class RepositoryViewHolder(val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<RepoApiResponseItem>() {
        override fun areItemsTheSame(
            oldItem: RepoApiResponseItem,
            newItem: RepoApiResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: RepoApiResponseItem,
            newItem: RepoApiResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<RepoApiResponseItem>) = differ.submitList(list)

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

        if (item.builtBy.isNotEmpty()) {
            Glide.with(context).load(item.builtBy[0].avatar ?: R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(view.ivRepoAvatar)
        } else {
            Glide.with(context).load(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view.ivRepoAvatar)
        }

        view.apply {

            tvAuthorName.text = item.username
            tvRepoName.text = item.repositoryName
            tvRepoDesc.text = item.description
            tvRepoLink.text = item.url

            val col = Color.parseColor(item.languageColor ?: "#000000")
            ivLanguageColor.setBackgroundColor(col)

            tvLanguageName.text = item.language
            tvStarCount.text = item.totalStars.toString()
            tvForkCount.text = item.forks.toString()

        }
        var isExpanded = false
        view.repoListItemLayout.setOnClickListener {
            if (!isExpanded) {
                isExpanded = true
                expandItem(view)
            } else {
                isExpanded = false
                collapseItem(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private fun expandItem(view: RepoListItemBinding) {
        view.apply {
            ivLanguageColor.show()
            ivStar.show()
            ivFork.show()

            tvRepoLink.show()
            tvRepoDesc.show()
            tvForkCount.show()
            tvStarCount.show()
            tvLanguageName.show()
            repoListItemLayout.setBackgroundColor(Color.parseColor("#dddddd"))
        }
    }

    private fun collapseItem(view: RepoListItemBinding) {
        view.apply {
            ivLanguageColor.gone()
            ivStar.gone()
            ivFork.gone()

            tvRepoLink.gone()
            tvRepoDesc.gone()
            tvForkCount.gone()
            tvStarCount.gone()
            tvLanguageName.gone()
            repoListItemLayout.setBackgroundColor(Color.parseColor("#ffffff"))
        }
    }
}
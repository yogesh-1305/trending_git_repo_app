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
import com.example.trendinggitrepos.data.model.RepositoryItem
import com.example.trendinggitrepos.data.model.test.Item
import com.example.trendinggitrepos.databinding.RepoListItemBinding
import com.example.trendinggitrepos.util.LanguageColors
import com.example.trendinggitrepos.util.LanguageColors.getColor
import com.example.trendinggitrepos.util.UtilityMethods.gone
import com.example.trendinggitrepos.util.UtilityMethods.show

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryViewHolder>() {

    inner class RepositoryViewHolder(val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Item>) = differ.submitList(list)

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

        if (item.avatars.isNotEmpty()) {
            Glide.with(context).load(item.avatars[0])
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(view.ivRepoAvatar)
        } else {
            Glide.with(context).load(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view.ivRepoAvatar)
        }

        view.apply {

            tvAuthorName.text = item.repo.split("/").first()
            tvRepoName.text = item.repo.split("/").last()
            tvRepoDesc.text = item.desc

            val col = Color.parseColor(item.lang.getColor() ?: "#000000")
            ivLanguageColor.setBackgroundColor(col)

            tvLanguageName.text = item.lang
            tvStarCount.text = item.stars
            tvForkCount.text = item.forks

        }
        var isExpanded = false
        view.repoListItemLayout.setOnClickListener {
            if (!isExpanded) {
                isExpanded = true
                view.apply {
                    ivLanguageColor.show()
                    ivStar.show()
                    ivFork.show()

                    tvRepoDesc.show()
                    tvForkCount.show()
                    tvStarCount.show()
                    tvLanguageName.show()
                    repoListItemLayout.setBackgroundColor(Color.parseColor("#dddddd"))
                }
            } else {
                isExpanded = false
                view.apply {
                    ivLanguageColor.gone()
                    ivStar.gone()
                    ivFork.gone()

                    tvRepoDesc.gone()
                    tvForkCount.gone()
                    tvStarCount.gone()
                    tvLanguageName.gone()
                    repoListItemLayout.setBackgroundColor(Color.parseColor("#ffffff"))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
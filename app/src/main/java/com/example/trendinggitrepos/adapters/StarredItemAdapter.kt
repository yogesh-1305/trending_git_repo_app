package com.example.trendinggitrepos.adapters

import android.app.Activity
import android.graphics.Color
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.trendinggitrepos.R
import com.example.trendinggitrepos.StarredRepoFragmentDirections
import com.example.trendinggitrepos.constants.Constants
import com.example.trendinggitrepos.data.model.CustomRepository
import com.example.trendinggitrepos.databinding.RepoListItemBinding
import com.example.trendinggitrepos.ui.fragments.RepoListFragmentDirections
import com.example.trendinggitrepos.ui.fragments.SearchFragmentDirections
import com.example.trendinggitrepos.util.UtilityMethods.show

class StarredItemAdapter(val context: Activity) :
    RecyclerView.Adapter<StarredItemAdapter.StarredItemViewHolder>() {

    inner class StarredItemViewHolder(val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<CustomRepository>() {
        override fun areItemsTheSame(
            oldItem: CustomRepository,
            newItem: CustomRepository
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CustomRepository,
            newItem: CustomRepository
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<CustomRepository>) = differ.submitList(list)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarredItemViewHolder {
        return StarredItemViewHolder(
            RepoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StarredItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        val view = holder.binding

        makeEveryViewVisible(view)

        Glide.with(context).load(item.builtBy ?: R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(view.ivRepoAvatar)

        view.apply {

            tvAuthorName.text = item.username
            tvRepoName.text = item.repositoryName
            tvRepoDesc.text = item.description

            val content = SpannableString(item.url)
            content.setSpan(UnderlineSpan(), 0, item.url!!.length, 0)
            tvRepoLink.text = content

            val col = Color.parseColor(item.languageColor ?: "#000000")
            ivLanguageColor.setBackgroundColor(col)

            tvLanguageName.text = item.language
            tvStarCount.text = item.totalStars.toString()
            tvForkCount.text = item.forks.toString()
        }

        setClickListeners(view, item)
    }

    private fun setClickListeners(view: RepoListItemBinding, item: CustomRepository) {
        val navController = context.findNavController(R.id.fragmentContainerView)
        view.tvRepoLink.setOnClickListener {
            navController.navigate(
                StarredRepoFragmentDirections.actionStarredRepoFragmentToWebViewFragment(
                    item
                )
            )
        }
    }

    private fun makeEveryViewVisible(view: RepoListItemBinding) {
        view.apply {
            ivLanguageColor.show()
            ivStar.show()
            ivFork.show()

            tvRepoLink.show()
            tvRepoDesc.show()
            tvForkCount.show()
            tvStarCount.show()
            tvLanguageName.show()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
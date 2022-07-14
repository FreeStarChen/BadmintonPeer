package com.mark.badmintonpeer.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.data.News
import com.mark.badmintonpeer.databinding.NewsNewsItemBinding
import com.mark.badmintonpeer.util.TimeCalculator

class NewsItemAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<News, NewsItemAdapter.NewsViewHolder>(DiffCallback) {
    class NewsViewHolder(private var binding: NewsNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(news: News) {
            binding.news = news
            binding.textPostTimeNewsItem.text = TimeCalculator.getDate(news.postTime.time)

            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        news?.let {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(news)
            }
            holder.bind(news)
        }
    }

    class OnClickListener(val clickListener: (news: News) -> Unit) {
        fun onClick(news: News) = clickListener(news)
    }
}
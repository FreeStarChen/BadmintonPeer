package com.mark.badmintonpeer.groupdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.MainActivity
import com.mark.badmintonpeer.databinding.GroupDetailCircleBinding

class GroupDetailCircleAdapter : RecyclerView.Adapter<GroupDetailCircleAdapter.ImageViewHolder>() {

    private lateinit var context: Context
    private var count = 0
    var selectedPosition = MutableLiveData<Int>()

    class ImageViewHolder(val binding: GroupDetailCircleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var isSelected = MutableLiveData<Boolean>()

        fun bind(context: Context, selectedPosition: MutableLiveData<Int>) {

            selectedPosition.observe(
                context as MainActivity
            ) {
                binding.isSelected = it == adapterPosition
                binding.executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        context = parent.context
        return ImageViewHolder(
            GroupDetailCircleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(context, selectedPosition)
    }

    override fun getItemCount(): Int {
        return count
    }

    /**
     * Submit data list and refresh adapter by [notifyDataSetChanged]
     * @param count: set up count of circles
     */
    fun submitCount(count: Int) {
        this.count = count
        notifyDataSetChanged()
    }
}
package com.mark.badmintonpeer.news

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.databinding.NewsFragmentBinding
import com.mark.badmintonpeer.databinding.ProfileFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.profile.ProfileViewModel
import java.util.*

class NewsFragment : Fragment() {

    private val viewModel by viewModels<NewsViewModel> { getVmFactory() }
    private lateinit var binding: NewsFragmentBinding

    companion object {
        fun newInstance() = NewsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = NewsFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerViewHotGroup.adapter = NewsHotGroupAdapter(
            NewsHotGroupAdapter.OnClickListener {
                viewModel.navigateToGroupDetail(it)
            }
        )

        binding.recyclerViewNewsCircles.adapter = NewsCircleAdapter()

        viewModel.news.observe(viewLifecycleOwner) {
            (binding.recyclerViewNews.adapter as NewsItemAdapter).submitList(it)

            viewModel.news.value?.let { news ->
                binding.recyclerViewNews.scrollToPosition(news.size)

                viewModel.snapPosition.observe(viewLifecycleOwner) { position ->
                    (binding.recyclerViewNewsCircles.adapter as NewsCircleAdapter).selectedPosition.value =
                        (position % news.size)
                }
            }
        }

        viewModel.navigateToGroupDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToGroupDetailFragment(it))
                viewModel.onGroupDetailNavigated()
            }
        }

        viewModel.groups.observe(viewLifecycleOwner) {

            (binding.recyclerViewHotGroup.adapter as NewsHotGroupAdapter).submitList(it)
        }

        var recyclerNews = binding.recyclerViewNews

        var newsAdapter = binding.recyclerViewNews.adapter

        newsAdapter = NewsItemAdapter(
            NewsItemAdapter.OnClickListener {
                viewModel.navigateToNewsDetail(it)
            }
        )

        val manager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        recyclerNews.run {
            adapter = newsAdapter
            layoutManager = manager
        }

        val linearSnapHelper = LinearSnapHelper().apply {
            attachToRecyclerView(recyclerNews)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.recyclerViewNews.setOnScrollChangeListener { _, _, _, _, _ ->
                viewModel.onImageScrollChange(
                    binding.recyclerViewNews.layoutManager, linearSnapHelper
                )
            }
        }

        val timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    if (manager.findLastVisibleItemPosition() < (newsAdapter.itemCount - 1)) {

                        manager.smoothScrollToPosition(
                            recyclerNews,
                            RecyclerView.State(), manager.findLastVisibleItemPosition() + 1
                        )

                    } else if (
                        manager.findLastVisibleItemPosition() == (newsAdapter.itemCount - 1)) {

                        manager.smoothScrollToPosition(
                            recyclerNews,
                            RecyclerView.State(), 0
                        )
                    }
                }
            },
            0, 5000
        )

        viewModel.navigateToNewsDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToNewsDetailFragment(it))
                viewModel.onNewsDetailNavigated()
            }
        }



        return binding.root
    }

}
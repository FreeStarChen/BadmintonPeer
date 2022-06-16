package com.mark.badmintonpeer.creatgroup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mark.badmintonpeer.R

class CreatGroupFragment : Fragment() {

    companion object {
        fun newInstance() = CreatGroupFragment()
    }

    private lateinit var viewModel: CreatGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.creat_group_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreatGroupViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
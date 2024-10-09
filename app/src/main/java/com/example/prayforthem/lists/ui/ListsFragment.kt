package com.example.prayforthem.lists.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentListsBinding
import com.example.prayforthem.lists.presentation.ListsViewModel
import com.example.prayforthem.lists_for_health.ui.ForHealthFragment
import com.example.prayforthem.lists_for_repose.ui.ForReposeFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListsFragment : Fragment() {

    private var _binding: FragmentListsBinding? = null
    private val binding get() = _binding!!
    private var tabsMediator: TabLayoutMediator? = null
    private val viewModel by viewModel<ListsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ListsViewPagerAdapter(childFragmentManager, lifecycle)

        tabsMediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                ZERO -> tab.text = getString(R.string.for_the_health)
                ONE -> tab.text = getString(R.string.for_the_repose)
            }
        }

        tabsMediator!!.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabsMediator!!.detach()
        tabsMediator = null
        _binding = null
    }

    private inner class ListsViewPagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int = TABS_QUANTITY

        override fun createFragment(position: Int): Fragment {
            return if (position == ZERO) ForHealthFragment.newInstance()
            else ForReposeFragment.newInstance()
        }

    }

    companion object {
        private const val TABS_QUANTITY = 2
        private const val ZERO = 0
        private const val ONE = 1
    }

}
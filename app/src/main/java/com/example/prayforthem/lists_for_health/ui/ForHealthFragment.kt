package com.example.prayforthem.lists_for_health.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.databinding.FragmentForHealthBinding
import com.example.prayforthem.lists.ui.ListsFragmentDirections
import com.example.prayforthem.lists_for_health.presentation.ForHealthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForHealthFragment : Fragment() {

    private var _binding: FragmentForHealthBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ForHealthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForHealthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            navigate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate() {
        val action = ListsFragmentDirections.actionListsFragmentToCreateListFragment(
            isForHealthArg = IS_FOR_HEALTH
        )
        findNavController().navigate(action)
    }

    companion object {
        fun newInstance() = ForHealthFragment()
        private const val IS_FOR_HEALTH = true
    }

}
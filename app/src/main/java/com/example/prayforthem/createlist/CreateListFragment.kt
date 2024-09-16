package com.example.prayforthem.createlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.prayforthem.R
import com.example.prayforthem.RootActivity
import com.example.prayforthem.databinding.FragmentCreateListBinding
import com.example.prayforthem.utils.setFragmentTitle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class CreateListFragment : Fragment() {

    private var _binding: FragmentCreateListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<CreateListViewModel>()

    private var isForHealth: Boolean = true
    private lateinit var listType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateListBinding.inflate(inflater, container, false)
        isForHealth = CreateListFragmentArgs.fromBundle(requireArguments()).isForHealthArg
        listType =
            if (isForHealth) getString(R.string.for_the_health)
                .lowercase(Locale.getDefault())
            else getString(R.string.for_the_repose)
        setFragmentTitle(requireActivity() as RootActivity, getString(R.string.new_list, listType))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setListType(isForHealth)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
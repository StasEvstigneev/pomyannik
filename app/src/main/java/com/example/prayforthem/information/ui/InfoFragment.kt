package com.example.prayforthem.information.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentInfoBinding
import com.example.prayforthem.information.presentation.InfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<InfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appName = "\"${getString(R.string.app_name)}\""
        val appVersion =
            activity?.let {
                requireActivity().packageManager.getPackageInfo(
                    it.packageName,
                    0
                ).versionName
            }
        binding.appInfo.text = getString(R.string.app_info, appName, appVersion)

        binding.pamyatkaOZapiskah.setOnClickListener {
            navigateToArticleDisplay(PAMYATKA_FILE)
        }

        binding.postanovlenieObImenah.setOnClickListener {
            navigateToArticleDisplay(POSTANOVLENIE_OB_IMENAH_FILE)
        }

        binding.pozhertvovaniya.setOnClickListener {
            navigateToArticleDisplay(POZHERTVOVANIYA_FILE)
        }

    }

    private fun navigateToArticleDisplay(articleFileName: String) {
        val action =
            InfoFragmentDirections.actionInfoFragmentToArticleDisplayFragment(articleFileName)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PAMYATKA_FILE = "pamyatka_o_zapiskah"
        private const val POSTANOVLENIE_OB_IMENAH_FILE = "postanovlenie_ob_imenah"
        private const val POZHERTVOVANIYA_FILE = "pozhertvovaniya"
    }

}
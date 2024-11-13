package com.example.prayforthem.pomyannikdisplay

import androidx.navigation.fragment.navArgs
import com.example.prayforthem.prayerdisplay.ui.PrayerDisplayFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PomyannikDisplayFragment : PrayerDisplayFragment() {

    private val args: PomyannikDisplayFragmentArgs by navArgs()
    override val viewModel: PomyannikDisplayViewModel by viewModel {
        parametersOf(args.prayerFileNameArg)
    }
}
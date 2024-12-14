package com.example.prayforthem.splashscreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.prayforthem.R
import com.example.prayforthem.RootActivity
import com.example.prayforthem.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appVersion = this.let {
            this.packageManager.getPackageInfo(
                it.packageName,
                0
            ).versionName
        }
        binding.appName.text = getString(R.string.app_name)
        binding.version.text = getString(R.string.version_n, appVersion)

        lifecycleScope.launch {
            delay(2000)
            closeSplashScreen()
        }

    }

    private fun closeSplashScreen() {
        val intent = Intent(this, RootActivity::class.java)
        startActivity(intent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0)
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0, 0)
        } else {
            overridePendingTransition(0, 0)
        }
        this.finish()
    }

}
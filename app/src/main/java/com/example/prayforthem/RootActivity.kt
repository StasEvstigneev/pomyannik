package com.example.prayforthem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.prayforthem.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    lateinit var rootBinding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootBinding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(rootBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationMenu = rootBinding.bottomNavigation
        bottomNavigationMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.listsFragment,
                R.id.prayersFragment,
                R.id.infoFragment -> {
                    rootBinding.apply {
                        bottomNavigation.isVisible = true
                        divider.isVisible = true
                    }
                }

                else -> {
                    rootBinding.apply {
                        bottomNavigation.isVisible = false
                        divider.isVisible = false
                    }
                }
            }
        }

    }

}
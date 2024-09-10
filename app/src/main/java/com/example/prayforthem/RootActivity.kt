package com.example.prayforthem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prayforthem.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var rootBinding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootBinding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(rootBinding.root)
    }

}
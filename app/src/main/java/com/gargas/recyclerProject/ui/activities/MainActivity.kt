package com.gargas.recyclerProject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gargas.recyclerProject.R
import com.gargas.recyclerProject.activities.ui.main.MainFragment
import com.gargas.recyclerProject.databinding.MainActivityBinding
import com.gargas.recyclerProject.ui.PostRxFragment
import com.gargas.recyclerProject.ui.PostRxRemoteFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = MainActivityBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mBinding.bottomNavView.setupWithNavController(findNavController(R.id.fragNavHost))
    }
}
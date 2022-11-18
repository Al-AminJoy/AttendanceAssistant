package com.alamin.attendanceassistant.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.includeContent.toolbar)
        navController = findNavController(R.id.fragment)
        setupActionBarWithNavController(navController);

    }

    override fun onSupportNavigateUp(): Boolean {

        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}
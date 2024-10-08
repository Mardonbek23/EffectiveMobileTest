package com.example.effectivemobiletest.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivityMainBinding
import com.example.effectivemobiletest.viewmodel.VacancyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private val vacancyViewModel: VacancyViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavView = binding.bottomNav
        navController = findNavController(R.id.nav_host_fragment_controller)
        bottomNavView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(bottomNavView, navController)

        vacancyViewModel.vacanciesCountOnSaved.observe(this) { count ->
            val badge = binding.bottomNav.getOrCreateBadge(R.id.savedFragment)
            badge.backgroundColor = Color.parseColor("#FF0000")
            badge.badgeTextColor = Color.WHITE
            if (count > 0) {
                badge.isVisible = true
                badge.number = count
            } else {
                badge.isVisible = false
            }
        }
    }
}
package com.example.kpmobrazrab

import MAIN
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.kpmobrazrab.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        navController = Navigation.findNavController(this, R.id.fragmentContainer)
        MAIN = this
        binding.navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.openNav,R.string.closeNav)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> MAIN.navController.navigate(R.id.mainFragment)
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }


}
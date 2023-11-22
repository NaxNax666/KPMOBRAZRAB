package com.example.kpmobrazrab

import MAIN
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.kpmobrazrab.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.io.File


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
        toggle.setDrawerIndicatorEnabled(true)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.openFileActivityButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 0)
            } else
            {
                val imgFile = File("/storage/emulated/0/Download/Rainier.bmp")
                if (imgFile.exists()) {
                    // on below line we are creating an image bitmap variable
                    // and adding a bitmap to it from image file.
                    val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)

                    // on below line we are setting bitmap to our image view.
                    DataClass._bitmap = imgBitmap
                }
            }


// do whatever you need with b
            }

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
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val imgFile = File("/storage/emulated/0/Download/Rainier.bmp")
                if (imgFile.exists()) {
                    // on below line we are creating an image bitmap variable
                    // and adding a bitmap to it from image file.
                    val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)

                    // on below line we are setting bitmap to our image view.
                    DataClass._bitmap = imgBitmap
                }
            } else {
                Toast.makeText(this, R.string.openNav, Toast.LENGTH_SHORT).show()
            }
        }
    }



}
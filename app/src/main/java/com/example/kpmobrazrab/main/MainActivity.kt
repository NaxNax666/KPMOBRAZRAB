package com.example.kpmobrazrab.main

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kpmobrazrab.R
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.ActivityMainBinding
import com.example.kpmobrazrab.utils.build.BuildUtils
import com.example.kpmobrazrab.utils.context.isReadExternalStorageGranted
import com.example.kpmobrazrab.utils.context.isReadMediaImagesGranted
import com.example.kpmobrazrab.utils.context.openAppSystemSettings
import java.io.FileNotFoundException

// https://developer.android.com/kotlin/style-guide
class MainActivity : AppCompatActivity(), MainRouter {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var mediaRationaleShown = false

    // Permission result can be handled automatically or manually.
    // This single permission launcher expect to handle only the READ_MEDIA_IMAGES request result:
    // https://developer.android.com/training/permissions/requesting#allow-system-manage-request-code
    // Otherwise you can handle results code by yourself:
    // https://developer.android.com/training/permissions/requesting#manage-request-code-yourself
    private val requestMediaPermissionLauncher =
        registerForActivityResult(RequestPermission()) { granted ->
            if (granted) {
                showMediaChooserDialog()
            } else {
                notifyMediaPermissionRequired()
            }
    }

    // General about take media:
    // https://developer.android.com/training/data-storage/shared/media
    // Registers a photo picker activity launcher in single-select mode:
    // https://developer.android.com/training/data-storage/shared/photopicker
    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the photo picker
        if (uri != null) {
            handleImage(uri)
        } else {
            notifyImageNotSelected()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.openNav,
            R.string.closeNav
        ).also {
            it.isDrawerIndicatorEnabled = true
            binding.drawerLayout.addDrawerListener(it)
            it.syncState()
        }

        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).let {
            navController = it.navController
        }
        binding.navView.setNavigationItemSelectedListener(::onDrawerItemSelected)

        // https://developer.android.com/reference/androidx/activity/OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(enabled = true) { backPressCallback() }

        binding.loadImageButton.setOnClickListener { onLoadImage() }
    }

    override fun navigateForward(destinationId: Int) {
//        Log.d("Navigate", MAIN.navController.currentDestination?.id.toString())
//        if (MAIN.navController.currentDestination?.id == R.id.mainFragment) {
        navController.navigate(destinationId.toScreen())
//        }
    }

    private fun onDrawerItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_home) navController.navigate(R.id.mainFragment)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun backPressCallback() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onLoadImage() {
        when {
            BuildUtils.isSdk33() && this.isReadMediaImagesGranted() -> showMediaChooserDialog()
            BuildUtils.isSdk33() && ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ).also { shown -> mediaRationaleShown = shown } -> {
                showMediaRequiredExplanationDialog()
            }
            !BuildUtils.isSdk33() && this.isReadExternalStorageGranted() -> showMediaChooserDialog()
            !BuildUtils.isSdk33() && ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).also { shown -> mediaRationaleShown = shown } -> {
                showMediaRequiredExplanationDialog()
            }
            mediaRationaleShown -> notifyMediaPermissionRequired()
            else -> {
                requestMediaPermissionLauncher.launch(if (BuildUtils.isSdk33()) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                })
            }
        }
    }

    private fun showMediaChooserDialog() {
        // Launch the photo picker and let the user choose only images.
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }

    private fun handleImage(uri: Uri) {
        // Some devices can rotate image on shot, some not. Add EXIF orientation check
        val bitmap: Bitmap? = try {
            BitmapFactory.decodeStream(this.contentResolver.openInputStream(uri))
        } catch (e: FileNotFoundException) {
            notifyLoadImageError()
            null
        }

        // On below line we are setting bitmap to our image view
        Storage.bitmap = bitmap
    }

    private fun showMediaRequiredExplanationDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.main_permission_dialog_title))
            .setMessage(getString(R.string.main_permission_dialog_description))
            .setPositiveButton(getString(R.string.button_text_yes)) { dialog, _ ->
                this.openAppSystemSettings()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.button_text_no)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun notifyMediaPermissionRequired() {
        Toast.makeText(
            this,
            "Can't load pictures without permissions",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun notifyImageNotSelected() {
        Toast.makeText(
            this,
            "Image not selected",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun notifyLoadImageError() {
        Toast.makeText(
            this,
            "Load image error, try choose another one",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun Int.toScreen() = when (this) {
        0 -> R.id.action_mainFragment_to_blackWhiteFragment
        1 -> R.id.action_mainFragment_to_addNoizeFragment
        2 -> R.id.action_mainFragment_to_cutColorsFragment
        3 -> R.id.action_mainFragment_to_pixelizationFragment
        4 -> R.id.action_mainFragment_to_gaussianBlurFragment
        5 -> R.id.action_mainFragment_to_mirrorVertAxisFragment
        6 -> R.id.action_mainFragment_to_mirrorHorizAxisFragment
        7 -> R.id.action_mainFragment_to_mirrorBothAxisFragment
        8 -> R.id.action_mainFragment_to_changeRGBFragment
        9 -> R.id.action_mainFragment_to_setRedFragment
        10 -> R.id.action_mainFragment_to_setGreenFragment
        11 -> R.id.action_mainFragment_to_setBlueFragment
        12 -> R.id.action_mainFragment_to_changeYUVFragment
        13 -> R.id.action_mainFragment_to_setYFragment
        14 -> R.id.action_mainFragment_to_setUFragment
        15 -> R.id.action_mainFragment_to_setVFragment
        16 -> R.id.action_mainFragment_to_changeCMYKFragment
        17 -> R.id.action_mainFragment_to_negativeRGBFragment
        18 -> R.id.action_mainFragment_to_convolutionWithMatrFragment
        19 -> R.id.action_mainFragment_to_sobelOperatorFragment
        20 -> R.id.action_mainFragment_to_robertsCrossFragment
        21 -> R.id.action_mainFragment_to_autoLumAndConRegFragment
        22 -> R.id.action_mainFragment_to_shiftImageFragment
        else -> SCREEN_UNKNOWN
    }

    companion object {

        private const val SCREEN_UNKNOWN = -1
    }
}
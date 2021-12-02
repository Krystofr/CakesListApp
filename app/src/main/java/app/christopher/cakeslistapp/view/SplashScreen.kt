package app.christopher.cakeslistapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.christopher.cakeslistapp.model.CakesModel
import app.christopher.cakeslistapp.viewModel.SplashViewModel
import cakeslistapp.R

class SplashScreen : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        initViewSplashModel()
        observeSplashLivedata()
    }

    private fun observeSplashLivedata() {
        splashViewModel.initSplashScreen()
        val observer = Observer<CakesModel> {
            startActivity(Intent(this, CakesActivity::class.java))
            finish()
        }
        splashViewModel.liveData.observe(this, observer)
    }

    private fun initViewSplashModel() {
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUi()
    }

    private fun hideSystemUi() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}
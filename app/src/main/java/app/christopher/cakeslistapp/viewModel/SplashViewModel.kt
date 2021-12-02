package app.christopher.cakeslistapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.christopher.cakeslistapp.model.CakesModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    var liveData: MutableLiveData<CakesModel> = MutableLiveData()

    fun initSplashScreen (){
        viewModelScope.launch {
            delay(3000)
            updateLiveData()
        }
    }

    private fun updateLiveData() {
        val model = CakesModel(title = "", desc = "", image = "")
        liveData.value = model
    }
}
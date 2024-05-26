package com.rpn.smartgardening.ui.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rpn.smartgardening.model.GardenInfo
import com.rpn.smartgardening.repository.MainRepository
import kotlinx.coroutines.Dispatchers


class HomeViewModel(val mainRepository: MainRepository) : CoroutineViewModel(Dispatchers.IO) {

    val TAG = "HomeViewModel"
    // Define a MutableLiveData that you can edit
    private val _gardenInfo = MutableLiveData<GardenInfo?>()

    // Expose the LiveData as an immutable LiveData to observe
    val gardenInfo: LiveData<GardenInfo?>
        get() = _gardenInfo

    // Function to update the LiveData
    fun updateData(newValue: GardenInfo?) {
        _gardenInfo.value = newValue
    }

    fun updateManualModeData(newValue: Int) {
        mainRepository.setManualMode(manualMode = newValue) {
            _gardenInfo.value?.info?.apply {
                manual?.manualMode = it?.manualMode ?: 0
                Log.d(TAG, "updateManualModeData: is ${it?.manualMode}")
            }
        }
        Log.d(TAG, "updateManualModeData: ${_gardenInfo.value}")
    }
    fun updateMotorStatusData(newValue: Int) {
        _gardenInfo.value?.info?.apply {
            controller?.motorStatus = newValue
        }
        Log.d(TAG, "updateMotorStatusData: ${_gardenInfo.value}")
    }

    init {
        Log.d("TAG", "HomeViewModel initiated: ")
        mainRepository.getGardenInfo {
            updateData(it)
        }
    }
}
package by.quizzes.amazingquiz.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.quizzes.amazingquiz.model.db.Settings
import by.quizzes.amazingquiz.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: SettingsRepository):ViewModel() {

    var openQuizFragment = MutableLiveData<Boolean>()

    fun addSettings(settings: Settings) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.isEmptySettings()) {
                repository.addSettings(settings)
                openQuizFragment.postValue(true)
            } else {
                settings.id = 1
                repository.updateSetting(settings)
                openQuizFragment.postValue(true)
            }
        }
    }
}
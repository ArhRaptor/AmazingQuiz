package by.quizzes.amazingquiz.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import by.quizzes.amazingquiz.repository.SettingsRepository
import javax.inject.Inject

class SettingViewModelProvider @Inject constructor(private val settingsRepository: SettingsRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SettingViewModel(settingsRepository) as T
    }
}
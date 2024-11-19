package com.example.prayforthem.addname.presentation

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.models.Name
import com.example.prayforthem.utils.NameForms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class AddNameViewModel(private val namesInteractor: NamesInteractor) : ViewModel() {

    private var nameNom: String? = null
    private var nameGen: String? = null
    private var nameDat: String? = null
    private var nameAcc: String? = null
    private var nameInst: String? = null
    private var namePrep: String? = null

    protected val saveButtonState = MutableLiveData<Boolean>(false)
    fun getSaveButtonState(): LiveData<Boolean> = saveButtonState

    protected val exitDialogStatus = MutableLiveData<Boolean>(false)
    fun getExitDialogStatus(): LiveData<Boolean> = exitDialogStatus

    init {
        saveButtonState.postValue(false)
        exitDialogStatus.postValue(false)
    }

    open fun updateName(text: Editable?, forms: NameForms) {
        val nameForm = prepareNameForm(text)
        when (forms) {
            NameForms.NAME_NOMINATIVE -> nameNom = nameForm
            NameForms.NAME_GENITIVE -> nameGen = nameForm
            NameForms.NAME_DATIVE -> nameDat = nameForm
            NameForms.NAME_ACCUSATIVE -> nameAcc = nameForm
            NameForms.NAME_INSTRUMENTAL -> nameInst = nameForm
            NameForms.NAME_PREPOSITIONAL -> namePrep = nameForm
        }
        saveButtonState.postValue(objectContainsNoNull())
        exitDialogStatus.postValue(!objectHasAllNulls())
    }

    open fun saveName() {
        if (objectContainsNoNull()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    namesInteractor.addCustomName(
                        Name(
                            id = null,
                            nameDisplay = nameNom!!,
                            nameNominative = nameNom!!,
                            nameGenitive = nameGen!!,
                            nameDative = nameDat!!,
                            nameAccusative = nameAcc!!,
                            nameInstrumental = nameInst!!,
                            namePrepositional = namePrep!!,
                            isCustom = IS_CUSTOM
                        )
                    )
                }
            }
        }
    }

    protected fun prepareNameForm(text: Editable?): String? {
        return if (!text.isNullOrBlank()) text.toString() else null
    }

    open fun objectContainsNoNull(): Boolean {
        return arrayOf(nameNom, nameGen, nameDat, nameAcc, nameInst, namePrep).all { it != null }
    }

    private fun objectHasAllNulls(): Boolean {
        return arrayOf(nameNom, nameGen, nameDat, nameAcc, nameInst, namePrep).all { it == null }
    }

    companion object {
        private const val IS_CUSTOM = true
    }

}
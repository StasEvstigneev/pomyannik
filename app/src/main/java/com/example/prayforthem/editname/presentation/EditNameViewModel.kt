package com.example.prayforthem.editname.presentation

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.addname.presentation.AddNameViewModel
import com.example.prayforthem.editname.domain.NameTempNameConverter
import com.example.prayforthem.editname.domain.TempName
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.models.Name
import com.example.prayforthem.utils.NameForms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNameViewModel(
    private val nameId: Int,
    private val namesInteractor: NamesInteractor
) : AddNameViewModel(namesInteractor) {

    private var initialName: TempName? = null
    private var updatedName: TempName? = null
    private val nameData = MutableLiveData<Name>()
    fun getNameData(): LiveData<Name> = nameData

    init {
        getNameById(nameId)
        saveButtonState.postValue(false)
        exitDialogStatus.postValue(false)
    }

    private fun getNameById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processName(namesInteractor.getNameById(id))
            }
        }
    }

    private fun processName(name: Name) {
        nameData.postValue(name)
        initialName = NameTempNameConverter.map(name)
        updatedName = NameTempNameConverter.map(name)
    }

    override fun updateName(text: Editable?, forms: NameForms) {
        val nameForm = prepareNameForm(text)
        when (forms) {
            NameForms.NAME_NOMINATIVE -> {
                updatedName!!.nameDisplay = nameForm
                updatedName!!.nameNominative = nameForm
            }

            NameForms.NAME_GENITIVE -> updatedName!!.nameGenitive = nameForm
            NameForms.NAME_DATIVE -> updatedName!!.nameDative = nameForm
            NameForms.NAME_ACCUSATIVE -> updatedName!!.nameAccusative = nameForm
            NameForms.NAME_INSTRUMENTAL -> updatedName!!.nameInstrumental = nameForm
            NameForms.NAME_PREPOSITIONAL -> updatedName!!.namePrepositional = nameForm
        }
        saveButtonState.postValue((objectContainsNoNull() && isNameUpdated()))
        exitDialogStatus.postValue(isNameUpdated())

    }

    override fun saveName() {
        if (objectContainsNoNull()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    namesInteractor
                        .updateCustomName(
                            NameTempNameConverter.map(updatedName!!)
                        )
                }
            }
        }
    }

    override fun objectContainsNoNull(): Boolean {
        return arrayOf(
            updatedName!!.nameDisplay,
            updatedName!!.nameNominative,
            updatedName!!.nameGenitive,
            updatedName!!.nameDative,
            updatedName!!.nameAccusative,
            updatedName!!.nameInstrumental,
            updatedName!!.namePrepositional
        ).all { it != null }
    }

    private fun isNameUpdated(): Boolean {
        return initialName != updatedName
    }
    
}
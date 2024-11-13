package com.example.prayforthem.prayerdisplay.presentation

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.prayeraddnames.domain.TempPersonInteractor
import com.example.prayforthem.prayerdisplay.domain.PrayerContentInteractor
import com.example.prayforthem.prayerdisplay.domain.PrayerFormatter
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.prayerdisplay.domain.models.PrayersDisplayScreenState
import com.example.prayforthem.utils.NameForms
import com.example.prayforthem.utils.NameFormsConstructor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class PrayerDisplayViewModel(
    private val isForHealth: Boolean,
    private val prayerFileName: String,
    private val prayerContentInteractor: PrayerContentInteractor,
    private val prayerFormatter: PrayerFormatter,
    private val tempPersonInteractor: TempPersonInteractor,
    private val listingInteractor: ListingInteractor
) : ViewModel() {

    private val listOfPerson: ArrayList<PersonDignityName> = arrayListOf()

    internal val screenState =
        MutableLiveData<PrayersDisplayScreenState>(PrayersDisplayScreenState.Loading)

    fun getScreenState(): LiveData<PrayersDisplayScreenState> = screenState

    init {
        screenState.postValue(PrayersDisplayScreenState.Loading)
        prepareContent()
    }

    open fun prepareContent() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processPersonList(
                    listing = listingInteractor.getReservedListingById(getListingId())
                )
                processPrayer(
                    prayer = prayerContentInteractor.getPrayer(prayerFileName),
                )
            }
        }
    }

    private fun processPersonList(listing: ListingWithPerson) {
        listOfPerson.clear()
        listOfPerson.addAll(listing.personListing)
    }

    open fun processPrayer(prayer: PrayerContent) {
        val prayerTitle = prayer.title
        val prayerText = prayerFormatter.composePrayer(prayer)

        val namesNom = if (listOfPerson.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listOfPerson,
            NameForms.NAME_NOMINATIVE
        )

        val namesGen = if (listOfPerson.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listOfPerson,
            NameForms.NAME_GENITIVE
        )
        val namesDat = if (listOfPerson.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listOfPerson,
            NameForms.NAME_DATIVE
        )

        val namesAcc = if (listOfPerson.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listOfPerson,
            NameForms.NAME_ACCUSATIVE
        )

        val namesInstr = if (listOfPerson.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listOfPerson,
            NameForms.NAME_INSTRUMENTAL
        )

        val namesPrep = if (listOfPerson.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listOfPerson,
            NameForms.NAME_PREPOSITIONAL
        )

        val prayerWithNames = prayerText
            .replace(NAMES_NOM, namesNom)
            .replace(NAMES_GEN, namesGen)
            .replace(NAMES_DAT, namesDat)
            .replace(NAMES_ACC, namesAcc)
            .replace(NAMES_INST, namesInstr)
            .replace(NAMES_PREP, namesPrep)
            .replace(PATRIARH_GEN, PATRIARH_NAME_GEN)

        val processedPrayer = Html.fromHtml(prayerWithNames, FROM_HTML_MODE_LEGACY)
        screenState.postValue(
            PrayersDisplayScreenState
                .Content(title = prayerTitle, text = processedPrayer)
        )

    }

    internal fun prepareNameFormsToInsert(
        personList: List<PersonDignityName>,
        nameForm: NameForms
    ): String {
        return NameFormsConstructor.preparePersonListForPrayer(personList, nameForm)
    }

    fun clearTempNames() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tempPersonInteractor.clearAll()
            }
        }
    }

    private fun getListingId(): Int {
        return if (isForHealth) LIST_HEALTH else LIST_REPOSE
    }

    companion object {
        private const val LIST_HEALTH = 1
        private const val LIST_REPOSE = 2
        private const val IMYAREK = "<b><i>(имярек)</i></b>"
        private const val NAMES_NOM = "NAMES_NOM"
        private const val NAMES_GEN = "NAMES_GEN"
        private const val NAMES_DAT = "NAMES_DAT"
        private const val NAMES_ACC = "NAMES_ACC"
        private const val NAMES_INST = "NAMES_INST"
        private const val NAMES_PREP = "NAMES_PREP"
        private const val PATRIARH_GEN = "PATRIARH_GEN"
        private const val PATRIARH_NAME_GEN = "Кирилла"
    }

}
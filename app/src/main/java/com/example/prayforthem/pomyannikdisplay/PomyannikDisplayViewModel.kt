package com.example.prayforthem.pomyannikdisplay

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.prayeraddnames.domain.TempPersonInteractor
import com.example.prayforthem.prayerdisplay.domain.PrayerContentInteractor
import com.example.prayforthem.prayerdisplay.domain.PrayerFormatter
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.prayerdisplay.domain.models.PrayersDisplayScreenState
import com.example.prayforthem.prayerdisplay.presentation.PrayerDisplayViewModel
import com.example.prayforthem.utils.NameForms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PomyannikDisplayViewModel(
    private val prayerFileName: String,
    private val prayerContentInteractor: PrayerContentInteractor,
    private val prayerFormatter: PrayerFormatter,
    private val tempPersonInteractor: TempPersonInteractor,
    private val listingInteractor: ListingInteractor
) : PrayerDisplayViewModel(
    true,
    prayerFileName,
    prayerContentInteractor,
    prayerFormatter,
    tempPersonInteractor,
    listingInteractor
) {
    private val listDuhOtets: ArrayList<PersonDignityName> = arrayListOf()
    private val listParents: ArrayList<PersonDignityName> = arrayListOf()
    private val listRelatives: ArrayList<PersonDignityName> = arrayListOf()
    private val listFriends: ArrayList<PersonDignityName> = arrayListOf()
    private val listReposeRelatives: ArrayList<PersonDignityName> = arrayListOf()

    override fun prepareContent() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processList(
                    listing = listingInteractor.getReservedListingById(LIST_DUH_OTETS),
                    listId = LIST_DUH_OTETS
                )
                processList(
                    listing = listingInteractor.getReservedListingById(LIST_PARENTS),
                    listId = LIST_PARENTS
                )
                processList(
                    listing = listingInteractor.getReservedListingById(LIST_RELATIVES),
                    listId = LIST_RELATIVES
                )
                processList(
                    listing = listingInteractor.getReservedListingById(LIST_DRUGI),
                    listId = LIST_DRUGI
                )
                processList(
                    listing = listingInteractor.getReservedListingById(LIST_REPOSE_RELATIVES),
                    listId = LIST_REPOSE_RELATIVES
                )
                processPrayer(
                    prayer = prayerContentInteractor.getPrayer(prayerFileName),
                )
            }
        }
    }

    override fun processPrayer(prayer: PrayerContent) {
        val prayerTitle = prayer.title
        val prayerText = prayerFormatter.composePrayer(prayer)

        val duhOtets = if (listDuhOtets.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listDuhOtets,
            NameForms.NAME_GENITIVE
        )

        val parents = if (listParents.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listParents,
            NameForms.NAME_GENITIVE
        )

        val relatives = if (listRelatives.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listRelatives,
            NameForms.NAME_GENITIVE
        )

        val friends = if (listFriends.isEmpty()) IMYAREK else prepareNameFormsToInsert(
            listFriends,
            NameForms.NAME_GENITIVE
        )

        val reposeRelatives =
            if (listReposeRelatives.isEmpty()) IMYAREK else prepareNameFormsToInsert(
                listReposeRelatives,
                NameForms.NAME_GENITIVE
            )

        val prayerWithNames = prayerText
            .replace(PATRIARH_GEN, PATRIARH_NAME_GEN)
            .replace(DUHOVN_OTEC_GEN, duhOtets)
            .replace(RODITELI_GEN, parents)
            .replace(SRODNIKI_GEN, relatives)
            .replace(DRUGI_GEN, friends)
            .replace(USOP_ROD_SROD_GEN, reposeRelatives)

        val processedPrayer = Html.fromHtml(prayerWithNames, FROM_HTML_MODE_LEGACY)
        screenState.postValue(
            PrayersDisplayScreenState
                .Content(title = prayerTitle, text = processedPrayer)
        )

    }

    private fun processList(listing: ListingWithPerson, listId: Int) {
        when (listId) {
            LIST_DUH_OTETS -> listDuhOtets.addAll(listing.personListing)
            LIST_PARENTS -> listParents.addAll(listing.personListing)
            LIST_RELATIVES -> listRelatives.addAll(listing.personListing)
            LIST_DRUGI -> listFriends.addAll(listing.personListing)
            LIST_REPOSE_RELATIVES -> listReposeRelatives.addAll(listing.personListing)
        }
    }

    companion object {
        private const val LIST_DRUGI = 1
        private const val LIST_REPOSE_RELATIVES = 2
        private const val LIST_DUH_OTETS = 3
        private const val LIST_PARENTS = 4
        private const val LIST_RELATIVES = 5
        private const val IMYAREK = "<b><i>(имярек)</i></b>"
        private const val PATRIARH_GEN = "PATRIARH_GEN"
        private const val PATRIARH_NAME_GEN = "Кирилла"
        private const val DUHOVN_OTEC_GEN = "DUHOVN_OTEC_GEN"
        private const val RODITELI_GEN = "RODITELI_GEN"
        private const val SRODNIKI_GEN = "SRODNIKI_GEN"
        private const val DRUGI_GEN = "DRUGI_GEN"
        private const val USOP_ROD_SROD_GEN = "USOP_ROD_SROD_GEN"
    }

}
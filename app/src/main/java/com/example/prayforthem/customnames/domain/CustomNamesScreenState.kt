package com.example.prayforthem.customnames.domain

import com.example.prayforthem.names.domain.models.Name

sealed class CustomNamesScreenState {
    data object Loading: CustomNamesScreenState()
    data class Content(val names: List<Name>): CustomNamesScreenState()
    data object Error: CustomNamesScreenState()
}
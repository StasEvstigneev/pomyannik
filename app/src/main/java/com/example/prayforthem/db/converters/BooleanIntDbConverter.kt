package com.example.prayforthem.db.converters

class BooleanIntDbConverter {

    fun map(parameter: Boolean): Int {
        return if (parameter) INT_TRUE else INT_FALSE
    }

    fun map(parameter: Int): Boolean {
        return parameter == INT_TRUE
    }


    companion object {
        private const val INT_TRUE = 1
        private const val INT_FALSE = 0
    }
}
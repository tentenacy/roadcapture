package com.untilled.roadcapture.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object User: KotprefModel() {
    var id by longPref()
}
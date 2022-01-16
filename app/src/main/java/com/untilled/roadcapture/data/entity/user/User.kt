package com.untilled.roadcapture.data.entity.user

import com.chibatching.kotpref.KotprefModel

object User: KotprefModel() {
    var userId by intPref()
}
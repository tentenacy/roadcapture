package com.untilled.roadcapture.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UserDetailResponse

object User: KotprefModel() {
    var id by longPref()
    var email by stringPref()
    var username by stringPref()
    var profileImageUrl by stringPref()
    var introduction by nullableStringPref()
    var provider by nullableStringPref()
    var addressName by stringPref()
    var roadAddressName by nullableStringPref()
    var region1DepthName by stringPref()
    var region2DepthName by stringPref()
    var region3DepthName by stringPref()
    var zoneNo by stringPref()

    fun save(response: UserDetailResponse) {
        id = response.id
        email = response.email
        username = response.username
        profileImageUrl = response.profileImageUrl
        introduction = response.introduction
        provider = response.provider
        addressName = response.address?.addressName ?: ""
        roadAddressName = response.address?.roadAddressName ?: ""
        region1DepthName = response.address?.region1DepthName ?: ""
        region2DepthName = response.address?.region2DepthName ?: ""
        region3DepthName = response.address?.region3DepthName ?: ""
        zoneNo = response.address?.zoneNo ?: ""
    }

    fun save(response: StudioUserResponse) {
        id = response.id
        username = response.username
        profileImageUrl = response.profileImageUrl
        introduction = response.introduction
    }
}
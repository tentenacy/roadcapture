package com.untilled.roadcapture.data.dto.common

data class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        const val INVALID_MESSAGE_RES_ID = -1
    }

    sealed class ResourceState {
        object LOADING : ResourceState() {
            override fun toString() = "loading"
        }

        object SUCCESS : ResourceState() {
            override fun toString() = "success"
        }

        object ERROR : ResourceState() {
            override fun toString() = "error"
        }
    }
}
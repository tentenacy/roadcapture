package com.untilled.roadcapture.data.repository.album

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.untilled.roadcapture.data.dto.album.Albums
import com.untilled.roadcapture.utils.dateToSnsFormat
import retrofit2.HttpException
import java.io.IOException

class AlbumsPagingSource (
    private val repository: AlbumRepository,
    private val dateTimeFrom: String,
    private val dateTimeTo: String)
    : PagingSource<Int, Albums>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Albums> {
        return try{
            val position = params.key ?: STARTING_PAGE_INDEX

            val response = repository.getAlbumsList(
                page = position,
                size = 10,
                dateTimeFrom = dateTimeFrom,
                dateTimeTo = dateTimeTo
            )

            val post = response.body()?.albums
            post?.forEachIndexed { index, albums ->
                albums.createdAt = dateToSnsFormat(albums.createdAt)
            }
            LoadResult.Page(
                data = post!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.body()!!.albums.isNotEmpty()) position + 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Albums>): Int? {
        TODO("Not yet implemented")
    }

    companion object{
        val STARTING_PAGE_INDEX = 0
    }
}
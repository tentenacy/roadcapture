package com.untilled.roadcapture.data.repository.album

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.untilled.roadcapture.data.datasource.api.dto.album.Albums
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.entity.token.Token
import retrofit2.HttpException
import java.io.IOException

class AlbumsPagingSource (
    private val repository: AlbumRepository,
    private val token: String,
    private val dateTimeFrom: String?,
    private val dateTimeTo: String?)
    : PagingSource<Int, Albums>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Albums> {
        return try{
            val position = params.key ?: STARTING_PAGE_INDEX

            val response = repository.getAlbumsList(
                page = position,
                size = 10,
                dateTimeFrom = dateTimeFrom,
                dateTimeTo = dateTimeTo,
            )

            val post = response.body()?.albums

            LoadResult.Page(
                data = post ?: listOf(),
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.body()?.albums?.isNotEmpty() == true) position + 1 else null
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
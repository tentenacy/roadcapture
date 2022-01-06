package com.untilled.roadcapture.data.repository.album

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.untilled.roadcapture.data.dto.comment.Comments
import com.untilled.roadcapture.utils.dateToSnsFormat
import retrofit2.HttpException
import java.io.IOException

class AlbumCommentsPagingSource(
    private val repository: AlbumRepository,
    private val albumId: Int
): PagingSource<Int,Comments>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comments> {
        return try{
            val position = params.key ?: AlbumsPagingSource.STARTING_PAGE_INDEX
            val response = repository.getAlbumCommentsList(
                albumId = albumId,
                page = position,
                size = 10
            )
            val post = response.body()?.comments
            post?.forEachIndexed { index, comments ->
                comments.createdAt = dateToSnsFormat(comments.createdAt)
            }
            LoadResult.Page(
                data = post!!,
                prevKey = if (position == AlbumsPagingSource.STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.body()!!.comments.isNotEmpty()) position + 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Comments>): Int? {
        TODO("Not yet implemented")
    }

}
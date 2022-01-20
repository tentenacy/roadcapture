package com.untilled.roadcapture.data.repository.comment.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.paging.album.AlbumsPagingSource
import com.untilled.roadcapture.data.datasource.paging.comment.AlbumCommentsPagingSource
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class AlbumCommentPagingRepositoryImpl(
    private val pagingSource: AlbumCommentsPagingSource,
): AlbumCommentPagingRepository {

    override fun getAlbumComments(albumId: Long): Flowable<PagingData<AlbumComments.AlbumComment>> {
        pagingSource.albumId = albumId
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }
}
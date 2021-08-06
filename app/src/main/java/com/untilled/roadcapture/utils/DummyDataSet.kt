package com.untilled.roadcapture.utils

import com.untilled.roadcapture.data.entity.Album

object DummyDataSet {
    val albums = listOf(
        Album(
            id = "1",
            username = "tenutz",
            profileUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
            likeCount = "1천",
            commentCount = "39",
            title = "설명이 없는 앨범은 이렇게 보입니다!",
            description = "",
            createdDate = "3분전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2018/07/05/12/24/mountain-3518219_960_720.jpg",
        ),
        Album(
            id = "2",
            username = "apple",
            profileUrl = "https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
            likeCount = "3.2만",
            commentCount = "152",
            title = "여행은 역시 제주도로!",
            description = "저번에 이어 이번에도 제주도로 향했습니다. 이번에는 저만 알고 있는 제주도의 특별 여행 코스를 안내해드리겠습니다!",
            createdDate = "5일전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2017/10/15/13/54/doll-2853763_960_720.jpg",
        ),
        Album(
            id = "3",
            username = "orange",
            profileUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
            likeCount = "1.5천",
            commentCount = "25",
            title = "볼거리가 가득한 국내 여행지 : 곡교천변 은행나무길",
            description = "전국의 아름다운 10대 가로수길 중 하나로 선정된 곡교천변 은행나무길은 현충사 입구의 곡교천 충무교에서부터 현충사 입구까지 조성되어 있는 2.2km 길이의 도로입니다. 가을이면 350여 그루의 은행나무가 일제히 노란 빛을 내 평일에도 많은 인파가 몰려드는 명소입니다.",
            createdDate = "1달전",
            modifiedDate = "",
            thumbnailUrl = "https://t1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/wgtUIZSe7IVwsEGZFg1MNf9CRZ0.jpg",
        ),
    )
}
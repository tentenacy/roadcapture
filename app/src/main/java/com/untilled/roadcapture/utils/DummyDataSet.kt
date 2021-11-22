package com.untilled.roadcapture.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.untilled.roadcapture.data.entity.*
import kotlinx.android.parcel.Parcelize

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
            //thumbnailUrl = "https://cdn.pixabay.com/photo/2018/07/05/12/24/mountain-3518219_960_720.jpg",
            thumbnailUrl = "https://pixabay.com/get/gce3f3dc9eac4aad981d4797691ed701cecf1f74c68f5aea5b9f910a309cf14e625e9e6e7c20f69c6980915f77c8cd5a8c6bb7797d859f0311ae5c3135e6ca4755b1a5075fa5ef24350fb131f3abef660_1920.jpg"
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
            //thumbnailUrl = "https://cdn.pixabay.com/photo/2017/10/15/13/54/doll-2853763_960_720.jpg",
            thumbnailUrl = "https://pixabay.com/get/g2aee644d5f2322f4ad8e410309da0daebc3c018c261e341448a8ed1ddf05a7d2687a4f6ab6c354ac600be5d5b887fb73d893e804aafc431de02ace3ff1b6cc288fa542e44b07a054ff891aad1da72653_1920.jpg"
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
            //thumbnailUrl = "https://t1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/wgtUIZSe7IVwsEGZFg1MNf9CRZ0.jpg",
            thumbnailUrl = "https://pixabay.com/get/ga84b67c4695381e891a97a9a378b3a2ced2dcd72da9ae424a7fe30995e34c719679293c22c6175112f12ff83675627c465e7694928c2b078a0c0cd819061c788b6e2298ed4bbbd2ab24f9612d0306359_1920.jpg"
        ),
    )

    val places = listOf(
        Place(
            image = "https://cdn.pixabay.com/photo/2013/12/31/03/17/namhansanseong-236223_960_720.jpg",
            name = "경기도"
        ),
        Place(
            image = "https://cdn.pixabay.com/photo/2015/09/26/05/06/mt-seoraksan-958642_960_720.jpg",
            name = "강원도"
        ),
        Place(
            image = "https://cdn.pixabay.com/photo/2018/08/23/22/18/jeonju-3626873_960_720.jpg",
            name = "전라도"
        ),
        Place(
            image = "https://cdn.pixabay.com/photo/2015/09/23/08/10/haemieupseong-953103_960_720.jpg",
            name = "충청도"
        ),
        Place(
            image = "https://cdn.pixabay.com/photo/2020/05/24/11/56/to-5213925_960_720.jpg",
            name = "경상도"
        ),
        Place(
            image = "https://images.unsplash.com/photo-1621268698058-ef5c3da2963e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=387&q=80",
            name = "제주도"
        )
    )

    val studios = listOf(
        Album(
            id = "10",
            username = "tenutz",
            profileUrl = "",
            likeCount = "100",
            commentCount = "3",
            title = "마이 스튜디오 제작",
            description = "아직 미완성입니다.",
            createdDate = "하루 전",
            modifiedDate = "",
            thumbnailUrl = "https://mblogthumb-phinf.pstatic.net/20160829_126/miniberry89_1472411951976SASny_PNG/1012xiphone.png?type=w800",
        ),
        Album(
            id = "11",
            username = "tenutz",
            profileUrl = "",
            likeCount = "100",
            commentCount = "3",
            title = "광현 생일",
            description = "제 생일입니다.",
            createdDate = "5개월 전",
            modifiedDate = "",
            thumbnailUrl = "https://s3.ap-northeast-2.amazonaws.com/om-public-static/media/images/products/2020/06/aurorapurplecake_thum.jpg",
        ),
        Album(

            id = "12",
            username = "tenutz",
            profileUrl = "",
            likeCount = "100",
            commentCount = "3",
            title = "개강 첫 날!",
            description = "1교시 수업을 하였습니다.",
            createdDate = "6개월 전",
            modifiedDate = "",
            thumbnailUrl = "data:image/jpeg;base64,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",
        ),
        Album(
            id = "13",
            username = "tenutz",
            profileUrl = "",
            likeCount = "100",
            commentCount = "3",
            title = "2021년이 시작되었습니다.",
            description = "모두 새해 복 많이 받으세요^^",
            createdDate = "1년 전",
            modifiedDate = "",
            thumbnailUrl = "data:image/jpeg;base64,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",
        ),

        Album(
            id = "14",
            username = "tenutz",
            profileUrl = "",
            likeCount = "100",
            commentCount = "3",
            title = "긴 내용",
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            createdDate = "1년 전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2017/10/15/13/54/doll-2853763_960_720.jpg",
        ),
    )

    val comment = listOf(
        Comment(
            username = "hsw0715",
            createdDate = "3분전",
            content = "캡쳐 라이브러리 개발 정말 어렵네요..",
            profileUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"
        ),

        Comment(
            username = "kwangddang11",
            createdDate = "30분전",
            content = "게시물 정말 잘 봤어요!",
            profileUrl = "https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"
        ),

        Comment(
            username = "tentancy",
            createdDate = "3개월전",
            content = "좋아요 누르고 갑니다~",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80"
        ),

        Comment(
            username = "fkdjksl132",
            createdDate = "1년전",
            content = "맞팔 소통해요 ㅎㅎ",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80"
        ),

        Comment(
            username = "looonnnngg",
            createdDate = "3년전",
            content = "read more textview라는 것을 사용했는데 괜찮은 것 같네요 max line을 6줄로 설정하였고 글이 길어지면 이런 식으로 ...으로 설정할 수도 있고 커스텀하여서 \"see more\" 이런 식으로도 작성할 수 있습니다 괜찮은 것 같죠? 자연스럽게 6줄을 채우려고 적고 있는데 아무리 주저리 주저리 적어도 6줄을 채우기는 쉽지 않네요...",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80"
        ),
        Comment(
            username = "hsw0715",
            createdDate = "3분전",
            content = "캡쳐 라이브러리 개발 정말 어렵네요..",
            profileUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"
        ),

        Comment(
            username = "kwangddang11",
            createdDate = "30분전",
            content = "게시물 정말 잘 봤어요!",
            profileUrl = "https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"
        ),

        Comment(
            username = "tentancy",
            createdDate = "3개월전",
            content = "좋아요 누르고 갑니다~",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80"
        ),

        Comment(
            username = "fkdjksl132",
            createdDate = "1년전",
            content = "맞팔 소통해요 ㅎㅎ",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80"
        ),

        Comment(
            username = "looonnnngg",
            createdDate = "3년전",
            content = "내용이 길면 어떻게 될까요?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80"
        ),
        Comment(
            username = "hsw0715",
            createdDate = "3분전",
            content = "캡쳐 라이브러리 개발 정말 어렵네요..",
            profileUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"
        ),

        Comment(
            username = "kwangddang11",
            createdDate = "30분전",
            content = "게시물 정말 잘 봤어요!",
            profileUrl = "https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"
        ),

        Comment(
            username = "tentancy",
            createdDate = "3개월전",
            content = "좋아요 누르고 갑니다~",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80"
        ),

        Comment(
            username = "fkdjksl132",
            createdDate = "1년전",
            content = "맞팔 소통해요 ㅎㅎ",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80"
        ),

        Comment(
            username = "looonnnngg",
            createdDate = "3년전",
            content = "내용이 길면 어떻게 될까요?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80"
        )

    )

    val notification = listOf(
        Album(
            username = "hsw0715",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 앨범을 만들었습니다.",
            createdDate = "3분전",
            modifiedDate = "",
            thumbnailUrl = "https://t1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/wgtUIZSe7IVwsEGZFg1MNf9CRZ0.jpg",
        ),

        Album(
            username = "kwangddang11",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님의 앨범을 좋아합니다.",
            createdDate = "30분전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2017/10/15/13/54/doll-2853763_960_720.jpg",
        ),

        Album(
            username = "tentancy",
            id = "",
            profileUrl = "https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님을 팔로우합니다.",
            createdDate = "1년전",
            modifiedDate = "",
            thumbnailUrl = "",
        ),

        Album(
            username = "tentancy",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님의 앨범에 댓글을 남겼습니다",
            createdDate = "1시간전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2018/07/05/12/24/mountain-3518219_960_720.jpg",
        ),
        Album(
            username = "hsw0715",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 앨범을 만들었습니다.",
            createdDate = "3분전",
            modifiedDate = "",
            thumbnailUrl = "https://t1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/wgtUIZSe7IVwsEGZFg1MNf9CRZ0.jpg",
        ),

        Album(
            username = "kwangddang11",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님의 앨범을 좋아합니다.",
            createdDate = "30분전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2017/10/15/13/54/doll-2853763_960_720.jpg",
        ),

        Album(
            username = "tentancy",
            id = "",
            profileUrl = "https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님을 팔로우합니다.",
            createdDate = "1년전",
            modifiedDate = "",
            thumbnailUrl = "",
        ),

        Album(
            username = "tentancy",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님의 앨범에 댓글을 남겼습니다",
            createdDate = "1시간전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2018/07/05/12/24/mountain-3518219_960_720.jpg",
        ),
        Album(
            username = "hsw0715",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 앨범을 만들었습니다.",
            createdDate = "3분전",
            modifiedDate = "",
            thumbnailUrl = "https://t1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/wgtUIZSe7IVwsEGZFg1MNf9CRZ0.jpg",
        ),

        Album(
            username = "kwangddang11",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님의 앨범을 좋아합니다.",
            createdDate = "30분전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2017/10/15/13/54/doll-2853763_960_720.jpg",
        ),

        Album(
            username = "tentancy",
            id = "",
            profileUrl = "https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님을 팔로우합니다.",
            createdDate = "1년전",
            modifiedDate = "",
            thumbnailUrl = "",
        ),

        Album(
            username = "tentancy",
            id = "",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80",
            likeCount = "",
            commentCount = "",
            title = "",
            description = "님이 작가님의 앨범에 댓글을 남겼습니다",
            createdDate = "1시간전",
            modifiedDate = "",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2018/07/05/12/24/mountain-3518219_960_720.jpg",
        ),
    )

    val user = listOf(
        User(
            username = "hsw0715",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80",
        ),
        User(
            username = "kwangddang11",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80"
        ),
        User(
            username = "tentancy",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80"
        ),
        User(
            username = "hsw0715",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80"
        ),
        User(
            username = "kwangddang11",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80"
        ),
        User(
            username = "tentancy",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80"
        ),
        User(
            username = "hsw0715",
            profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80"
        ),
        User(
            username = "kwangddang11",
            profileUrl = "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=344&q=80"
        ),
        User(
            username = "tentancy",
            profileUrl = "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=333&q=80"
        )
    )

    val picture = listOf(
        Picture(
            imageUri = "https://cdn.pixabay.com/photo/2018/07/05/12/24/mountain-3518219_960_720.jpg",
            date = "9월 24일",
            searchResult = null,
            name = null,
            description = "PictureViewer 테스트 중입니다."
        ),
        Picture(
            imageUri = "https://cdn.pixabay.com/photo/2017/10/15/13/54/doll-2853763_960_720.jpg",
            date = "5월 19일",
            searchResult = null,
            name = null,
            description = "저번에 이어 이번에도 제주도로 향했습니다. 이번에는 저만 알고 있는 제주도의 특별 여행 코스를 안내해드리겠습니다!"
        ),
        Picture(
            imageUri = "https://t1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/wgtUIZSe7IVwsEGZFg1MNf9CRZ0.jpg",
            date = "4월 11일",
            searchResult = null,
            name = null,
            description = "전국의 아름다운 10대 가로수길 중 하나로 선정된 곡교천변 은행나무길은 현충사 입구의 곡교천 충무교에서부터 현충사 입구까지 조성되어 있는 2.2km 길이의 도로입니다. 가을이면 350여 그루의 은행나무가 일제히 노란 빛을 내 평일에도 많은 인파가 몰려드는 명소입니다."
        )
    )

    val studioUser = User(
        username = "kvvangddang",
        profileUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80",
        backgroundUrl = "https://pixabay.com/get/g2aee644d5f2322f4ad8e410309da0daebc3c018c261e341448a8ed1ddf05a7d2687a4f6ab6c354ac600be5d5b887fb73d893e804aafc431de02ace3ff1b6cc288fa542e44b07a054ff891aad1da72653_1920.jpg",
        description = "안녕하세요 저는 최광현입니다."
    )

}
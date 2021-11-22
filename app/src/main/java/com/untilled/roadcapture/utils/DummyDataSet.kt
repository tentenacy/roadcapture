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
            thumbnailUrl = "https://mblogthumb-phinf.pstatic.net/MjAxOTAxMDJfMjcz/MDAxNTQ2NDM5MjA3NjM2.qZOKA4N7NWbBjNH-ZcWdSvDXDAqpFbPc1_WGoY_A9gsg.6Vd7g-wE1Mt0Afe9_JwdONfQ8w9qo5OSOpq_7hbJQOQg.PNG.comeinto_/SE-c6d9cab3-86b1-4cfa-a3a1-72db7764c20e.png?type=w800",
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
            thumbnailUrl = "https://mblogthumb-phinf.pstatic.net/MjAxOTAxMDJfMjg1/MDAxNTQ2NDM5MjA5NDkx.KlJz_JE814P9bjsdYtf7lOLhON-MPDLvgveGDuv5mnog.DnF_JGAjkuOBGpEksGO11pvaF1cJ8YTa_ryffxMTx9Yg.PNG.comeinto_/SE-a303eff9-aa83-4dc5-b2ea-b3459a31de53.png?type=w800",
        ),
        Album(

            id = "12",
            username = "tenutz",
            profileUrl = "",
            likeCount = "100",
            commentCount = "3",
            title = "제목이 길면 어떻게 될까요???????????????????",
            description = "1교시 수업을 하였습니다.",
            createdDate = "6개월 전",
            modifiedDate = "",
            thumbnailUrl = "https://mblogthumb-phinf.pstatic.net/MjAxOTAxMDJfMTg1/MDAxNTQ2NDM5MjEzODI0.9v4NAMbYyVr_86gPd_-D4n3EsJpXQteCuPyhngbKCE0g.L6neteSOx3JVK1oKld0oo0Mt1_IdhrvdLINQiAa01BMg.PNG.comeinto_/SE-c013fe0e-dad9-4aeb-a1fd-bae00add7a71.png?type=w800",
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
            thumbnailUrl = "https://mblogthumb-phinf.pstatic.net/MjAxOTAxMDJfMyAg/MDAxNTQ2NDM5MjE4OTU4.NdSqgiRwXMB23Z0cIeCd-9mlgjCaoB3iwwCSVr_bbOIg.GbdlII4SlA__5puJpVDRcqp5nOT2Q11qd3e0tYivsYAg.PNG.comeinto_/SE-7d33c08e-ef63-4a29-8cbb-b29deee55121.png?type=w800",
        )
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
        backgroundUrl = "https://mblogthumb-phinf.pstatic.net/MjAxOTAxMDJfODMg/MDAxNTQ2NDM5MjIwOTE5.5qxiNSGpSnjZ-KC_2Qz9MUMhsf84ydJ9NXlEk8vclJ4g.3fByHBJcHRjla2Y3LUZVxjW26_NObEp_52PmYmoMjL8g.PNG.comeinto_/SE-b386b581-67d9-4da3-a037-dd19c54509c5.png?type=w800",
        description = "안녕하세요 저는 최광현입니다."
    )

}
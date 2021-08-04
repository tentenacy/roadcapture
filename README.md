<p align="center">
	<img src="https://user-images.githubusercontent.com/76826021/128172146-d09b1b6a-3289-4be3-85c3-dedd97e26dd4.png" alt="App Logo" width="25%" height="25%"/>
</p>

# Road Capture

로드캡처는 위치 서비스를 기반으로 앨범을 만들어 간단하게 여행기를 서로 공유할 수 있는  SNS입니다.



## Environment

* 안드로이드 6.0 (Marshmallow) 이상 Sdk 버전에서 실행할 수 있습니다.



## Features

TODO: 우리 앱만의 주요 기능 작성



## Technologies

### MVVM

이 설계는 일관되고 즐거운 사용자 환경을 제공합니다. 사용자가 앱을 마지막으로 닫은지 몇 분 후 또는 며칠 후에 다시 사용하는지와 관계없이 앱이 로컬에 보존하는 사용자의 정보가 바로 표시됩니다. 이 데이터가 오래된 경우 앱의 저장소 모듈이 백그라운드에서 데이터 업데이트를 시작합니다.



<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png?hl=ko" alt="img" style="zoom:50%;" />



* View(Activity / Fragment)
  * 프래그먼트, 관련 레이아웃 파일로 구성됩니다.
  * 객체 변경사항을 감지하여 UI가 새로고침됩니다.
* ViewModel
  * `Fragment`나 `Activity` 같은 특정 UI 구성요소에 관한 데이터를 제공하고 모델과 커뮤니케이션하기 위한 데이터 처리 비즈니스 로직을 포함합니다.
  * `LiveData`를 사용하여 `Fragment`나 `Activity` 같은 특정 UI 구성요소 간에 명시적이고 엄격한 종속성 경로를 만들지 않고도 객체 변경사항을 모니터링할 수 있습니다.
* Model(Repository)
  * 여러 개의 다른 클래스에 종속되는 유일한 클래스입니다.
  * 위의 예처럼 지속 데이터 모델과 원격 백엔드 데이터 소스에 종속될 수 있습니다.



### Dagger2 + Hilt

Hilt는 프로젝트에서 수동 종속 항목 삽입을 실행하는 상용구를 줄이는 Android용 종속 항목 삽입 라이브러리입니다. Dagger가 제공하는 컴파일 시간 정확성, 런타임 성능, 확장성 및 [Android 스튜디오 지원](https://medium.com/androiddevelopers/dagger-navigation-support-in-android-studio-49aa5d149ec9)의 이점을 누리기 위해 인기 있는 DI 라이브러리 [Dagger](https://developer.android.com/training/dependency-injection/dagger-basics?hl=ko)를 기반으로 빌드되었습니다.



### Retrofit2

Retrofit2는 Android와 Java를 위한 type-safe한 HTTP 클라이언트입니다. HTTP 기반의 요청 및 응답을 쉽게 할 수 있도록 도와주는 오픈소스 라이브러리인 Okhttp3 기반으로 동작합니다.



### Coroutine

Android에서 사용할 수 있는 동시 실행 설계 패턴으로서 비동기적으로 실행되는 코드를 간소화합니다.



### Navigation

인앱 UI를 빌드 및 구조화하고 딥 링크를 처리하며 화면 간에 이동합니다.



### DataBinding

레이아웃의 UI 구성요소를 선언적 형식을 사용하여 앱의 데이터 소스에 결합합니다.



### Room

SQLite 데이터베이스에서 지원하는 영구 데이터를 생성, 저장 및 관리합니다.



### Camera

모바일 카메라 앱을 빌드합니다.



### **Paging**

페이지에 데이터를 로드하여 RecyclerView에 표시합니다.



### WorkManager

지연 가능한 제약 조건 기반 백그라운드 작업을 예약하고 실행합니다.



## Dependencies

TODO: 의존하는 라이브러리들



## License


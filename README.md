# RXCAssignment
RXC Android Engineer Position 코딩 과제 by 서혁범 <br />


## Development Environment
- Android Studio Electric Eel | 2022.1.1 Patch 1
- Compile Sdk Version: 33
- Gradle Plugin Version: 7.3.1
- Gradle Version 8.0
- Language: Kotlin


## Application Version
- minSdkVersion : 23
- targetSdkVersion : 33


## Introduction
1. Clean Architecture + MVVM structure 기반, UDA 개념 참조 하여 설계 되었음
2. 2*N Scrollable GridLayout, 상품 리스트 표현 
3. 각 상품 찜 버튼 및 상태 저장
4. Light & Dark theme 호환
5. release build 를 위한 .jks(keystore) 파일을 프로젝트 내 위치
6. 로딩 상태 애니메이션 확인을 위해 리스트 호출시 임의로 지연 시간 두었음(3000ms)


## APIs
### Dagger
Dagger Hilt 는 DI 를 위한 Android Jetpack 의 권장 라이브러리이다. 안드로이드 프레임워크에서 표준적으로 사용되는 DI 컴포넌트와 scope를 기본적으로 제공하여, 초기 DI 구축 비용을 절감 및 보일러 플레이트 코드를 줄여 안드로이드 프레임워크에서 클래스간 의존성을 낮추는 데 도움을 줌 <br />

### Coil 
여러 이미지 로딩 라이브러리가 존재하지만 Kotlin Base(Kotlin Coroutines)로 만들어져 kotlin 친화적일 것이라 생각해 사용하게 되었으며 성능상 다른 라이브러리와 드라마틱한 차이가 나지 않는 점과 Jetpack Compose 도 쉽게 사용이 가능한 점 때문에 해당 프로젝트에도 적용 되었음 <br />

### Retrofit
HTTP 통신을 할 때 많이 사용되는 Third-Party library. OkHttp 를 네트워크 레이어로 활용하여 그 위에 레이어를 한단계 더 추가해 만든 라이브러리 이다. 저수준 네트워크 명령을 OkHttp 에서 처리하고, 그 위 abstraction layer 를 구현해 간결하고 사용하기 편리함 <br />


## Preview
![open](https://github.com/hyeok713/RXCAssignment/assets/72484451/8b013986-546d-4e12-b7ca-791295c17418)
![dark](https://github.com/hyeok713/RXCAssignment/assets/72484451/a6245091-1c0e-489f-b0ba-efc4f06c807f)
![like](https://github.com/hyeok713/RXCAssignment/assets/72484451/af0080c2-c9d6-44d6-bbcf-9ab55cf85bf4)

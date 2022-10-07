# test3

AduJungBock Smart_Application
---------------------------------------------------------
# 기능
1. 아두이노에서 블루투스 시리얼통신을 통해 어플리케이션으로 전송, 출력해주는 프로그램  
2. 아두이노 전등 스위치 On/Off
3. 블루투스 아두이노 RC카 제어 컨트롤러 
# 개발 기간
- 2019.5.1 ~ 2019.6.19
--------------------------------------------------------
# AduJungBock Aduino
## 1. 개발 환경
- Windows 10 Home 64Bit / Arduino IDE

## 2. 개발 보드
- Aduino nano

## 3. 부품
- 블루투스 모듈(HC-06) <img src="https://images-na.ssl-images-amazon.com/images/I/61RwNwc8P9L._SX425_.jpg" width="100">

## 4. 구조
## 5. 회로도

-----------------------------
# AduJungBock Smart_Application
## 1. 개발 환경
- Windows 10 Home 64Bit(1809) / Android Studio
- Galaxy S8 Star(android 8.0 Oreo)
- Galaxy ZFlip3 (android 12)

## 2. 구성 및 설명
- [IntroActivity.java](https://github.com/seonyeongBEAK/test3/blob/master/app/src/main/java/com/adu/test3/IntroActivity.java) 
, [activity_intro.xml](https://github.com/seonyeongBEAK/test3/blob/master/app/src/main/res/layout/activity_intro.xml)
   - 첫화면을 나타내는 인트로 액티비티
   - 3초동안 앱 첫 화면 보여주고 블루투스 연결 할 수 있는 InitialActivity 화면으로 이동

- [InitialActivity.java](https://github.com/seonyeongBEAK/test3/blob/master/app/src/main/java/com/adu/test3/InitialActivity.java) 
, [activity_initial.xml](https://github.com/seonyeongBEAK/test3/blob/master/app/src/main/res/layout/activity_initial.xml)
   - 블루투스 연결을 하는 액티비티
   - 앱 실행시 자동으로 블루투스 리스트 출력
   - Bluetooth Connect : 블루투스 리스트 출력, HC-06(AduinoNano에 연결된 블루투스 모듈) 선택 후 연결 성공 시 MainActivity로 이동
   - MainActivity로 이동 : 블루투스 서비스로 연결 MainActivity로 이동, 각 컨텐츠 선택 화면
   
<img src="./screenshot/Initial1.jpg" width="300">    <img src="./screenshot/Initial2.jpg" width="300">
- [MainActivity.java]
, [activity_main.xml]
   

- [BluetoothService.java]
- [DeviceListActivity.java],
[device_list.xml]
, [device_name.xml]


## 3. 저작권 및 출처
   - image [aduino board : https://prinsta.io/electronics-lab/make-arduino-development-board]
   - lottie [https://lottiefiles.com/] : https://assets4.lottiefiles.com/packages/lf20_m71u1phy.json
   - font [카페 24 한글폰트] : https://fonts.cafe24.com/
   - android studio vector Asset

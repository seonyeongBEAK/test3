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
- [MainActivity.java](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/java/my/final_project/MainActivity.java) , [activity_main.xml](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/res/layout/activity_main.xml)
   - Atmega128로부터 받아온 센서(온.습도, 미세먼지)데이터 출력
   - 미세먼지 데이터는 수치 기준(좋음, 보통, 나쁨, 매우나쁨)에 따라 글자색이 다르게 출력
   - 사진 배경은 시간대에 따라 사진이 변경됨
   - 전등 제어 On, Off 버튼 클릭시 Atmega128에 "On", "Off" 명령 전송
   - 알람 설정 체크 박스 활성화 시 알람 시간 설정 버튼이 생성되고 버튼 클릭 시 TimePickerDialog에 나온 시간을 선택 후 확인을 누르면 설정한 시간이 알람 시간으로 설정 됨
   - 알람 설정 시 Notification 생성
   - 우측 하단 FloatingActionButton 클릭 시 TimePickermode와 Alarmmode를 선택할 수 있는 AlertDialog 생성
   
<img src="./screenshot/Main1.jpg" width="300">   <img src="./screenshot/Main2.jpg" width="300">   <img src="./screenshot/Main3.jpg" width="300">   <img src="./screenshot/Main4.jpg" width="300">
- [AlarmActivity.java](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/java/my/final_project/AlarmActivity.java) , [activity_alarm.xml](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/res/layout/activity_alarm.xml)
   - 알람 시간이 되었을 때 실행되는 액티비티
   - 액티비티 상단 실시간 시간 출력
   - 설정한 알람 모드에 따라 음악과 사진 혹은 GIF가 다르게 출력
   - 하단 알람 종료 버튼 클릭 시 액티비티 종료
   
<img src="./screenshot/Alarm1.jpg" width="300">   <img src="./screenshot/Alarm2.jpg" width="300">
- [WeatherActivity.java](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/java/my/final_project/WeatherActivity.java) , [activity_weather.xml](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/res/layout/activity_weather.xml)
   - MainActivity에서 미세먼지 데이터 클릭 시 실행되는 액티비티
   - MainActivity에서 미세먼지 데이터를 가져온 값에 따라 다르게 화면이 출력
   
<img src="./screenshot/Weather1.jpg" width="300">   <img src="./screenshot/Weather2.jpg" width="300">
- [AlarmReceiver.java](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/java/my/final_project/AlarmReceiver.java)
- [AlarmService.java](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/java/my/final_project/AlarmService.java)
- [BluetoothService.java](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/java/my/final_project/BluetoothService.java
- [DeviceListActivity.java](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/java/my/final_project/DeviceListActivity.java), [device_list.xml](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/res/layout/device_list.xml) , [device_name.xml](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/res/layout/device_name.xml)
- [PushWakeLock.java](https://github.com/psy1064/SmartHomeIoT/blob/master/SmartHomeIoT_Application/app/src/main/java/my/final_project/PushWakeLock.java)


## 3. 저작권 및 출처
   - image [aduino board : https://prinsta.io/electronics-lab/make-arduino-development-board]
   - lottie [https://lottiefiles.com/] : https://assets4.lottiefiles.com/packages/lf20_m71u1phy.json
   - font [카페 24 한글폰트] : https://fonts.cafe24.com/
   - android studio vector Asset

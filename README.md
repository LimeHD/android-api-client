# Модуль для взаимодействия с API

[![Build Status](https://ci.iptv2022.com/app/rest/builds/buildType(id:Android_root_ApiModule_ApiModuleMasterBranch)/statusIcon)](https://ci.iptv2022.com/viewType.html?buildTypeId=Android_root_ApiModule_ApiModuleMasterBranch&guest=1)
[![Build Status](https://ci.iptv2022.com/app/rest/builds/buildType(id:Android_root_ApiModule_ApiModulePullRequests)/statusIcon)](https://ci.iptv2022.com/viewType.html?buildTypeId=Android_root_ApiModule_ApiModulePullRequests&guest=1)

## Присоединение к модулю

### 0. Клонировать репозиторий при помощи git submodule

git submodule add https://github.com/LimeHD/android-api-module

### 1. Добавить модуль к проекту
Файл-Структура Проекта-Модули

Нажмите + кнопку

1. Добавить модуль androidapimodule без DEMO модуля
2. Demo модуль его пример приложения из использования androidapimodule

### 2. Добавить в dependencies

``` js
implementation project(':androidapimodule')
```

## Примеры использования
Перед использованием необходимо добавить в файл модуль `LimeApiClient`
``` java
import tv.limehd.androidapimodule.LimeApiClient;
```
### Получение версии модуля
```java
String versionCode = LimeApiClient.getVersionCode(context);
String versionName = LimeApiClient.getVersionName(context);
```

### Инициализация `LimeApiClient`
```java
String api_root = API_ROOT;
String package_name = getPackageName();
String example_x_access_token = "example_x_access_token";
String locale = getResources().getConfiguration().locale.getLanguage();
Context context = getApplicationContext();
File fileCacheDir = getCacheDir();
String device_id = LimeApiClient.getDeviceId(context);
LimeApiClient limeApiClient = new LimeApiClient(context, device_id, api_root, scheme, package_name, example_x_access_token, locale, fileCacheDir);
ApiValues apiValues = new ApiValues();

```
Если требуется обновить локаль:
``` java
limeApiClient.upDateLocale(locale);
```

### Получение списка каналов
Пример запроса
``` java
limeApiClient.downloadChannelList();
limeApiClient.setDownloadChannelListCallBack(new LimeApiClient.DownloadChannelListCallBack() {
    @Override
    public void downloadChannelListSuccess(String response) {
	// ответ
    }

    @Override
    public void downloadChannelListError(String message) {
	// ошибка
    }
});
```
### Настройка дат для получения программы передач
``` java
String before_date = LimeRFC.timeStampToRFC(before_date_timestamp);
String after_date = LimeRFC.timeStampToRFC(after_date_timestamp);
```
### Получения программы передач
Пример запроса
``` java
String example_channel_id = "105";
//Тайм зона в формате UTC offset
String example_time_zone = "UTC+03:00";

limeApiClient.downloadBroadcast(example_channel_id, before_date, after_date, example_time_zone);
limeApiClient.setDownloadBroadCastCallBack(new LimeApiClient.DownloadBroadCastCallBack() {
    @Override
    public void downloadBroadCastSuccess(String response) {
	// ответ
    }

    @Override
    public void downloadBroadCastError(String message) {
	// ошибка
    }
});
```

### Запрос сессии
Пример запроса
``` java
limeApiClient.downloadSession();
limeApiClient.setDownloadSessionCallBack(new LimeApiClient.DownloadSessionCallBack() {
     @Override
     public void downloadSessionSuccess(String response) {
         // ответ
     }

     @Override
     public void downloadSessionError(String message) {
	 // ошика
     }
});
```

### Пинг
Пример запроса
``` java
limeApiClient.downloadPing();
limeApiClient.setDownloadPingCallBack(new LimeApiClient.DownloadPingCallBack() {
     @Override
     public void downloadPingSuccess(String response) {
         // ответ
     }

     @Override
     public void downloadPingError(String message) {
	 // ошика
     }
});
```
### DeepClicks
Пример запроса
``` java
limeApiClient.downloadDeepClicks(query, path, false); //false - без кэша 
limeApiClient.setDownloadDeepClicksCallBack(new LimeApiClient.DownloadDeepClicksCallBack() {
      @Override
      public void sendingDeepClicksSuccess(String response) {
          //ответ         
      }

      @Override
      public void sendingDeepClicksError(String message) {
          //ошибка             
      }
 });
```

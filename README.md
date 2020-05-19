# Модуль для взаимодействия с API

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
### Инициализация `LimeApiClient`
```java
String api_root = API_ROOT;
LimeApiClient limeApiClient = new LimeApiClient(api_root);
ApiValues apiValues = new ApiValues();
```
### Получение списка каналов
Пример запроса
``` java
limeApiClient.downloadChannelList(apiValues.getSCHEME_HTTP(), apiValues.getURL_CHANNELS_GRECE_PATH());
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
``` js
String before_date = LimeRFC.timeStampToRFC(before_date_timestamp);
String after_date = LimeRFC.timeStampToRFC(after_date_timestamp);
```
### Получения программы передач
Пример запроса
``` java
limeApiClient.downloadBroadcast(apiValues.getSCHEME_HTTP(), apiValues.getURL_BROADCAST_PATH()
                ,"105", before_date, after_date, "Asia/Kolkata");
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

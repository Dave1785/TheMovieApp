
![moviedb_icon](https://github.com/Dave1785/TheMovieApp/assets/8507644/951c952f-ed50-49d2-a479-5cf112d887a4)


The MovieDB app consume TheMovieApi to show the most popular user, the movies sorted by popularity, ranked and recommended. This app use Hilt for dependency injection combine with MVVM
arquitecture and Coroutines to handle asyncronus tasks. We can navigate through the app using a bottom navigation bar, we can be notify when the WiFi connection is lost using a Broadcast 
receiver and notify to the user with a Toast message. The app consume a FireBaseDataBase of locations to show in a local map and track the device location to FireBase every 5 minutes using a worker 
manager also we can upload images to FireBaseStorage from gallery or camera. The app save the data in a room a database to cosume in case of the user lose WiFi connection. 

- Retrofit
- NavigationComponent
- WorkerManager
- Room
- Hilt
- Coroutines
- Firebase DataBase
- Firebase Storage

Warnings:

 - For use FireBase we need to create an account and create a database for consume and storage for upload files.
 - For use TheMovieDBApi we need to create an account to have access.
 - Create an account in GoogleCloud to enable maps and get map_api_key.

Improvements:

 - Best handling of denied permissions.
 - Best handling of pagination calls.
 - Best handling of api token.
 - Best handling of image url.

Disclaimer:

 - I have to use api 23 to use FireBaseStorage android studio give me this error when i try to compile with api 21 uses-sdk:minSdkVersion 21 cannot be smaller than version 23 declared
   in library [com.google.firebase:firebase-auth-ktx:23.0.0]

ProfilePage

![challenge_1](https://github.com/Dave1785/TheMovieApp/assets/8507644/c4bcbf8f-a373-4599-9e59-98e1c934f9e1)

MoviesPage

![challenge_2](https://github.com/Dave1785/TheMovieApp/assets/8507644/9901640a-92ee-41e4-85ac-2502ff2cc4d5)

MapsPage

![challenge_4](https://github.com/Dave1785/TheMovieApp/assets/8507644/90143a52-0319-42e6-9a4e-7684222cfab1)

UploadFilePage

![upload_file](https://github.com/Dave1785/TheMovieApp/assets/8507644/3f6f55d4-b5d2-4c90-b54d-1fb3295d2655)

FireBaseConsole

<img width="1434" alt="Screenshot 2024-06-17 at 4 08 50 a m" src="https://github.com/Dave1785/TheMovieApp/assets/8507644/db9aadbd-b789-4d5c-9b46-03b156a39ad8">

GoogleCloudConsole

<img width="1431" alt="Screenshot 2024-06-17 at 4 09 48 a m" src="https://github.com/Dave1785/TheMovieApp/assets/8507644/45adb424-1bf3-474f-be63-e9cdbba35a2c">

# Leanplum-Android-CustomTemplates
Custom message templates example for Android

This project illustrates how to define a new message/action template in Leanplum. The samples include defining a 3-button Confirm message and a Slider (Pager) template. They are available in **Java**.

#### [Slider (Pager)](#Slider)
#### [3-button Alert](#3-button-Confirm)
#### [App Review Request](#App-Rating)

## Installation
Checkout repository and open with Android Studio. Enter your APP_ID and APP_DEVKEY in [App.java file][13].
Run project on emulator or device.

Sync the new message templates to the Dashboard from Messages:  
![Sync Message templates][2]

## Documentation

Full Documentation can be found in [Leanplum Docs][1].

## Setup

Once the templates are synced, they will appear in the Campaign Composer under Custom In-App section:   
![Custom In-App section][3]

### 3-button Confirm

Composer:  
![Alert Composer][4]  
Message:  
<img src="/readme_images/3-buttons.png" alt="Confirm Message" height="800px">  
Results:  
![Alert Results][6]  
Milestones:  
![Alert Milestones][7]  

### Slider

Composer:  
![Slider Composer][8]  
Message:  
<img src="/readme_images/slider_anim.gif" alt="Slider Message" height="900px">  
Milestones:  
![Slider Milestones][10]  

### App Review Request

App Rating for Android is implemented using the [In-App Review API][11] of the Play Core SDK.
Full documentation about the App Review functionality can be found in [Leanplum Docs][12].


[1]: https://docs.leanplum.com/reference#section-android-custom-templates
[2]: /readme_images/sync.png
[3]: /readme_images/custom_templates.png
[4]: /readme_images/3-buttons_composer.png
[5]: /readme_images/3-buttons.png
[6]: /readme_images/3-buttons_result.png
[7]: /readme_images/3-buttons_milestones_missing.png
[8]: /readme_images/slider_composer.png
[9]: /readme_images/slider_anim.gif
[10]: /readme_images/slider_milestones.png
[11]: https://developer.android.com/guide/playcore/in-app-review
[12]: https://docs.leanplum.com/docs/app-review-request#android-50
[13]: /app/src/main/java/com/leanplum/App.java

package com.leanplum;

import android.app.Application;
import com.leanplum.annotations.Parser;
import com.leanplum.customtemplates.AppRating;
import com.leanplum.customtemplates.Confirm3Buttons;
import com.leanplum.customtemplates.SliderTemplate;
import com.leanplum.internal.Log;
import com.leanplum.internal.Log.Level;
import com.leanplum.messagetemplates.MessageTemplates;

public class App extends Application {

  private static final String APP_ID = "";
  private static final String APP_DEVKEY = "";
  private static final String APP_PRODKEY = "";

  @Override
  public void onCreate() {
    super.onCreate();
    initLeanplum();
  }

  private void initLeanplum() {
    Leanplum.setApplicationContext(this);
    Leanplum.setLogLevel(Level.DEBUG);
    Parser.parseVariables(this);
    LeanplumActivityHelper.enableLifecycleCallbacks(this);
    Leanplum.setAppIdForDevelopmentMode(APP_ID, APP_DEVKEY);
//    Leanplum.setAppIdForProductionMode(APP_ID, APP_PRODKEY);
    initCustomTemplates();
    Leanplum.start(this);
  }

  private void initCustomTemplates() {
    MessageTemplates.registerAction(new AppRating(), this);
    MessageTemplates.registerTemplate(new Confirm3Buttons(), this);
    MessageTemplates.registerTemplate(new SliderTemplate(), this);
  }
}

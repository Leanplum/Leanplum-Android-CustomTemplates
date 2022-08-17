package com.leanplum.customtemplates;

import android.app.Activity;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import com.leanplum.ActionContext;
import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.callbacks.ActionCallback;
import com.leanplum.messagetemplates.controllers.WebInterstitialController;
import com.leanplum.messagetemplates.options.WebInterstitialOptions;

/**
 * Overrides the default 'Web Interstitial' template to support JavaScript inside WebView.
 */
public class CustomWebInterstitial extends WebInterstitialController {

  public CustomWebInterstitial(Activity activity, @NonNull WebInterstitialOptions options) {
    super(activity, options);
    ViewGroup viewGroup = contentView.findViewById(com.leanplum.core.R.id.container_view);
    WebView webView = (WebView) viewGroup.getChildAt(0);
    webView.getSettings().setJavaScriptEnabled(true);
  }

  private static WebInterstitialController webInterstitial;

  public static void register() {
    Leanplum.defineAction("Web Interstitial",
        Leanplum.ACTION_KIND_MESSAGE | Leanplum.ACTION_KIND_ACTION,
        WebInterstitialOptions.toArgs(),
        // presentHandler:
        new ActionCallback() {
          @Override
          public boolean onResponse(ActionContext actionContext) {
            Activity activity = LeanplumActivityHelper.getCurrentActivity();
            if (activity == null || activity.isFinishing()) {
              return false;
            }

            WebInterstitialOptions options = new WebInterstitialOptions(actionContext);
            webInterstitial = new WebInterstitialController(activity, options);
            webInterstitial.setOnDismissListener(listener -> {
              webInterstitial = null;
              actionContext.actionDismissed();
            });
            webInterstitial.show();

            return true;
          }
        },
        // dismissHandler:
        new ActionCallback() {
          @Override
          public boolean onResponse(ActionContext context) {
            if (webInterstitial != null) {
              webInterstitial.dismiss();
            }
            return true;
          }
        });
  }
}

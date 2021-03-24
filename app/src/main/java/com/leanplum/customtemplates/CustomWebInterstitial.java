package com.leanplum.customtemplates;

import android.app.Activity;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import com.leanplum.ActionContext;
import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.callbacks.ActionCallback;
import com.leanplum.callbacks.PostponableAction;
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

  public static void register() {
    Leanplum.defineAction("Web Interstitial",
        Leanplum.ACTION_KIND_MESSAGE | Leanplum.ACTION_KIND_ACTION,
        WebInterstitialOptions.toArgs(),
        new ActionCallback() {
          @Override
          public boolean onResponse(ActionContext actionContext) {
            LeanplumActivityHelper.queueActionUponActive(
                new PostponableAction() {
                  @Override
                  public void run() {
                    Activity activity = LeanplumActivityHelper.getCurrentActivity();
                    if (activity == null || activity.isFinishing()) {
                      return;
                    }
                    WebInterstitialOptions options = new WebInterstitialOptions(actionContext);
                    CustomWebInterstitial customWebInterstitial = new CustomWebInterstitial(activity, options);
                    customWebInterstitial.show();
                  }
                });
            return true;
          }
        });
  }
}

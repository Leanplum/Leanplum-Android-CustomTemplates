package com.leanplum.customtemplates;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import com.leanplum.ActionArgs;
import com.leanplum.ActionContext;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.messagetemplates.MessageTemplate;

import static com.leanplum.messagetemplates.MessageTemplateConstants.Args;
import static com.leanplum.messagetemplates.MessageTemplateConstants.Values;

/**
 * Defines a message template for 3-button Confirm Message
 */
public class Confirm3Buttons implements MessageTemplate {

  private static final String NAME = "3-buttons Confirm";

  private static final String MAYBE_TEXT_ARG = "Maybe text";
  private static final String MAYBE_TEXT_VALUE = "Maybe";
  private static final String MAYBE_ACTION_ARG = "Maybe action";

  private AlertDialog alertDialog;

  @NonNull
  @Override
  public String getName() {
    return NAME;
  }

  @NonNull
  @Override
  public ActionArgs createActionArgs(Context context) {
    return new ActionArgs()
        .with(Args.TITLE, context.getString(R.string.app_name))
        .with(Args.MESSAGE, Values.CONFIRM_MESSAGE)
        .with(Args.ACCEPT_TEXT, Values.YES_TEXT)
        .with(Args.CANCEL_TEXT, Values.NO_TEXT)
        .with(MAYBE_TEXT_ARG, MAYBE_TEXT_VALUE) // Adding Text argument for Maybe button
        .withAction(Args.ACCEPT_ACTION, null)
        .withAction(Args.CANCEL_ACTION, null)
        .withAction(MAYBE_ACTION_ARG, null); // Adding Action for Maybe button
  }

  @Override
  public boolean present(@NonNull ActionContext context) {
    Activity activity = LeanplumActivityHelper.getCurrentActivity();
    if (activity == null || activity.isFinishing())
      return false;

    alertDialog = new AlertDialog.Builder(activity)
        .setTitle(context.stringNamed(Args.TITLE))
        .setMessage(context.stringNamed(Args.MESSAGE))
        .setCancelable(false)
        .setPositiveButton(
            context.stringNamed(Args.ACCEPT_TEXT),
            (dialog, id) -> {
              alertDialog = null;
              context.runTrackedActionNamed(Args.ACCEPT_ACTION);
              context.actionDismissed();
            })
        .setNegativeButton(
            context.stringNamed(Args.CANCEL_TEXT),
            (dialog, id) -> {
              alertDialog = null;
              context.runActionNamed(Args.CANCEL_ACTION);
              context.actionDismissed();
            })
        .setNeutralButton(
            context.stringNamed(MAYBE_TEXT_ARG),
            (dialog, id) -> {
              alertDialog = null;
              context.runActionNamed(MAYBE_ACTION_ARG);
              context.actionDismissed();
            })
        .create();
    alertDialog.show();
    return true;
  }

  @Override
  public boolean dismiss(@NonNull ActionContext context) {
    if (alertDialog != null) {
      alertDialog.dismiss();
      alertDialog = null;
      context.actionDismissed();
    }
    return true;
  }
}

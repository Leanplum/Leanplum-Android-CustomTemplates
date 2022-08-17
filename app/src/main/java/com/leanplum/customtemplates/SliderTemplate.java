package com.leanplum.customtemplates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.leanplum.ActionArgs;
import com.leanplum.ActionContext;
import com.leanplum.Leanplum;
import com.leanplum.customtemplates.slider.SlideData;
import com.leanplum.customtemplates.slider.SliderActivity;
import com.leanplum.internal.FileManager;
import com.leanplum.messagetemplates.MessageTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a message template containing several slides similar to on-boarding message.
 *
 * If you need more than 6 slides change the {@link #MAX_SLIDES} constant as it is needed by
 * the Leanplum dashboard to define the template correctly. If you do not assign an image for
 * particular slide it won't be visualized.
 */
public class SliderTemplate implements MessageTemplate {
  private static final int MAX_SLIDES = 6;
  private static final String NAME = "Slider";

  private static final String BG_COLOR_ARG = "Background Color";
  private static final String TITLE_COLOR_ARG = "Title Color";
  private static final String TITLE_ARG_PREFIX = "Titles.Title ";
  private static final String IMAGE_ARG_PREFIX = "Images.Image ";

  @NonNull
  @Override
  public String getName() {
    return NAME;
  }

  @NonNull
  @Override
  public ActionArgs createActionArgs(@NonNull Context context) {
    ActionArgs actionArgs = new ActionArgs();
    actionArgs.withColor(BG_COLOR_ARG, Color.BLACK);
    actionArgs.withColor(TITLE_COLOR_ARG, Color.BLUE);

    for (int i = 1; i <= MAX_SLIDES; i++) {
      actionArgs.with(TITLE_ARG_PREFIX + i, "");
      actionArgs.withFile(IMAGE_ARG_PREFIX + i, null);
    }
    return actionArgs;
  }

  @Override
  public boolean present(@NonNull ActionContext actionContext) {
    // Register receiver for the activity finish event
    LocalBroadcastManager.getInstance(Leanplum.getContext())
        .registerReceiver(new BroadcastReceiver() {
          @Override
          public void onReceive(Context context, Intent intent) {
            LocalBroadcastManager.getInstance(Leanplum.getContext()).unregisterReceiver(this);
            actionContext.actionDismissed();
          }
        }, new IntentFilter(SliderActivity.SLIDER_DISMISSED));

    // Start activity
    List<SlideData> slides = createSlides(actionContext);
    startSliderActivity(slides);
    return true;
  }

  @Override
  public boolean dismiss(@NonNull ActionContext context) {
    // Dismiss can't be implemented because Slider is implemented using Activity and switching
    // activity calls this method to avoid Dialog object leaks in in-apps, which will prevent
    // showing the Slider.
    return true;
  }

  private List<SlideData> createSlides(ActionContext actionContext) {
    int bgColor = actionContext.numberNamed(BG_COLOR_ARG).intValue();
    int titleColor = actionContext.numberNamed(TITLE_COLOR_ARG).intValue();

    List<SlideData> slides = new ArrayList<>();

    int position = 0;
    for (int i = 1; i <= MAX_SLIDES; i++) {
      String title = actionContext.stringNamed(TITLE_ARG_PREFIX + i);
      String image = actionContext.stringNamed(IMAGE_ARG_PREFIX + i);

      // Add slide only if a content image is configured
      if (!TextUtils.isEmpty(image)) {
        SlideData slide = new SlideData();
        slide.title = title;
        slide.imagePath = getPathForImage(image);
        slide.position = position++;
        slide.backgroundColor = bgColor;
        slide.titleColor = titleColor;
        slides.add(slide);
      }
    }
    return slides;
  }

  private String getPathForImage(String imageName) {
    if (TextUtils.isEmpty(imageName))
      return null;
    return FileManager.fileValue(imageName, imageName, null);
  }

  private void startSliderActivity(List<SlideData> data) {
    Context context = Leanplum.getContext();
    Intent intent = new Intent(context, SliderActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra(
        SliderActivity.INTENT_SLIDES_DATA,
        data.toArray(new SlideData[0]));
    context.startActivity(intent);
  }
}

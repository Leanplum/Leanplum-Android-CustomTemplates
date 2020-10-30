package com.leanplum.customtemplates.slider;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.leanplum.customtemplates.R;
import java.util.ArrayList;
import java.util.List;

public class IndicatorView extends LinearLayout {
  private static final int SIZE_SELECTED_DP = 10;
  private static final int SIZE_SMALL_DP = 6;
  private static final int SIZE_WITH_MARGIN_DP = 16;

  private int activePosition = 0;
  private List<View> indicators = new ArrayList<>();

  public IndicatorView(Context context) {
    super(context);
  }

  public IndicatorView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private int dpToPx(int dp) {
    float density = getContext().getResources().getDisplayMetrics().density;
    return Math.round((float) dp * density);
  }

  private void applySize(View view, int sizeDp) {
    int size = dpToPx(sizeDp);
    int margin = dpToPx((SIZE_WITH_MARGIN_DP - sizeDp) / 2);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
    params.leftMargin = margin;
    params.rightMargin = margin;
    params.gravity = Gravity.CENTER;
    view.setLayoutParams(params);
  }

  private View createIndicator() {
    View view = new View(getContext());
    applySize(view, SIZE_SMALL_DP);
    view.setBackgroundResource(R.drawable.indicator_gray);
    return view;
  }

  public void setIndicatorsCount(int count) {
    for (int i = 0; i < count; i++) {
      View indicator = createIndicator();
      addView(indicator);
      indicators.add(indicator);
    }
  }

  public void setActiveIndicator(int position) {
    View active = indicators.get(activePosition);
    applySize(active, SIZE_SMALL_DP);
    active.setBackgroundResource(R.drawable.indicator_gray);

    View newActive = indicators.get(position);
    applySize(newActive, SIZE_SELECTED_DP);
    newActive.setBackgroundResource(R.drawable.indicator_selected);
    activePosition = position;
  }

}

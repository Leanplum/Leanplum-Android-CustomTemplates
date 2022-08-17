package com.leanplum.customtemplates.slider;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import com.leanplum.customtemplates.R;
import com.leanplum.views.CloseButton;

public class SliderActivity extends AppCompatActivity {

  public static final String SLIDER_DISMISSED = "SliderDismissed";
  public static final String INTENT_SLIDES_DATA = "SLIDES_DATA";
  private SlideData[] slides;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().hide();
    setContentView(R.layout.activity_slider);

    slides = (SlideData[]) getIntent().getExtras().get(INTENT_SLIDES_DATA);
    initViews();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // Communicate back to MessageTemplate to notify ActionContext
    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SLIDER_DISMISSED));
  }

  private void initViews() {
    CloseButton closeButton = findViewById(R.id.close_button);
    closeButton.setOnClickListener(v -> finish());

    IndicatorView indicatorView = findViewById(R.id.indicator_view);
    indicatorView.setIndicatorsCount(slides.length);
    indicatorView.setActiveIndicator(0);

    ViewPager2 pager = findViewById(R.id.view_pager);
    pager.setAdapter(new FragmentStateAdapter(this) {
      @Override
      public int getItemCount() {
        return slides.length;
      }
      @NonNull
      @Override
      public Fragment createFragment(int position) {
        return SlideFragment.create(slides[position]);
      }
    });

    pager.registerOnPageChangeCallback(new OnPageChangeCallback() {
      @Override
      public void onPageSelected(int position) {
        indicatorView.setActiveIndicator(position);
      }
    });
  }
}

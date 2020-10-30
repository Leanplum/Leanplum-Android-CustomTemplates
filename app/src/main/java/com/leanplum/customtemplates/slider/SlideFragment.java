package com.leanplum.customtemplates.slider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.leanplum.customtemplates.R;

public class SlideFragment extends Fragment {

  private static final String KEY_DATA = "DATA";

  public static SlideFragment create(SlideData slide) {
    SlideFragment fragment = new SlideFragment();
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_DATA, slide);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.slide_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View container, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(container, savedInstanceState);
    SlideData slide = (SlideData) requireArguments().getSerializable(KEY_DATA);

    container.setBackgroundColor(slide.backgroundColor);

    if (slide.title != null) {
      TextView title = container.findViewById(R.id.title);
      title.setText(slide.title);
      title.setTextColor(slide.titleColor);
    }

    if (slide.imagePath != null) {
      Bitmap bitmap = BitmapFactory.decodeFile(slide.imagePath);
      ImageView image = container.findViewById(R.id.image);
      image.setImageBitmap(bitmap);
    }
  }
}

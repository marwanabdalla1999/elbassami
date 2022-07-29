// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SocialLoginActivity_ViewBinding implements Unbinder {
  private SocialLoginActivity target;

  private View view7f090108;

  private View view7f09010e;

  private View view7f090093;

  @UiThread
  public SocialLoginActivity_ViewBinding(SocialLoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SocialLoginActivity_ViewBinding(final SocialLoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_fb, "method 'onViewClicked'");
    view7f090108 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_gmail, "method 'onViewClicked'");
    view7f09010e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.backBtn, "method 'onViewClicked'");
    view7f090093 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view7f090108.setOnClickListener(null);
    view7f090108 = null;
    view7f09010e.setOnClickListener(null);
    view7f09010e = null;
    view7f090093.setOnClickListener(null);
    view7f090093 = null;
  }
}

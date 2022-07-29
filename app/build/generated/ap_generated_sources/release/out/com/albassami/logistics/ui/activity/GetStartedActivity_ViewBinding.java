// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.Spinner;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GetStartedActivity_ViewBinding implements Unbinder {
  private GetStartedActivity target;

  private View view7f0905d8;

  @UiThread
  public GetStartedActivity_ViewBinding(GetStartedActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GetStartedActivity_ViewBinding(final GetStartedActivity target, View source) {
    this.target = target;

    View view;
    target.spCountryReg = Utils.findRequiredViewAsType(source, R.id.sp_country_reg, "field 'spCountryReg'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.welcome_btn, "field 'welcomeBtn' and method 'onViewClicked'");
    target.welcomeBtn = Utils.castView(view, R.id.welcome_btn, "field 'welcomeBtn'", CustomRegularTextView.class);
    view7f0905d8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    GetStartedActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spCountryReg = null;
    target.welcomeBtn = null;

    view7f0905d8.setOnClickListener(null);
    view7f0905d8 = null;
  }
}

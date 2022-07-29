// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NoInternetActivity_ViewBinding implements Unbinder {
  private NoInternetActivity target;

  @UiThread
  public NoInternetActivity_ViewBinding(NoInternetActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NoInternetActivity_ViewBinding(NoInternetActivity target, View source) {
    this.target = target;

    target.close = Utils.findRequiredViewAsType(source, R.id.close, "field 'close'", CustomRegularTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NoInternetActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.close = null;
  }
}

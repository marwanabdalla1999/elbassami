// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignInActivity_ViewBinding implements Unbinder {
  private SignInActivity target;

  private View view7f09025a;

  private View view7f090313;

  private View view7f09045a;

  @UiThread
  public SignInActivity_ViewBinding(SignInActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignInActivity_ViewBinding(final SignInActivity target, View source) {
    this.target = target;

    View view;
    target.phone = Utils.findRequiredViewAsType(source, R.id.user_phone, "field 'phone'", CustomRegularEditView.class);
    target.code = Utils.findRequiredViewAsType(source, R.id.countrycode, "field 'code'", AutoCompleteTextView.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", CustomRegularEditView.class);
    view = Utils.findRequiredView(source, R.id.forgotPassword, "field 'forgotPassword' and method 'onViewClicked'");
    target.forgotPassword = Utils.castView(view, R.id.forgotPassword, "field 'forgotPassword'", CustomRegularTextView.class);
    view7f09025a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login, "field 'login' and method 'onViewClicked'");
    target.login = Utils.castView(view, R.id.login, "field 'login'", Button.class);
    view7f090313 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.signUplayout, "field 'signUpactivity' and method 'onViewClicked'");
    target.signUpactivity = Utils.castView(view, R.id.signUplayout, "field 'signUpactivity'", LinearLayout.class);
    view7f09045a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.inputLayoutPassword = Utils.findRequiredViewAsType(source, R.id.inputLayoutPassword, "field 'inputLayoutPassword'", TextInputLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignInActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.phone = null;
    target.code = null;
    target.password = null;
    target.forgotPassword = null;
    target.login = null;
    target.signUpactivity = null;
    target.inputLayoutPassword = null;

    view7f09025a.setOnClickListener(null);
    view7f09025a = null;
    view7f090313.setOnClickListener(null);
    view7f090313 = null;
    view7f09045a.setOnClickListener(null);
    view7f09045a = null;
  }
}

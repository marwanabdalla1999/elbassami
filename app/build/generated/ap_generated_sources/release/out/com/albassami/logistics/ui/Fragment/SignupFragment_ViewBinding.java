// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import com.hbb20.CountryCodePicker;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignupFragment_ViewBinding implements Unbinder {
  private SignupFragment target;

  private View view7f0903eb;

  private View view7f090102;

  @UiThread
  public SignupFragment_ViewBinding(SignupFragment target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignupFragment_ViewBinding(final SignupFragment target, View source) {
    this.target = target;

    View view;
    target.ccp = Utils.findRequiredViewAsType(source, R.id.ccp, "field 'ccp'", CountryCodePicker.class);
    target.userMobileNuber = Utils.findRequiredViewAsType(source, R.id.user_mobile_nuber, "field 'userMobileNuber'", CustomRegularEditView.class);
    view = Utils.findRequiredView(source, R.id.redirectLogin, "field 'redirectLogin' and method 'onViewClicked'");
    target.redirectLogin = Utils.castView(view, R.id.redirectLogin, "field 'redirectLogin'", CustomRegularTextView.class);
    view7f0903eb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_confirm_phone, "method 'onViewClicked'");
    view7f090102 = view;
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
    SignupFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ccp = null;
    target.userMobileNuber = null;
    target.redirectLogin = null;

    view7f0903eb.setOnClickListener(null);
    view7f0903eb = null;
    view7f090102.setOnClickListener(null);
    view7f090102 = null;
  }
}

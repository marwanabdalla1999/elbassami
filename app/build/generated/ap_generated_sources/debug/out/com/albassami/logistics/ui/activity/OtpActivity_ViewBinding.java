// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class OtpActivity_ViewBinding implements Unbinder {
  private OtpActivity target;

  private View view7f090105;

  private View view7f090365;

  private View view7f090101;

  private View view7f09026a;

  @UiThread
  public OtpActivity_ViewBinding(OtpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OtpActivity_ViewBinding(final OtpActivity target, View source) {
    this.target = target;

    View view;
    target.etOtpMobile = Utils.findRequiredViewAsType(source, R.id.et_otp_mobile, "field 'etOtpMobile'", CustomRegularEditView.class);
    view = Utils.findRequiredView(source, R.id.btn_edit_number, "field 'btnEditNumber' and method 'onViewClicked'");
    target.btnEditNumber = Utils.castView(view, R.id.btn_edit_number, "field 'btnEditNumber'", ImageView.class);
    view7f090105 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.userOtp = Utils.findRequiredViewAsType(source, R.id.user_otp, "field 'userOtp'", CustomRegularEditView.class);
    view = Utils.findRequiredView(source, R.id.notRecevied, "field 'notRecevied' and method 'onViewClicked'");
    target.notRecevied = Utils.castView(view, R.id.notRecevied, "field 'notRecevied'", CustomRegularTextView.class);
    view7f090365 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_confirm_otp, "field 'btnConfirmOtp' and method 'onViewClicked'");
    target.btnConfirmOtp = Utils.castView(view, R.id.btn_confirm_otp, "field 'btnConfirmOtp'", Button.class);
    view7f090101 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ccp = Utils.findRequiredViewAsType(source, R.id.ccp, "field 'ccp'", CountryCodePicker.class);
    view = Utils.findRequiredView(source, R.id.getOtp, "field 'getOtp' and method 'onViewClicked'");
    target.getOtp = Utils.castView(view, R.id.getOtp, "field 'getOtp'", TextView.class);
    view7f09026a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.otpDemoNote = Utils.findRequiredViewAsType(source, R.id.otpDemoNote, "field 'otpDemoNote'", CustomRegularTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OtpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etOtpMobile = null;
    target.btnEditNumber = null;
    target.userOtp = null;
    target.notRecevied = null;
    target.btnConfirmOtp = null;
    target.ccp = null;
    target.getOtp = null;
    target.otpDemoNote = null;

    view7f090105.setOnClickListener(null);
    view7f090105 = null;
    view7f090365.setOnClickListener(null);
    view7f090365 = null;
    view7f090101.setOnClickListener(null);
    view7f090101 = null;
    view7f09026a.setOnClickListener(null);
    view7f09026a = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ForgotPasswordBottomSheet_ViewBinding implements Unbinder {
  private ForgotPasswordBottomSheet target;

  private View view7f09044c;

  private View view7f09016b;

  @UiThread
  public ForgotPasswordBottomSheet_ViewBinding(final ForgotPasswordBottomSheet target,
      View source) {
    this.target = target;

    View view;
    target.email = Utils.findRequiredViewAsType(source, R.id.yourEmail, "field 'email'", EditText.class);
    view = Utils.findRequiredView(source, R.id.sendConfirmationEmail, "method 'sendEmailClicked'");
    view7f09044c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.sendEmailClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.closeBtn, "method 'closeSheet'");
    view7f09016b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.closeSheet();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ForgotPasswordBottomSheet target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.email = null;

    view7f09044c.setOnClickListener(null);
    view7f09044c = null;
    view7f09016b.setOnClickListener(null);
    view7f09016b = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChangePasswordActivity_ViewBinding implements Unbinder {
  private ChangePasswordActivity target;

  private View view7f090153;

  @UiThread
  public ChangePasswordActivity_ViewBinding(ChangePasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChangePasswordActivity_ViewBinding(final ChangePasswordActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.currentPassword = Utils.findRequiredViewAsType(source, R.id.currentPassword, "field 'currentPassword'", EditText.class);
    target.newPassword = Utils.findRequiredViewAsType(source, R.id.newPassword, "field 'newPassword'", EditText.class);
    target.newPasswordConfirm = Utils.findRequiredViewAsType(source, R.id.newPasswordConfirm, "field 'newPasswordConfirm'", EditText.class);
    view = Utils.findRequiredView(source, R.id.changePasswordButton, "method 'changePasswordClicked'");
    view7f090153 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.changePasswordClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ChangePasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.currentPassword = null;
    target.newPassword = null;
    target.newPasswordConfirm = null;

    view7f090153.setOnClickListener(null);
    view7f090153 = null;
  }
}

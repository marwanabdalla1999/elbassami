// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IncompletePaymentDialog_ViewBinding implements Unbinder {
  private IncompletePaymentDialog target;

  private View view7f09038a;

  private View view7f09038d;

  @UiThread
  public IncompletePaymentDialog_ViewBinding(final IncompletePaymentDialog target, View source) {
    this.target = target;

    View view;
    target.amount = Utils.findRequiredViewAsType(source, R.id.amount, "field 'amount'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.payDue, "field 'payDue' and method 'onViewClicked'");
    target.payDue = Utils.castView(view, R.id.payDue, "field 'payDue'", CustomRegularTextView.class);
    view7f09038a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.paymentMode, "method 'onViewClicked'");
    view7f09038d = view;
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
    IncompletePaymentDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.amount = null;
    target.payDue = null;

    view7f09038a.setOnClickListener(null);
    view7f09038a = null;
    view7f09038d.setOnClickListener(null);
    view7f09038d = null;
  }
}

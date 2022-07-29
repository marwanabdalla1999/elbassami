// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DefaultPaymentModesAdapter$PaymentModeViewHolder_ViewBinding implements Unbinder {
  private DefaultPaymentModesAdapter.PaymentModeViewHolder target;

  @UiThread
  public DefaultPaymentModesAdapter$PaymentModeViewHolder_ViewBinding(
      DefaultPaymentModesAdapter.PaymentModeViewHolder target, View source) {
    this.target = target;

    target.paymentModeName = Utils.findRequiredViewAsType(source, R.id.paymentModeName, "field 'paymentModeName'", TextView.class);
    target.paymentModeSelected = Utils.findRequiredViewAsType(source, R.id.paymentModeSelected, "field 'paymentModeSelected'", ImageView.class);
    target.paymentModePhoto = Utils.findRequiredViewAsType(source, R.id.paymentModePhoto, "field 'paymentModePhoto'", ImageView.class);
    target.root = Utils.findRequiredViewAsType(source, R.id.paymentModeRoot, "field 'root'", ViewGroup.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DefaultPaymentModesAdapter.PaymentModeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.paymentModeName = null;
    target.paymentModeSelected = null;
    target.paymentModePhoto = null;
    target.root = null;
  }
}

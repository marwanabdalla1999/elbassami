// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CreditCardAdapter$CreditCardViewHolder_ViewBinding implements Unbinder {
  private CreditCardAdapter.CreditCardViewHolder target;

  @UiThread
  public CreditCardAdapter$CreditCardViewHolder_ViewBinding(
      CreditCardAdapter.CreditCardViewHolder target, View source) {
    this.target = target;

    target.cardLastFour = Utils.findRequiredViewAsType(source, R.id.cardLastFour, "field 'cardLastFour'", TextView.class);
    target.cardType = Utils.findRequiredViewAsType(source, R.id.cardType, "field 'cardType'", TextView.class);
    target.cardSelected = Utils.findRequiredViewAsType(source, R.id.cardSelected, "field 'cardSelected'", ImageView.class);
    target.deleteCard = Utils.findRequiredViewAsType(source, R.id.deleteCard, "field 'deleteCard'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CreditCardAdapter.CreditCardViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cardLastFour = null;
    target.cardType = null;
    target.cardSelected = null;
    target.deleteCard = null;
  }
}

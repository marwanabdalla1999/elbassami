// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PaymentsActivity_ViewBinding implements Unbinder {
  private PaymentsActivity target;

  private View view7f090060;

  @UiThread
  public PaymentsActivity_ViewBinding(PaymentsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PaymentsActivity_ViewBinding(final PaymentsActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.cardsRecycler = Utils.findRequiredViewAsType(source, R.id.cardsRecycler, "field 'cardsRecycler'", RecyclerView.class);
    target.noCardsLayout = Utils.findRequiredView(source, R.id.empty_cards_layout, "field 'noCardsLayout'");
    view = Utils.findRequiredView(source, R.id.add_card, "field 'addCard' and method 'addCard'");
    target.addCard = Utils.castView(view, R.id.add_card, "field 'addCard'", TextView.class);
    view7f090060 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addCard();
      }
    });
    target.emptyIcon = Utils.findRequiredViewAsType(source, R.id.emptyImage, "field 'emptyIcon'", ImageView.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.emptyLayout, "field 'emptyLayout'", LinearLayout.class);
    target.savedCardsLayout = Utils.findRequiredViewAsType(source, R.id.savedCardsLayout, "field 'savedCardsLayout'", LinearLayout.class);
    target.paymentModesRecycler = Utils.findRequiredViewAsType(source, R.id.paymentModesRecycler, "field 'paymentModesRecycler'", RecyclerView.class);
    target.emptyPaymentModeLayout = Utils.findRequiredViewAsType(source, R.id.empty_payment_mode_layout, "field 'emptyPaymentModeLayout'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PaymentsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.cardsRecycler = null;
    target.noCardsLayout = null;
    target.addCard = null;
    target.emptyIcon = null;
    target.emptyLayout = null;
    target.savedCardsLayout = null;
    target.paymentModesRecycler = null;
    target.emptyPaymentModeLayout = null;

    view7f090060.setOnClickListener(null);
    view7f090060 = null;
  }
}

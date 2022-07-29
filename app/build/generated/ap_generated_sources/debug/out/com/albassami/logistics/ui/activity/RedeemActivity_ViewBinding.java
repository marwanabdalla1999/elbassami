// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RedeemActivity_ViewBinding implements Unbinder {
  private RedeemActivity target;

  private View view7f09044b;

  private View view7f0905cf;

  @UiThread
  public RedeemActivity_ViewBinding(RedeemActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RedeemActivity_ViewBinding(final RedeemActivity target, View source) {
    this.target = target;

    View view;
    target.recentTransactionRecycler = Utils.findRequiredViewAsType(source, R.id.recentTransactionRecycler, "field 'recentTransactionRecycler'", RecyclerView.class);
    target.remainingAmount = Utils.findRequiredViewAsType(source, R.id.remainingAmount, "field 'remainingAmount'", CustomBoldRegularTextView.class);
    target.totalAmount = Utils.findRequiredViewAsType(source, R.id.totalAmount, "field 'totalAmount'", CustomBoldRegularTextView.class);
    target.redeemedAmount = Utils.findRequiredViewAsType(source, R.id.redeemedAmount, "field 'redeemedAmount'", CustomBoldRegularTextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.sendBtn, "field 'sendBtn' and method 'onViewClicked'");
    target.sendBtn = Utils.castView(view, R.id.sendBtn, "field 'sendBtn'", CardView.class);
    view7f09044b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.viewMore, "field 'viewMore' and method 'setViewMore'");
    target.viewMore = Utils.castView(view, R.id.viewMore, "field 'viewMore'", CustomRegularTextView.class);
    view7f0905cf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setViewMore();
      }
    });
    target.noData = Utils.findRequiredViewAsType(source, R.id.noData, "field 'noData'", CustomRegularTextView.class);
    target.emptyData = Utils.findRequiredViewAsType(source, R.id.emptyData, "field 'emptyData'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RedeemActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recentTransactionRecycler = null;
    target.remainingAmount = null;
    target.totalAmount = null;
    target.redeemedAmount = null;
    target.toolbar = null;
    target.sendBtn = null;
    target.viewMore = null;
    target.noData = null;
    target.emptyData = null;

    view7f09044b.setOnClickListener(null);
    view7f09044b = null;
    view7f0905cf.setOnClickListener(null);
    view7f0905cf = null;
  }
}

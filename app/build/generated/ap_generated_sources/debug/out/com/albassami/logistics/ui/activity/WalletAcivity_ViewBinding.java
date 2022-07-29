// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WalletAcivity_ViewBinding implements Unbinder {
  private WalletAcivity target;

  private View view7f09005e;

  private View view7f090543;

  private View view7f0903eb;

  private View view7f0905cf;

  @UiThread
  public WalletAcivity_ViewBinding(WalletAcivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WalletAcivity_ViewBinding(final WalletAcivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.addMoney, "field 'addMoney' and method 'onViewClicked'");
    target.addMoney = Utils.castView(view, R.id.addMoney, "field 'addMoney'", LinearLayout.class);
    view7f09005e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.transaction, "field 'transaction' and method 'onViewClicked'");
    target.transaction = Utils.castView(view, R.id.transaction, "field 'transaction'", LinearLayout.class);
    view7f090543 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.redeem, "field 'redeem' and method 'onViewClicked'");
    target.redeem = Utils.castView(view, R.id.redeem, "field 'redeem'", LinearLayout.class);
    view7f0903eb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.recentTransactionRecycler = Utils.findRequiredViewAsType(source, R.id.recentTransactionRecycler, "field 'recentTransactionRecycler'", RecyclerView.class);
    target.remaining = Utils.findRequiredViewAsType(source, R.id.remaining, "field 'remaining'", CustomBoldRegularTextView.class);
    target.total = Utils.findRequiredViewAsType(source, R.id.total, "field 'total'", CustomBoldRegularTextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.viewMore, "field 'viewMore' and method 'onViewClicked'");
    target.viewMore = Utils.castView(view, R.id.viewMore, "field 'viewMore'", CustomRegularTextView.class);
    view7f0905cf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.noData = Utils.findRequiredViewAsType(source, R.id.noData, "field 'noData'", CustomRegularTextView.class);
    target.empty_data = Utils.findRequiredViewAsType(source, R.id.emptyData, "field 'empty_data'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WalletAcivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.addMoney = null;
    target.transaction = null;
    target.redeem = null;
    target.recentTransactionRecycler = null;
    target.remaining = null;
    target.total = null;
    target.toolbar = null;
    target.viewMore = null;
    target.noData = null;
    target.empty_data = null;

    view7f09005e.setOnClickListener(null);
    view7f09005e = null;
    view7f090543.setOnClickListener(null);
    view7f090543 = null;
    view7f0903eb.setOnClickListener(null);
    view7f0903eb = null;
    view7f0905cf.setOnClickListener(null);
    view7f0905cf = null;
  }
}

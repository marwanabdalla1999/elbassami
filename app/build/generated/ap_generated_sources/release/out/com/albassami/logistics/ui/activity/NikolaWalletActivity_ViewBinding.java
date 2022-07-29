// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NikolaWalletActivity_ViewBinding implements Unbinder {
  private NikolaWalletActivity target;

  private View view7f0900fe;

  private View view7f0905d4;

  private View view7f09056e;

  private View view7f09056f;

  private View view7f090570;

  @UiThread
  public NikolaWalletActivity_ViewBinding(NikolaWalletActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NikolaWalletActivity_ViewBinding(final NikolaWalletActivity target, View source) {
    this.target = target;

    View view;
    target.toolbarWallet = Utils.findRequiredViewAsType(source, R.id.toolbar_wallet, "field 'toolbarWallet'", Toolbar.class);
    target.tvTotalBalance = Utils.findRequiredViewAsType(source, R.id.tv_total_balance, "field 'tvTotalBalance'", CustomRegularTextView.class);
    target.etEnterAmount = Utils.findRequiredViewAsType(source, R.id.et_enter_amount, "field 'etEnterAmount'", CustomRegularEditView.class);
    view = Utils.findRequiredView(source, R.id.btn_add_money, "field 'btnAddMoney' and method 'onViewClicked'");
    target.btnAddMoney = Utils.castView(view, R.id.btn_add_money, "field 'btnAddMoney'", Button.class);
    view7f0900fe = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.wallet_back, "method 'onViewClicked'");
    view7f0905d4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_1, "method 'onViewClicked'");
    view7f09056e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_2, "method 'onViewClicked'");
    view7f09056f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_3, "method 'onViewClicked'");
    view7f090570 = view;
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
    NikolaWalletActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbarWallet = null;
    target.tvTotalBalance = null;
    target.etEnterAmount = null;
    target.btnAddMoney = null;

    view7f0900fe.setOnClickListener(null);
    view7f0900fe = null;
    view7f0905d4.setOnClickListener(null);
    view7f0905d4 = null;
    view7f09056e.setOnClickListener(null);
    view7f09056e = null;
    view7f09056f.setOnClickListener(null);
    view7f09056f = null;
    view7f090570.setOnClickListener(null);
    view7f090570 = null;
  }
}

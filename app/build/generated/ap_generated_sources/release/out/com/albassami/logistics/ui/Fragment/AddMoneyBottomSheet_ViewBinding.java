// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddMoneyBottomSheet_ViewBinding implements Unbinder {
  private AddMoneyBottomSheet target;

  private View view7f09036c;

  private View view7f0905a9;

  private View view7f09050b;

  private View view7f09025c;

  private View view7f090254;

  private View view7f090460;

  private View view7f090453;

  private View view7f0901e3;

  private View view7f09035b;

  private View view7f090165;

  private View view7f0905e3;

  private View view7f0901ce;

  private View view7f09005e;

  @UiThread
  public AddMoneyBottomSheet_ViewBinding(final AddMoneyBottomSheet target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.one, "field 'one' and method 'onViewClicked'");
    target.one = Utils.castView(view, R.id.one, "field 'one'", CardView.class);
    view7f09036c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.two, "field 'two' and method 'onViewClicked'");
    target.two = Utils.castView(view, R.id.two, "field 'two'", CardView.class);
    view7f0905a9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.three, "field 'three' and method 'onViewClicked'");
    target.three = Utils.castView(view, R.id.three, "field 'three'", CardView.class);
    view7f09050b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.four, "field 'four' and method 'onViewClicked'");
    target.four = Utils.castView(view, R.id.four, "field 'four'", CardView.class);
    view7f09025c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.five, "field 'five' and method 'onViewClicked'");
    target.five = Utils.castView(view, R.id.five, "field 'five'", CardView.class);
    view7f090254 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.six, "field 'six' and method 'onViewClicked'");
    target.six = Utils.castView(view, R.id.six, "field 'six'", CardView.class);
    view7f090460 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.seven, "field 'seven' and method 'onViewClicked'");
    target.seven = Utils.castView(view, R.id.seven, "field 'seven'", CardView.class);
    view7f090453 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.eight, "field 'eight' and method 'onViewClicked'");
    target.eight = Utils.castView(view, R.id.eight, "field 'eight'", CardView.class);
    view7f0901e3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nine, "field 'nine' and method 'onViewClicked'");
    target.nine = Utils.castView(view, R.id.nine, "field 'nine'", CardView.class);
    view7f09035b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.clearAmt, "field 'clearAmt' and method 'onViewClicked'");
    target.clearAmt = Utils.castView(view, R.id.clearAmt, "field 'clearAmt'", CardView.class);
    view7f090165 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.zero, "field 'zero' and method 'onViewClicked'");
    target.zero = Utils.castView(view, R.id.zero, "field 'zero'", CardView.class);
    view7f0905e3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.dot, "field 'dot' and method 'onViewClicked'");
    target.dot = Utils.castView(view, R.id.dot, "field 'dot'", CardView.class);
    view7f0901ce = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.moneyText = Utils.findRequiredViewAsType(source, R.id.moneyText, "field 'moneyText'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.addMoney, "field 'addMoney' and method 'onViewClicked'");
    target.addMoney = Utils.castView(view, R.id.addMoney, "field 'addMoney'", Button.class);
    view7f09005e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.balance = Utils.findRequiredViewAsType(source, R.id.balance, "field 'balance'", CustomBoldRegularTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddMoneyBottomSheet target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.one = null;
    target.two = null;
    target.three = null;
    target.four = null;
    target.five = null;
    target.six = null;
    target.seven = null;
    target.eight = null;
    target.nine = null;
    target.clearAmt = null;
    target.zero = null;
    target.dot = null;
    target.moneyText = null;
    target.addMoney = null;
    target.toolbar = null;
    target.balance = null;

    view7f09036c.setOnClickListener(null);
    view7f09036c = null;
    view7f0905a9.setOnClickListener(null);
    view7f0905a9 = null;
    view7f09050b.setOnClickListener(null);
    view7f09050b = null;
    view7f09025c.setOnClickListener(null);
    view7f09025c = null;
    view7f090254.setOnClickListener(null);
    view7f090254 = null;
    view7f090460.setOnClickListener(null);
    view7f090460 = null;
    view7f090453.setOnClickListener(null);
    view7f090453 = null;
    view7f0901e3.setOnClickListener(null);
    view7f0901e3 = null;
    view7f09035b.setOnClickListener(null);
    view7f09035b = null;
    view7f090165.setOnClickListener(null);
    view7f090165 = null;
    view7f0905e3.setOnClickListener(null);
    view7f0905e3 = null;
    view7f0901ce.setOnClickListener(null);
    view7f0901ce = null;
    view7f09005e.setOnClickListener(null);
    view7f09005e = null;
  }
}

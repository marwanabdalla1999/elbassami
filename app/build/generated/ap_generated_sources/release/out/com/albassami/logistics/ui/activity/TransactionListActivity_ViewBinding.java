// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TransactionListActivity_ViewBinding implements Unbinder {
  private TransactionListActivity target;

  @UiThread
  public TransactionListActivity_ViewBinding(TransactionListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TransactionListActivity_ViewBinding(TransactionListActivity target, View source) {
    this.target = target;

    target.recentTransactionRecycler = Utils.findRequiredViewAsType(source, R.id.recentTransactionRecycler, "field 'recentTransactionRecycler'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.emptyData = Utils.findRequiredViewAsType(source, R.id.empty_data, "field 'emptyData'", ImageView.class);
    target.noData = Utils.findRequiredViewAsType(source, R.id.noData, "field 'noData'", CustomRegularTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TransactionListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recentTransactionRecycler = null;
    target.toolbar = null;
    target.emptyData = null;
    target.noData = null;
  }
}

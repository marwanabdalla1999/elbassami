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

public class HistoryAdapter$HistoryViewHolder_ViewBinding implements Unbinder {
  private HistoryAdapter.HistoryViewHolder target;

  @UiThread
  public HistoryAdapter$HistoryViewHolder_ViewBinding(HistoryAdapter.HistoryViewHolder target,
      View source) {
    this.target = target;

    target.sourceAddress = Utils.findRequiredViewAsType(source, R.id.sourceAddress, "field 'sourceAddress'", TextView.class);
    target.destAddress = Utils.findRequiredViewAsType(source, R.id.destAddress, "field 'destAddress'", TextView.class);
    target.type = Utils.findRequiredViewAsType(source, R.id.type, "field 'type'", ImageView.class);
    target.sales = Utils.findRequiredViewAsType(source, R.id.sales, "field 'sales'", TextView.class);
    target.statusText = Utils.findRequiredViewAsType(source, R.id.statusText, "field 'statusText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HistoryAdapter.HistoryViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sourceAddress = null;
    target.destAddress = null;
    target.type = null;
    target.sales = null;
    target.statusText = null;
  }
}

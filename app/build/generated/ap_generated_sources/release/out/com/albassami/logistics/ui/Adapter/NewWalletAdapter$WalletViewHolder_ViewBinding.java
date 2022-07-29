// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewWalletAdapter$WalletViewHolder_ViewBinding implements Unbinder {
  private NewWalletAdapter.WalletViewHolder target;

  @UiThread
  public NewWalletAdapter$WalletViewHolder_ViewBinding(NewWalletAdapter.WalletViewHolder target,
      View source) {
    this.target = target;

    target.icon = Utils.findRequiredViewAsType(source, R.id.icon, "field 'icon'", ImageView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", CustomRegularTextView.class);
    target.status = Utils.findRequiredViewAsType(source, R.id.status, "field 'status'", CustomRegularTextView.class);
    target.amount = Utils.findRequiredViewAsType(source, R.id.amount, "field 'amount'", TextView.class);
    target.cancel = Utils.findRequiredViewAsType(source, R.id.cancel, "field 'cancel'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewWalletAdapter.WalletViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.icon = null;
    target.title = null;
    target.status = null;
    target.amount = null;
    target.cancel = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavoriteAddressAdapter$ViewHolder_ViewBinding implements Unbinder {
  private FavoriteAddressAdapter.ViewHolder target;

  @UiThread
  public FavoriteAddressAdapter$ViewHolder_ViewBinding(FavoriteAddressAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.icon = Utils.findRequiredViewAsType(source, R.id.icon, "field 'icon'", ImageView.class);
    target.locationName = Utils.findRequiredViewAsType(source, R.id.locationName, "field 'locationName'", CustomRegularTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoriteAddressAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.icon = null;
    target.locationName = null;
  }
}

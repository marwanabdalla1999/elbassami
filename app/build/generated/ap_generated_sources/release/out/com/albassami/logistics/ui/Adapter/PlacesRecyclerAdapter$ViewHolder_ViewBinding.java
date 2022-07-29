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

public class PlacesRecyclerAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PlacesRecyclerAdapter.ViewHolder target;

  @UiThread
  public PlacesRecyclerAdapter$ViewHolder_ViewBinding(PlacesRecyclerAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.locationName = Utils.findRequiredViewAsType(source, R.id.locationName, "field 'locationName'", CustomRegularTextView.class);
    target.icon = Utils.findRequiredViewAsType(source, R.id.icon, "field 'icon'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlacesRecyclerAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.locationName = null;
    target.icon = null;
  }
}

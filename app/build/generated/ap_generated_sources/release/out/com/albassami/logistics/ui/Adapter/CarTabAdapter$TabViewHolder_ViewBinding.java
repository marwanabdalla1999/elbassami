// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CarTabAdapter$TabViewHolder_ViewBinding implements Unbinder {
  private CarTabAdapter.TabViewHolder target;

  @UiThread
  public CarTabAdapter$TabViewHolder_ViewBinding(CarTabAdapter.TabViewHolder target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvDes = Utils.findRequiredViewAsType(source, R.id.tvdes, "field 'tvDes'", TextView.class);
    target.ivImage = Utils.findRequiredViewAsType(source, R.id.ivIcon, "field 'ivImage'", ImageView.class);
    target.layoutMain = Utils.findRequiredViewAsType(source, R.id.layoutMain, "field 'layoutMain'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CarTabAdapter.TabViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvDes = null;
    target.ivImage = null;
    target.layoutMain = null;
  }
}

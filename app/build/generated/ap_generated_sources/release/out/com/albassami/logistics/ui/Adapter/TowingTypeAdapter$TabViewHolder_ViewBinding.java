// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TowingTypeAdapter$TabViewHolder_ViewBinding implements Unbinder {
  private TowingTypeAdapter.TabViewHolder target;

  @UiThread
  public TowingTypeAdapter$TabViewHolder_ViewBinding(TowingTypeAdapter.TabViewHolder target,
      View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvHomePick, "field 'tvTitle'", TextView.class);
    target.tvPrice = Utils.findRequiredViewAsType(source, R.id.tvPrice, "field 'tvPrice'", TextView.class);
    target.ivImage = Utils.findRequiredViewAsType(source, R.id.iv_icon, "field 'ivImage'", ImageView.class);
    target.mainLayout = Utils.findRequiredViewAsType(source, R.id.mainLayout, "field 'mainLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TowingTypeAdapter.TabViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvPrice = null;
    target.ivImage = null;
    target.mainLayout = null;
  }
}

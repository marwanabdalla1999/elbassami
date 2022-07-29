// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavProviderActivity_ViewBinding implements Unbinder {
  private FavProviderActivity target;

  @UiThread
  public FavProviderActivity_ViewBinding(FavProviderActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FavProviderActivity_ViewBinding(FavProviderActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.fav_Provider_Rv = Utils.findRequiredViewAsType(source, R.id.fav_providers_RV, "field 'fav_Provider_Rv'", RecyclerView.class);
    target.noFav = Utils.findRequiredViewAsType(source, R.id.no_fav, "field 'noFav'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavProviderActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.fav_Provider_Rv = null;
    target.noFav = null;
  }
}

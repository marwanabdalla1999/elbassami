// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.ImageView;
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

public class ProviderProfileSheet_ViewBinding implements Unbinder {
  private ProviderProfileSheet target;

  @UiThread
  public ProviderProfileSheet_ViewBinding(ProviderProfileSheet target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.container = Utils.findRequiredView(source, R.id.container, "field 'container'");
    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", TextView.class);
    target.numHomes = Utils.findRequiredViewAsType(source, R.id.numHomes, "field 'numHomes'", TextView.class);
    target.image = Utils.findRequiredViewAsType(source, R.id.image, "field 'image'", ImageView.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.description, "field 'description'", TextView.class);
    target.homesRecycler = Utils.findRequiredViewAsType(source, R.id.homesRecycler, "field 'homesRecycler'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProviderProfileSheet target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.container = null;
    target.name = null;
    target.numHomes = null;
    target.image = null;
    target.description = null;
    target.homesRecycler = null;
  }
}

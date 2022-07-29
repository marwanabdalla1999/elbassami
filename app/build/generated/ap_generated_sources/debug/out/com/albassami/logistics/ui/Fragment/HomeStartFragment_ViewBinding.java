// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeStartFragment_ViewBinding implements Unbinder {
  private HomeStartFragment target;

  @UiThread
  public HomeStartFragment_ViewBinding(HomeStartFragment target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.bntMenu = Utils.findRequiredViewAsType(source, R.id.bnt_menu, "field 'bntMenu'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeStartFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.bntMenu = null;
  }
}

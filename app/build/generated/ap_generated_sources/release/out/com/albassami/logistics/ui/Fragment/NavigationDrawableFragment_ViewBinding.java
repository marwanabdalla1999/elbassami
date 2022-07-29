// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.ListView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NavigationDrawableFragment_ViewBinding implements Unbinder {
  private NavigationDrawableFragment target;

  private View view7f0902d8;

  @UiThread
  public NavigationDrawableFragment_ViewBinding(final NavigationDrawableFragment target,
      View source) {
    this.target = target;

    View view;
    target.tvUserName = Utils.findRequiredViewAsType(source, R.id.tv_user_name, "field 'tvUserName'", CustomRegularTextView.class);
    target.tvBuildVersion = Utils.findRequiredViewAsType(source, R.id.tv_build_version, "field 'tvBuildVersion'", CustomRegularTextView.class);
    target.lvDrawerUserSettings = Utils.findRequiredViewAsType(source, R.id.lv_drawer_user_settings, "field 'lvDrawerUserSettings'", ListView.class);
    view = Utils.findRequiredView(source, R.id.iv_user_icon, "method 'onViewClicked'");
    view7f0902d8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    NavigationDrawableFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvUserName = null;
    target.tvBuildVersion = null;
    target.lvDrawerUserSettings = null;

    view7f0902d8.setOnClickListener(null);
    view7f0902d8 = null;
  }
}

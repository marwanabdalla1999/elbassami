// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SavedPlacesActivity_ViewBinding implements Unbinder {
  private SavedPlacesActivity target;

  @UiThread
  public SavedPlacesActivity_ViewBinding(SavedPlacesActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SavedPlacesActivity_ViewBinding(SavedPlacesActivity target, View source) {
    this.target = target;

    target.favBack = Utils.findRequiredViewAsType(source, R.id.fav_back, "field 'favBack'", ImageButton.class);
    target.favAdd = Utils.findRequiredViewAsType(source, R.id.fav_add, "field 'favAdd'", ImageButton.class);
    target.toolbarFav = Utils.findRequiredViewAsType(source, R.id.toolbar_fav, "field 'toolbarFav'", Toolbar.class);
    target.favLv = Utils.findRequiredViewAsType(source, R.id.fav_lv, "field 'favLv'", RecyclerView.class);
    target.favProgressBar = Utils.findRequiredViewAsType(source, R.id.fav_progress_bar, "field 'favProgressBar'", ProgressBar.class);
    target.favEmpty = Utils.findRequiredViewAsType(source, R.id.fav_empty, "field 'favEmpty'", CustomRegularTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SavedPlacesActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.favBack = null;
    target.favAdd = null;
    target.toolbarFav = null;
    target.favLv = null;
    target.favProgressBar = null;
    target.favEmpty = null;
  }
}

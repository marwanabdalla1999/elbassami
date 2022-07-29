// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchLocationDialog_ViewBinding implements Unbinder {
  private SearchLocationDialog target;

  @UiThread
  public SearchLocationDialog_ViewBinding(SearchLocationDialog target, View source) {
    this.target = target;

    target.searchBar = Utils.findRequiredViewAsType(source, R.id.searchBar, "field 'searchBar'", EditText.class);
    target.locationRecycler = Utils.findRequiredViewAsType(source, R.id.locationRecycler, "field 'locationRecycler'", RecyclerView.class);
    target.favoriteRecycler = Utils.findRequiredViewAsType(source, R.id.favoriteRecycler, "field 'favoriteRecycler'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchLocationDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchBar = null;
    target.locationRecycler = null;
    target.favoriteRecycler = null;
  }
}

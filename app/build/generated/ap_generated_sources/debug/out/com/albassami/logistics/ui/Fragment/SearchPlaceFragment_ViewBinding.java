// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchPlaceFragment_ViewBinding implements Unbinder {
  private SearchPlaceFragment target;

  private View view7f09005f;

  private View view7f090433;

  private View view7f090119;

  private View view7f09011e;

  @UiThread
  public SearchPlaceFragment_ViewBinding(final SearchPlaceFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.addStop, "field 'addStop' and method 'onViewClicked'");
    target.addStop = Utils.castView(view, R.id.addStop, "field 'addStop'", ImageView.class);
    view7f09005f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.stopLayout = Utils.findRequiredViewAsType(source, R.id.stopLayout, "field 'stopLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.search_back, "method 'onViewClicked'");
    view7f090433 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_pickLoc, "method 'onViewClicked'");
    view7f090119 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_search, "method 'onViewClicked'");
    view7f09011e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchPlaceFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.addStop = null;
    target.stopLayout = null;

    view7f09005f.setOnClickListener(null);
    view7f09005f = null;
    view7f090433.setOnClickListener(null);
    view7f090433 = null;
    view7f090119.setOnClickListener(null);
    view7f090119 = null;
    view7f09011e.setOnClickListener(null);
    view7f09011e = null;
  }
}

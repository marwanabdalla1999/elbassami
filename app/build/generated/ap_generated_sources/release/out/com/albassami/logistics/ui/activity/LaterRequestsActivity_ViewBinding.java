// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LaterRequestsActivity_ViewBinding implements Unbinder {
  private LaterRequestsActivity target;

  private View view7f0902e4;

  @UiThread
  public LaterRequestsActivity_ViewBinding(LaterRequestsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LaterRequestsActivity_ViewBinding(final LaterRequestsActivity target, View source) {
    this.target = target;

    View view;
    target.rideLv = Utils.findRequiredViewAsType(source, R.id.ride_lv, "field 'rideLv'", RecyclerView.class);
    target.rideProgressBar = Utils.findRequiredViewAsType(source, R.id.ride_progress_bar, "field 'rideProgressBar'", ProgressBar.class);
    target.laterEmpty = Utils.findRequiredViewAsType(source, R.id.later_empty, "field 'laterEmpty'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.later_back, "method 'onViewClicked'");
    view7f0902e4 = view;
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
    LaterRequestsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rideLv = null;
    target.rideProgressBar = null;
    target.laterEmpty = null;

    view7f0902e4.setOnClickListener(null);
    view7f0902e4 = null;
  }
}

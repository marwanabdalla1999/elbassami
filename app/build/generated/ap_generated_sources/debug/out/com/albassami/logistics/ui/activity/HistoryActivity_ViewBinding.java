// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HistoryActivity_ViewBinding implements Unbinder {
  private HistoryActivity target;

  private View view7f09027b;

  @UiThread
  public HistoryActivity_ViewBinding(HistoryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HistoryActivity_ViewBinding(final HistoryActivity target, View source) {
    this.target = target;

    View view;
    target.heading = Utils.findRequiredViewAsType(source, R.id.heading, "field 'heading'", CustomBoldRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.history_back, "method 'onViewClicked'");
    view7f09027b = view;
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
    HistoryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.heading = null;

    view7f09027b.setOnClickListener(null);
    view7f09027b = null;
  }
}

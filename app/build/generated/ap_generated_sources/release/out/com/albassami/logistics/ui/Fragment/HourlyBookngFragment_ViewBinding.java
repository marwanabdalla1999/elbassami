// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HourlyBookngFragment_ViewBinding implements Unbinder {
  private HourlyBookngFragment target;

  private View view7f09028c;

  private View view7f09028d;

  @UiThread
  public HourlyBookngFragment_ViewBinding(final HourlyBookngFragment target, View source) {
    this.target = target;

    View view;
    target.hourlyBack = Utils.findRequiredViewAsType(source, R.id.hourly_back, "field 'hourlyBack'", ImageButton.class);
    target.etHourlySourceAddress = Utils.findRequiredViewAsType(source, R.id.et_hourly_source_address, "field 'etHourlySourceAddress'", AutoCompleteTextView.class);
    target.etNoHours = Utils.findRequiredViewAsType(source, R.id.et_no_hours, "field 'etNoHours'", EditText.class);
    target.spType = Utils.findRequiredViewAsType(source, R.id.sp_type, "field 'spType'", Spinner.class);
    target.tripFair = Utils.findRequiredViewAsType(source, R.id.trip_fair, "field 'tripFair'", CustomRegularTextView.class);
    target.textDistance = Utils.findRequiredViewAsType(source, R.id.text_distance, "field 'textDistance'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.hourly_book_btn, "method 'onViewClicked'");
    view7f09028c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.hourly_book_btn_later, "method 'onViewClicked'");
    view7f09028d = view;
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
    HourlyBookngFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.hourlyBack = null;
    target.etHourlySourceAddress = null;
    target.etNoHours = null;
    target.spType = null;
    target.tripFair = null;
    target.textDistance = null;

    view7f09028c.setOnClickListener(null);
    view7f09028c = null;
    view7f09028d.setOnClickListener(null);
    view7f09028d = null;
  }
}

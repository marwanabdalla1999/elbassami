// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AirportBookingFragment_ViewBinding implements Unbinder {
  private AirportBookingFragment target;

  private View view7f09006f;

  private View view7f090070;

  private View view7f090071;

  @UiThread
  public AirportBookingFragment_ViewBinding(final AirportBookingFragment target, View source) {
    this.target = target;

    View view;
    target.fromAirport = Utils.findRequiredViewAsType(source, R.id.from_airport, "field 'fromAirport'", CustomRegularTextView.class);
    target.switchMode = Utils.findRequiredViewAsType(source, R.id.switch_mode, "field 'switchMode'", SwitchCompat.class);
    target.toAirport = Utils.findRequiredViewAsType(source, R.id.to_airport, "field 'toAirport'", CustomRegularTextView.class);
    target.spSelectAddress = Utils.findRequiredViewAsType(source, R.id.sp_select_address, "field 'spSelectAddress'", Spinner.class);
    target.etAirportAddress = Utils.findRequiredViewAsType(source, R.id.et_airport_address, "field 'etAirportAddress'", AutoCompleteTextView.class);
    target.spTypeAirport = Utils.findRequiredViewAsType(source, R.id.sp_type_airport, "field 'spTypeAirport'", Spinner.class);
    target.tripFair = Utils.findRequiredViewAsType(source, R.id.trip_fair, "field 'tripFair'", CustomRegularTextView.class);
    target.tripTolls = Utils.findRequiredViewAsType(source, R.id.trip_tolls, "field 'tripTolls'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.airport_back, "method 'onViewClicked'");
    view7f09006f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.airport_book_btn, "method 'onViewClicked'");
    view7f090070 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.airport_book_btn_later, "method 'onViewClicked'");
    view7f090071 = view;
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
    AirportBookingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fromAirport = null;
    target.switchMode = null;
    target.toAirport = null;
    target.spSelectAddress = null;
    target.etAirportAddress = null;
    target.spTypeAirport = null;
    target.tripFair = null;
    target.tripTolls = null;

    view7f09006f.setOnClickListener(null);
    view7f09006f = null;
    view7f090070.setOnClickListener(null);
    view7f090070 = null;
    view7f090071.setOnClickListener(null);
    view7f090071 = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OngoingTripFragment_ViewBinding implements Unbinder {
  private OngoingTripFragment target;

  private View view7f0901d5;

  private View view7f090133;

  private View view7f090337;

  private View view7f0901d4;

  @UiThread
  public OngoingTripFragment_ViewBinding(final OngoingTripFragment target, View source) {
    this.target = target;

    View view;
    target.tvDriverStatus = Utils.findRequiredViewAsType(source, R.id.tv_driver_status, "field 'tvDriverStatus'", CustomBoldRegularTextView.class);
    target.addressTitle = Utils.findRequiredViewAsType(source, R.id.address_title, "field 'addressTitle'", CustomRegularTextView.class);
    target.tvCurrentLocation = Utils.findRequiredViewAsType(source, R.id.tv_current_location, "field 'tvCurrentLocation'", CustomRegularTextView.class);
    target.stopAddress = Utils.findRequiredViewAsType(source, R.id.stopAddress, "field 'stopAddress'", CustomRegularTextView.class);
    target.stopLay = Utils.findRequiredViewAsType(source, R.id.stopLay, "field 'stopLay'", RelativeLayout.class);
    target.imageView2 = Utils.findRequiredViewAsType(source, R.id.imageView2, "field 'imageView2'", CircleImageView.class);
    view = Utils.findRequiredView(source, R.id.driver_img, "field 'driverImg' and method 'onViewClicked'");
    target.driverImg = Utils.castView(view, R.id.driver_img, "field 'driverImg'", CircleImageView.class);
    view7f0901d5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.driverName = Utils.findRequiredViewAsType(source, R.id.driver_name, "field 'driverName'", CustomBoldRegularTextView.class);
    target.driverCarNumber = Utils.findRequiredViewAsType(source, R.id.driver_car_number, "field 'driverCarNumber'", CustomRegularTextView.class);
    target.driverCarModel = Utils.findRequiredViewAsType(source, R.id.driver_car_model, "field 'driverCarModel'", CustomRegularTextView.class);
    target.driverMobileNumber = Utils.findRequiredViewAsType(source, R.id.driver_mobile_number, "field 'driverMobileNumber'", CustomRegularTextView.class);
    target.vLine = Utils.findRequiredView(source, R.id.v_line, "field 'vLine'");
    view = Utils.findRequiredView(source, R.id.cancel_trip, "field 'cancelTrip' and method 'onViewClicked'");
    target.cancelTrip = Utils.castView(view, R.id.cancel_trip, "field 'cancelTrip'", LinearLayout.class);
    view7f090133 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.moreLay, "field 'moreLay' and method 'onViewClicked'");
    target.moreLay = Utils.castView(view, R.id.moreLay, "field 'moreLay'", LinearLayout.class);
    view7f090337 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.optionsLine = Utils.findRequiredViewAsType(source, R.id.optionsLine, "field 'optionsLine'", CustomRegularTextView.class);
    target.addStop = Utils.findRequiredViewAsType(source, R.id.addStop, "field 'addStop'", CustomRegularTextView.class);
    target.editDrop = Utils.findRequiredViewAsType(source, R.id.editDrop, "field 'editDrop'", CustomRegularTextView.class);
    target.addEditLay = Utils.findRequiredViewAsType(source, R.id.addEditLay, "field 'addEditLay'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.driver_contact, "method 'onViewClicked'");
    view7f0901d4 = view;
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
    OngoingTripFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDriverStatus = null;
    target.addressTitle = null;
    target.tvCurrentLocation = null;
    target.stopAddress = null;
    target.stopLay = null;
    target.imageView2 = null;
    target.driverImg = null;
    target.driverName = null;
    target.driverCarNumber = null;
    target.driverCarModel = null;
    target.driverMobileNumber = null;
    target.vLine = null;
    target.cancelTrip = null;
    target.moreLay = null;
    target.optionsLine = null;
    target.addStop = null;
    target.editDrop = null;
    target.addEditLay = null;

    view7f0901d5.setOnClickListener(null);
    view7f0901d5 = null;
    view7f090133.setOnClickListener(null);
    view7f090133 = null;
    view7f090337.setOnClickListener(null);
    view7f090337 = null;
    view7f0901d4.setOnClickListener(null);
    view7f0901d4 = null;
  }
}

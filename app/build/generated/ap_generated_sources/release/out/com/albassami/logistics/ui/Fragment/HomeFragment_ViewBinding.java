// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  private View view7f09057e;

  private View view7f09011d;

  private View view7f090114;

  private View view7f09010c;

  private View view7f09010a;

  private View view7f09010b;

  @UiThread
  public HomeFragment_ViewBinding(final HomeFragment target, View source) {
    this.target = target;

    View view;
    target.mapLay = Utils.findRequiredViewAsType(source, R.id.map_lay, "field 'mapLay'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_current_location, "field 'tvCurrentLocation' and method 'onViewClicked'");
    target.tvCurrentLocation = Utils.castView(view, R.id.tv_current_location, "field 'tvCurrentLocation'", CustomRegularTextView.class);
    view7f09057e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_schedule, "field 'btnSchedule' and method 'onViewClicked'");
    target.btnSchedule = Utils.castView(view, R.id.btn_schedule, "field 'btnSchedule'", ImageButton.class);
    view7f09011d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.layoutSearch = Utils.findRequiredViewAsType(source, R.id.layout_search, "field 'layoutSearch'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_mylocation, "field 'btnMylocation' and method 'onViewClicked'");
    target.btnMylocation = Utils.castView(view, R.id.btn_mylocation, "field 'btnMylocation'", ImageButton.class);
    view7f090114 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.fabCoffee = Utils.findRequiredViewAsType(source, R.id.fab_coffee, "field 'fabCoffee'", FloatingActionButton.class);
    view = Utils.findRequiredView(source, R.id.btn_floating_hourly, "field 'btnFloatingHourly' and method 'onViewClicked'");
    target.btnFloatingHourly = Utils.castView(view, R.id.btn_floating_hourly, "field 'btnFloatingHourly'", FloatingActionButton.class);
    view7f09010c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_floating_airport, "field 'btnFloatingAirport' and method 'onViewClicked'");
    target.btnFloatingAirport = Utils.castView(view, R.id.btn_floating_airport, "field 'btnFloatingAirport'", FloatingActionButton.class);
    view7f09010a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_floating_bolt, "field 'btnFloatingBolt' and method 'onViewClicked'");
    target.btnFloatingBolt = Utils.castView(view, R.id.btn_floating_bolt, "field 'btnFloatingBolt'", FloatingActionButton.class);
    view7f09010b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.imageViewArrow = Utils.findRequiredViewAsType(source, R.id.imageViewArrow, "field 'imageViewArrow'", ImageView.class);
    target.recycAds = Utils.findRequiredViewAsType(source, R.id.recycAds, "field 'recycAds'", RecyclerView.class);
    target.cardTravel = Utils.findRequiredViewAsType(source, R.id.card_travel, "field 'cardTravel'", CardView.class);
    target.designBottomSheet = Utils.findRequiredViewAsType(source, R.id.design_bottom_sheet, "field 'designBottomSheet'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mapLay = null;
    target.tvCurrentLocation = null;
    target.btnSchedule = null;
    target.layoutSearch = null;
    target.btnMylocation = null;
    target.fabCoffee = null;
    target.btnFloatingHourly = null;
    target.btnFloatingAirport = null;
    target.btnFloatingBolt = null;
    target.imageViewArrow = null;
    target.recycAds = null;
    target.cardTravel = null;
    target.designBottomSheet = null;

    view7f09057e.setOnClickListener(null);
    view7f09057e = null;
    view7f09011d.setOnClickListener(null);
    view7f09011d = null;
    view7f090114.setOnClickListener(null);
    view7f090114 = null;
    view7f09010c.setOnClickListener(null);
    view7f09010c = null;
    view7f09010a.setOnClickListener(null);
    view7f09010a = null;
    view7f09010b.setOnClickListener(null);
    view7f09010b = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RequestMapFragment_ViewBinding implements Unbinder {
  private RequestMapFragment target;

  @UiThread
  public RequestMapFragment_ViewBinding(RequestMapFragment target, View source) {
    this.target = target;

    target.lstVehicle = Utils.findRequiredViewAsType(source, R.id.lst_vehicle, "field 'lstVehicle'", RecyclerView.class);
    target.loadProgress = Utils.findRequiredViewAsType(source, R.id.load_progress, "field 'loadProgress'", ProgressBar.class);
    target.instructionLay = Utils.findRequiredViewAsType(source, R.id.instruction_lay, "field 'instructionLay'", LinearLayout.class);
    target.v = Utils.findRequiredView(source, R.id.v_, "field 'v'");
    target.tvNoSeats = Utils.findRequiredViewAsType(source, R.id.tv_no_seats, "field 'tvNoSeats'", CustomRegularTextView.class);
    target.layPayment = Utils.findRequiredViewAsType(source, R.id.lay_payment, "field 'layPayment'", RelativeLayout.class);
    target.tvPromocode = Utils.findRequiredViewAsType(source, R.id.tv_promocode, "field 'tvPromocode'", CustomRegularTextView.class);
    target.promoLayout = Utils.findRequiredViewAsType(source, R.id.promo_layout, "field 'promoLayout'", LinearLayout.class);
    target.btnRequestCab = Utils.findRequiredViewAsType(source, R.id.btn_request_cab, "field 'btnRequestCab'", CustomRegularTextView.class);
    target.mapLay = Utils.findRequiredViewAsType(source, R.id.map_lay, "field 'mapLay'", RelativeLayout.class);
    target.btnMylocation = Utils.findRequiredViewAsType(source, R.id.btn_mylocation, "field 'btnMylocation'", ImageButton.class);
    target.scheduleDate = Utils.findRequiredViewAsType(source, R.id.schedule_date, "field 'scheduleDate'", CustomRegularTextView.class);
    target.btnRequestLater = Utils.findRequiredViewAsType(source, R.id.btn_request_later, "field 'btnRequestLater'", CustomRegularTextView.class);
    target.dateLayout = Utils.findRequiredViewAsType(source, R.id.date_layout, "field 'dateLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RequestMapFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lstVehicle = null;
    target.loadProgress = null;
    target.instructionLay = null;
    target.v = null;
    target.tvNoSeats = null;
    target.layPayment = null;
    target.tvPromocode = null;
    target.promoLayout = null;
    target.btnRequestCab = null;
    target.mapLay = null;
    target.btnMylocation = null;
    target.scheduleDate = null;
    target.btnRequestLater = null;
    target.dateLayout = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import com.aurelhubert.simpleratingbar.SimpleRatingBar;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScheduleDetailBottomSheet_ViewBinding implements Unbinder {
  private ScheduleDetailBottomSheet target;

  private View view7f090155;

  private View view7f09049f;

  private View view7f09012c;

  @UiThread
  public ScheduleDetailBottomSheet_ViewBinding(final ScheduleDetailBottomSheet target,
      View source) {
    this.target = target;

    View view;
    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", ImageView.class);
    target.tripTime = Utils.findRequiredViewAsType(source, R.id.tripTime, "field 'tripTime'", CustomRegularTextView.class);
    target.requestUniqueId = Utils.findRequiredViewAsType(source, R.id.requestUniqueId, "field 'requestUniqueId'", CustomRegularTextView.class);
    target.amount = Utils.findRequiredViewAsType(source, R.id.amount, "field 'amount'", CustomBoldRegularTextView.class);
    target.mapImage = Utils.findRequiredViewAsType(source, R.id.mapImage, "field 'mapImage'", ImageView.class);
    target.providerImage = Utils.findRequiredViewAsType(source, R.id.providerImage, "field 'providerImage'", CircleImageView.class);
    target.providerName = Utils.findRequiredViewAsType(source, R.id.providerName, "field 'providerName'", CustomRegularTextView.class);
    target.rating = Utils.findRequiredViewAsType(source, R.id.rating, "field 'rating'", SimpleRatingBar.class);
    target.serviceImage = Utils.findRequiredViewAsType(source, R.id.serviceImage, "field 'serviceImage'", CircleImageView.class);
    target.serviceName = Utils.findRequiredViewAsType(source, R.id.serviceName, "field 'serviceName'", CustomRegularTextView.class);
    target.modelName = Utils.findRequiredViewAsType(source, R.id.modelName, "field 'modelName'", CustomRegularTextView.class);
    target.sourceAddress = Utils.findRequiredViewAsType(source, R.id.sourceAddress, "field 'sourceAddress'", CustomRegularTextView.class);
    target.destAddress = Utils.findRequiredViewAsType(source, R.id.destAddress, "field 'destAddress'", CustomRegularTextView.class);
    target.ridefare = Utils.findRequiredViewAsType(source, R.id.ridefare, "field 'ridefare'", CustomRegularTextView.class);
    target.serviceFee = Utils.findRequiredViewAsType(source, R.id.serviceFee, "field 'serviceFee'", CustomRegularTextView.class);
    target.cancellationFee = Utils.findRequiredViewAsType(source, R.id.cancellationFee, "field 'cancellationFee'", CustomRegularTextView.class);
    target.discount = Utils.findRequiredViewAsType(source, R.id.discount, "field 'discount'", CustomRegularTextView.class);
    target.total = Utils.findRequiredViewAsType(source, R.id.total, "field 'total'", CustomBoldRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.chat, "field 'chat' and method 'onViewClicked'");
    target.chat = Utils.castView(view, R.id.chat, "field 'chat'", CustomRegularTextView.class);
    view7f090155 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tack, "field 'track' and method 'onViewClicked'");
    target.track = Utils.castView(view, R.id.tack, "field 'track'", CustomRegularTextView.class);
    view7f09049f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.cancel, "field 'cancel' and method 'onViewClicked'");
    target.cancel = Utils.castView(view, R.id.cancel, "field 'cancel'", CustomRegularTextView.class);
    view7f09012c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.taxes = Utils.findRequiredViewAsType(source, R.id.taxes, "field 'taxes'", CustomRegularTextView.class);
    target.providerLayout = Utils.findRequiredViewAsType(source, R.id.providerLayout, "field 'providerLayout'", RelativeLayout.class);
    target.invoiceBreakdown = Utils.findRequiredViewAsType(source, R.id.invoiceBreakdown, "field 'invoiceBreakdown'", ImageView.class);
    target.invoiceLayout = Utils.findRequiredViewAsType(source, R.id.invoiceLayout, "field 'invoiceLayout'", LinearLayout.class);
    target.rootLayout = Utils.findRequiredViewAsType(source, R.id.rootLayout, "field 'rootLayout'", ScrollView.class);
    target.buttonLayout = Utils.findRequiredViewAsType(source, R.id.buttonLayout, "field 'buttonLayout'", LinearLayout.class);
    target.loader = Utils.findRequiredViewAsType(source, R.id.loader, "field 'loader'", ImageView.class);
    target.payment_mode = Utils.findRequiredViewAsType(source, R.id.payment_mode, "field 'payment_mode'", CustomRegularTextView.class);
    target.topLine = Utils.findRequiredView(source, R.id.topLine, "field 'topLine'");
  }

  @Override
  @CallSuper
  public void unbind() {
    ScheduleDetailBottomSheet target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.back = null;
    target.tripTime = null;
    target.requestUniqueId = null;
    target.amount = null;
    target.mapImage = null;
    target.providerImage = null;
    target.providerName = null;
    target.rating = null;
    target.serviceImage = null;
    target.serviceName = null;
    target.modelName = null;
    target.sourceAddress = null;
    target.destAddress = null;
    target.ridefare = null;
    target.serviceFee = null;
    target.cancellationFee = null;
    target.discount = null;
    target.total = null;
    target.chat = null;
    target.track = null;
    target.cancel = null;
    target.taxes = null;
    target.providerLayout = null;
    target.invoiceBreakdown = null;
    target.invoiceLayout = null;
    target.rootLayout = null;
    target.buttonLayout = null;
    target.loader = null;
    target.payment_mode = null;
    target.topLine = null;

    view7f090155.setOnClickListener(null);
    view7f090155 = null;
    view7f09049f.setOnClickListener(null);
    view7f09049f = null;
    view7f09012c.setOnClickListener(null);
    view7f09012c = null;
  }
}

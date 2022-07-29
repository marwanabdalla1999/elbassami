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
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import com.aurelhubert.simpleratingbar.SimpleRatingBar;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RatingFragment_ViewBinding implements Unbinder {
  private RatingFragment target;

  private View view7f090061;

  @UiThread
  public RatingFragment_ViewBinding(final RatingFragment target, View source) {
    this.target = target;

    View view;
    target.tvTotal = Utils.findRequiredViewAsType(source, R.id.tv_total, "field 'tvTotal'", CustomBoldRegularTextView.class);
    target.tvCancellationFee = Utils.findRequiredViewAsType(source, R.id.tv_cancellation_fee, "field 'tvCancellationFee'", CustomRegularTextView.class);
    target.ivFeedbackUser = Utils.findRequiredViewAsType(source, R.id.iv_feedback_user, "field 'ivFeedbackUser'", CircleImageView.class);
    target.ivFeedbackLocation = Utils.findRequiredViewAsType(source, R.id.iv_feedback_location, "field 'ivFeedbackLocation'", CircleImageView.class);
    target.textTime = Utils.findRequiredViewAsType(source, R.id.text_time, "field 'textTime'", CustomBoldRegularTextView.class);
    target.textDistance = Utils.findRequiredViewAsType(source, R.id.text_distance, "field 'textDistance'", CustomBoldRegularTextView.class);
    target.layoutDistance = Utils.findRequiredViewAsType(source, R.id.layout_distance, "field 'layoutDistance'", LinearLayout.class);
    target.tvPaymentType = Utils.findRequiredViewAsType(source, R.id.tv_payment_type, "field 'tvPaymentType'", CustomRegularTextView.class);
    target.tvNoTolls = Utils.findRequiredViewAsType(source, R.id.tv_no_tolls, "field 'tvNoTolls'", CustomRegularTextView.class);
    target.tollLayout = Utils.findRequiredViewAsType(source, R.id.toll_layout, "field 'tollLayout'", LinearLayout.class);
    target.simpleRatingBar = Utils.findRequiredViewAsType(source, R.id.simple_rating_bar, "field 'simpleRatingBar'", SimpleRatingBar.class);
    target.btnSubmitRating = Utils.findRequiredViewAsType(source, R.id.btn_submit_rating, "field 'btnSubmitRating'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.add_fav, "field 'add_Fav' and method 'onFavClick'");
    target.add_Fav = Utils.castView(view, R.id.add_fav, "field 'add_Fav'", LinearLayout.class);
    view7f090061 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onFavClick();
      }
    });
    target.favIcon = Utils.findRequiredViewAsType(source, R.id.favIcon, "field 'favIcon'", ImageView.class);
    target.makeFav = Utils.findRequiredViewAsType(source, R.id.makeFavourite, "field 'makeFav'", CustomRegularTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RatingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTotal = null;
    target.tvCancellationFee = null;
    target.ivFeedbackUser = null;
    target.ivFeedbackLocation = null;
    target.textTime = null;
    target.textDistance = null;
    target.layoutDistance = null;
    target.tvPaymentType = null;
    target.tvNoTolls = null;
    target.tollLayout = null;
    target.simpleRatingBar = null;
    target.btnSubmitRating = null;
    target.add_Fav = null;
    target.favIcon = null;
    target.makeFav = null;

    view7f090061.setOnClickListener(null);
    view7f090061 = null;
  }
}

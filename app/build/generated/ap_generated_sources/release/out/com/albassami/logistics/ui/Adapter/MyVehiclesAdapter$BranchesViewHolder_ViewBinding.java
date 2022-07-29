// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyVehiclesAdapter$BranchesViewHolder_ViewBinding implements Unbinder {
  private MyVehiclesAdapter.BranchesViewHolder target;

  @UiThread
  public MyVehiclesAdapter$BranchesViewHolder_ViewBinding(
      MyVehiclesAdapter.BranchesViewHolder target, View source) {
    this.target = target;

    target.ivDelete = Utils.findRequiredViewAsType(source, R.id.ivDelete, "field 'ivDelete'", ImageView.class);
    target.ivEdit = Utils.findRequiredViewAsType(source, R.id.ivEdit, "field 'ivEdit'", ImageView.class);
    target.tvCarBrand = Utils.findRequiredViewAsType(source, R.id.tvCarBrand, "field 'tvCarBrand'", CustomRegularTextView.class);
    target.tvCarType = Utils.findRequiredViewAsType(source, R.id.tvCarType, "field 'tvCarType'", CustomRegularTextView.class);
    target.tvIDNumber = Utils.findRequiredViewAsType(source, R.id.tvIDNumber, "field 'tvIDNumber'", CustomRegularTextView.class);
    target.tvOwnerName = Utils.findRequiredViewAsType(source, R.id.tvOwnerName, "field 'tvOwnerName'", CustomRegularTextView.class);
    target.tvPlateNumber = Utils.findRequiredViewAsType(source, R.id.tvPlateNumber, "field 'tvPlateNumber'", CustomRegularTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyVehiclesAdapter.BranchesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivDelete = null;
    target.ivEdit = null;
    target.tvCarBrand = null;
    target.tvCarType = null;
    target.tvIDNumber = null;
    target.tvOwnerName = null;
    target.tvPlateNumber = null;
  }
}

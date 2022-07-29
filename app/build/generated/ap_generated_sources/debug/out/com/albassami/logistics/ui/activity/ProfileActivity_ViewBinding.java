// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileActivity_ViewBinding implements Unbinder {
  private ProfileActivity target;

  private View view7f0903ce;

  private View view7f090106;

  private View view7f0903cf;

  @UiThread
  public ProfileActivity_ViewBinding(ProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileActivity_ViewBinding(final ProfileActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.profile_back, "field 'profileBack' and method 'onViewClicked'");
    target.profileBack = Utils.castView(view, R.id.profile_back, "field 'profileBack'", ImageButton.class);
    view7f0903ce = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_edit_profile, "field 'btnEditProfile' and method 'onViewClicked'");
    target.btnEditProfile = Utils.castView(view, R.id.btn_edit_profile, "field 'btnEditProfile'", CustomBoldRegularTextView.class);
    view7f090106 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.toolbarProfile = Utils.findRequiredViewAsType(source, R.id.toolbar_profile, "field 'toolbarProfile'", Toolbar.class);
    target.actionbarLay = Utils.findRequiredViewAsType(source, R.id.actionbar_lay, "field 'actionbarLay'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.profile_image, "field 'profileImage' and method 'onViewClicked'");
    target.profileImage = Utils.castView(view, R.id.profile_image, "field 'profileImage'", ImageView.class);
    view7f0903cf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.profileImgLay = Utils.findRequiredViewAsType(source, R.id.profile_img_lay, "field 'profileImgLay'", RelativeLayout.class);
    target.layName = Utils.findRequiredViewAsType(source, R.id.lay_name, "field 'layName'", LinearLayout.class);
    target.etProfileEmail = Utils.findRequiredViewAsType(source, R.id.et_profile_email, "field 'etProfileEmail'", CustomRegularEditView.class);
    target.radioBtnMale = Utils.findRequiredViewAsType(source, R.id.radio_btn_male, "field 'radioBtnMale'", RadioButton.class);
    target.radioBtnFemale = Utils.findRequiredViewAsType(source, R.id.radio_btn_female, "field 'radioBtnFemale'", RadioButton.class);
    target.profileRadioGroup = Utils.findRequiredViewAsType(source, R.id.profile_radioGroup, "field 'profileRadioGroup'", RadioGroup.class);
    target.etFirstName = Utils.findRequiredViewAsType(source, R.id.etFirstName, "field 'etFirstName'", CustomRegularEditView.class);
    target.etLastName = Utils.findRequiredViewAsType(source, R.id.etLastName, "field 'etLastName'", CustomRegularEditView.class);
    target.id_number = Utils.findRequiredViewAsType(source, R.id.id_number, "field 'id_number'", CustomRegularEditView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profileBack = null;
    target.btnEditProfile = null;
    target.toolbarProfile = null;
    target.actionbarLay = null;
    target.profileImage = null;
    target.profileImgLay = null;
    target.layName = null;
    target.etProfileEmail = null;
    target.radioBtnMale = null;
    target.radioBtnFemale = null;
    target.profileRadioGroup = null;
    target.etFirstName = null;
    target.etLastName = null;
    target.id_number = null;

    view7f0903ce.setOnClickListener(null);
    view7f0903ce = null;
    view7f090106.setOnClickListener(null);
    view7f090106 = null;
    view7f0903cf.setOnClickListener(null);
    view7f0903cf = null;
  }
}

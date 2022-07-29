// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageFragment_ViewBinding implements Unbinder {
  private MessageFragment target;

  private View view7f0900aa;

  private View view7f090121;

  private View view7f090116;

  private View view7f0902d6;

  private View view7f0902d0;

  private View view7f090103;

  private View view7f090100;

  private View view7f09011f;

  @UiThread
  public MessageFragment_ViewBinding(final MessageFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.bolt_back, "field 'boltBack' and method 'onViewClicked'");
    target.boltBack = Utils.castView(view, R.id.bolt_back, "field 'boltBack'", ImageButton.class);
    view7f0900aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.toolbarBolt = Utils.findRequiredViewAsType(source, R.id.toolbar_bolt, "field 'toolbarBolt'", Toolbar.class);
    target.firstMsg = Utils.findRequiredViewAsType(source, R.id.first_msg, "field 'firstMsg'", CustomRegularTextView.class);
    target.boltMsg1 = Utils.findRequiredViewAsType(source, R.id.bolt_msg_1, "field 'boltMsg1'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.btn_yes, "field 'btnYes' and method 'onViewClicked'");
    target.btnYes = Utils.castView(view, R.id.btn_yes, "field 'btnYes'", Button.class);
    view7f090121 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_no, "field 'btnNo' and method 'onViewClicked'");
    target.btnNo = Utils.castView(view, R.id.btn_no, "field 'btnNo'", Button.class);
    view7f090116 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.btnsLayout = Utils.findRequiredViewAsType(source, R.id.btns_layout, "field 'btnsLayout'", LinearLayout.class);
    target.boltMsg2 = Utils.findRequiredViewAsType(source, R.id.bolt_msg_2, "field 'boltMsg2'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.iv_source_map, "field 'ivSourceMap' and method 'onViewClicked'");
    target.ivSourceMap = Utils.castView(view, R.id.iv_source_map, "field 'ivSourceMap'", ImageView.class);
    view7f0902d6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvSAddress = Utils.findRequiredViewAsType(source, R.id.tv_s_address, "field 'tvSAddress'", CustomRegularTextView.class);
    target.sourceLayout = Utils.findRequiredViewAsType(source, R.id.source_layout, "field 'sourceLayout'", LinearLayout.class);
    target.boltMsg3 = Utils.findRequiredViewAsType(source, R.id.bolt_msg_3, "field 'boltMsg3'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.iv_dest_map, "field 'ivDestMap' and method 'onViewClicked'");
    target.ivDestMap = Utils.castView(view, R.id.iv_dest_map, "field 'ivDestMap'", ImageView.class);
    view7f0902d0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvDestAddress = Utils.findRequiredViewAsType(source, R.id.tv_dest_address, "field 'tvDestAddress'", CustomRegularTextView.class);
    target.destinationLayout = Utils.findRequiredViewAsType(source, R.id.destination_layout, "field 'destinationLayout'", LinearLayout.class);
    target.boltMsg4 = Utils.findRequiredViewAsType(source, R.id.bolt_msg_4, "field 'boltMsg4'", CustomRegularTextView.class);
    target.recycelType = Utils.findRequiredViewAsType(source, R.id.recycel_type, "field 'recycelType'", RecyclerView.class);
    target.tvApproximatePrice = Utils.findRequiredViewAsType(source, R.id.tv_approximate_price, "field 'tvApproximatePrice'", CustomRegularTextView.class);
    view = Utils.findRequiredView(source, R.id.btn_create_request, "field 'btnCreateRequest' and method 'onViewClicked'");
    target.btnCreateRequest = Utils.castView(view, R.id.btn_create_request, "field 'btnCreateRequest'", Button.class);
    view7f090103 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_cancel, "field 'btnCancel' and method 'onViewClicked'");
    target.btnCancel = Utils.castView(view, R.id.btn_cancel, "field 'btnCancel'", Button.class);
    view7f090100 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.btnsRequest = Utils.findRequiredViewAsType(source, R.id.btns_request, "field 'btnsRequest'", LinearLayout.class);
    target.scrollBolt = Utils.findRequiredViewAsType(source, R.id.scroll_bolt, "field 'scrollBolt'", ScrollView.class);
    target.etMessage = Utils.findRequiredViewAsType(source, R.id.et_message, "field 'etMessage'", CustomRegularEditView.class);
    view = Utils.findRequiredView(source, R.id.btn_send, "field 'btnSend' and method 'onViewClicked'");
    target.btnSend = Utils.castView(view, R.id.btn_send, "field 'btnSend'", ImageView.class);
    view7f09011f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.layMsgSend = Utils.findRequiredViewAsType(source, R.id.lay_msg_send, "field 'layMsgSend'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessageFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.boltBack = null;
    target.toolbarBolt = null;
    target.firstMsg = null;
    target.boltMsg1 = null;
    target.btnYes = null;
    target.btnNo = null;
    target.btnsLayout = null;
    target.boltMsg2 = null;
    target.ivSourceMap = null;
    target.tvSAddress = null;
    target.sourceLayout = null;
    target.boltMsg3 = null;
    target.ivDestMap = null;
    target.tvDestAddress = null;
    target.destinationLayout = null;
    target.boltMsg4 = null;
    target.recycelType = null;
    target.tvApproximatePrice = null;
    target.btnCreateRequest = null;
    target.btnCancel = null;
    target.btnsRequest = null;
    target.scrollBolt = null;
    target.etMessage = null;
    target.btnSend = null;
    target.layMsgSend = null;

    view7f0900aa.setOnClickListener(null);
    view7f0900aa = null;
    view7f090121.setOnClickListener(null);
    view7f090121 = null;
    view7f090116.setOnClickListener(null);
    view7f090116 = null;
    view7f0902d6.setOnClickListener(null);
    view7f0902d6 = null;
    view7f0902d0.setOnClickListener(null);
    view7f0902d0 = null;
    view7f090103.setOnClickListener(null);
    view7f090103 = null;
    view7f090100.setOnClickListener(null);
    view7f090100 = null;
    view7f09011f.setOnClickListener(null);
    view7f09011f = null;
  }
}

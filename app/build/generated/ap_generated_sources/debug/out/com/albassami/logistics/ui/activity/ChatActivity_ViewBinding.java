// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatActivity_ViewBinding implements Unbinder {
  private ChatActivity target;

  private View view7f09044d;

  @UiThread
  public ChatActivity_ViewBinding(ChatActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChatActivity_ViewBinding(final ChatActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.chatRecycler = Utils.findRequiredViewAsType(source, R.id.chatRecycler, "field 'chatRecycler'", RecyclerView.class);
    target.msgToSend = Utils.findRequiredViewAsType(source, R.id.msgToSend, "field 'msgToSend'", EditText.class);
    view = Utils.findRequiredView(source, R.id.sendMsgBtn, "field 'sendMsgBtn' and method 'onNewMessageSend'");
    target.sendMsgBtn = Utils.castView(view, R.id.sendMsgBtn, "field 'sendMsgBtn'", Button.class);
    view7f09044d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNewMessageSend();
      }
    });
    target.chatSendingLayout = Utils.findRequiredViewAsType(source, R.id.chatSendingLayout, "field 'chatSendingLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.chatRecycler = null;
    target.msgToSend = null;
    target.sendMsgBtn = null;
    target.chatSendingLayout = null;

    view7f09044d.setOnClickListener(null);
    view7f09044d = null;
  }
}

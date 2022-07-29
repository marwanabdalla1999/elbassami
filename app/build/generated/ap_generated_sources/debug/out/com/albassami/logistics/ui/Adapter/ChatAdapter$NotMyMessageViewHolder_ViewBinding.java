// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatAdapter$NotMyMessageViewHolder_ViewBinding implements Unbinder {
  private ChatAdapter.NotMyMessageViewHolder target;

  @UiThread
  public ChatAdapter$NotMyMessageViewHolder_ViewBinding(ChatAdapter.NotMyMessageViewHolder target,
      View source) {
    this.target = target;

    target.personImage = Utils.findRequiredViewAsType(source, R.id.persionImage, "field 'personImage'", ImageView.class);
    target.messageTime = Utils.findRequiredViewAsType(source, R.id.messageTime, "field 'messageTime'", TextView.class);
    target.message = Utils.findRequiredViewAsType(source, R.id.message, "field 'message'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatAdapter.NotMyMessageViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.personImage = null;
    target.messageTime = null;
    target.message = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PayGateWeb_ViewBinding implements Unbinder {
  private PayGateWeb target;

  private View view7f090279;

  @UiThread
  public PayGateWeb_ViewBinding(PayGateWeb target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PayGateWeb_ViewBinding(final PayGateWeb target, View source) {
    this.target = target;

    View view;
    target.toolbarHelp = Utils.findRequiredViewAsType(source, R.id.toolbar_help, "field 'toolbarHelp'", Toolbar.class);
    target.webView = Utils.findRequiredViewAsType(source, R.id.webView, "field 'webView'", WebView.class);
    target.webLoader = Utils.findRequiredViewAsType(source, R.id.web_loader, "field 'webLoader'", ProgressBar.class);
    target.helpContent = Utils.findRequiredViewAsType(source, R.id.helpContent, "field 'helpContent'", TextView.class);
    view = Utils.findRequiredView(source, R.id.help_back, "method 'onViewClicked'");
    view7f090279 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PayGateWeb target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbarHelp = null;
    target.webView = null;
    target.webLoader = null;
    target.helpContent = null;

    view7f090279.setOnClickListener(null);
    view7f090279 = null;
  }
}

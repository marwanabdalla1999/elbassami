// Generated code from Butter Knife. Do not modify!
package com.albassami.logistics.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albassami.logistics.R;
import com.aurelhubert.simpleratingbar.SimpleRatingBar;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavoriteProviderAdapter$ViewHolder_ViewBinding implements Unbinder {
  private FavoriteProviderAdapter.ViewHolder target;

  @UiThread
  public FavoriteProviderAdapter$ViewHolder_ViewBinding(FavoriteProviderAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.providerPicture = Utils.findRequiredViewAsType(source, R.id.providerPicture, "field 'providerPicture'", CircleImageView.class);
    target.providerName = Utils.findRequiredViewAsType(source, R.id.providerName, "field 'providerName'", TextView.class);
    target.rating = Utils.findRequiredViewAsType(source, R.id.rating, "field 'rating'", SimpleRatingBar.class);
    target.favOrNot = Utils.findRequiredViewAsType(source, R.id.favOrNot, "field 'favOrNot'", ImageView.class);
    target.root = Utils.findRequiredViewAsType(source, R.id.root, "field 'root'", ViewGroup.class);
    target.mobile = Utils.findRequiredViewAsType(source, R.id.providerMobile, "field 'mobile'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoriteProviderAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.providerPicture = null;
    target.providerName = null;
    target.rating = null;
    target.favOrNot = null;
    target.root = null;
    target.mobile = null;
  }
}

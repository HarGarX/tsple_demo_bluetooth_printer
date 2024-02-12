// Generated code from Butter Knife. Do not modify!
package com.sdk.tspl.demo;

import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BTActivity_ViewBinding implements Unbinder {
  private BTActivity target;

  @UiThread
  public BTActivity_ViewBinding(BTActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BTActivity_ViewBinding(BTActivity target, View source) {
    this.target = target;

    target.recyHistory = Utils.findRequiredViewAsType(source, R.id.recy_history, "field 'recyHistory'", RecyclerView.class);
    target.swipeRefresh = Utils.findRequiredViewAsType(source, R.id.swipe_refresh, "field 'swipeRefresh'", SwipeRefreshLayout.class);
    target.activityBt = Utils.findRequiredViewAsType(source, R.id.activity_bt, "field 'activityBt'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BTActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyHistory = null;
    target.swipeRefresh = null;
    target.activityBt = null;
  }
}

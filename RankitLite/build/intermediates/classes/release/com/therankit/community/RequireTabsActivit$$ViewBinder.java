// Generated code from Butter Knife. Do not modify!
package com.therankit.community;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RequireTabsActivit$$ViewBinder<T extends com.therankit.community.RequireTabsActivit> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624082, "method 'onFabClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onFabClick();
        }
      });
  }

  @Override public void unbind(T target) {
  }
}

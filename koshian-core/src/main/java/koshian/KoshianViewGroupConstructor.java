package koshian;

import android.view.ViewGroup;

public interface KoshianViewGroupConstructor<V, L extends ViewGroup.LayoutParams> extends KoshianViewConstructor<V> {
   public L instantiateLayoutParams();
}

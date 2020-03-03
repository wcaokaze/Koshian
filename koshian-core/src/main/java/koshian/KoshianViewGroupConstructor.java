package koshian;

import android.view.*;

public interface KoshianViewGroupConstructor
      <V extends View, L extends ViewGroup.LayoutParams>
      extends KoshianViewConstructor<V>
{
   public L instantiateLayoutParams();
}

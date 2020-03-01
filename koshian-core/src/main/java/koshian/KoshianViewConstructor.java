package koshian;

import android.content.*;

public interface KoshianViewConstructor<V> {
   public V instantiate(Context context);
}

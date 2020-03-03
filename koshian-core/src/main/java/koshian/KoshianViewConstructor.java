package koshian;

import android.content.*;
import android.view.*;

public interface KoshianViewConstructor<V extends View> {
   public V instantiate(Context context);
}

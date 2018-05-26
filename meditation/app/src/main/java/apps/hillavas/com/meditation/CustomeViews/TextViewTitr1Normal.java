package apps.hillavas.com.meditation.CustomeViews;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by A.Mohammadi on 7/2/2017.
 */

public class TextViewTitr1Normal extends TextView{
    public TextViewTitr1Normal(Context context) {
        super(context);
        init();
    }

    public TextViewTitr1Normal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewTitr1Normal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TextViewTitr1Normal(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets() , "fonts/tahoma.ttf");
            this.setTypeface(typeface);
    }
}

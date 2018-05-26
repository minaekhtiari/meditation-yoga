package adapters;

import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;

/**
 * Created by A.Mohammadi on 11/18/2017.
 */

public class SparkView_Adapter extends SparkAdapter {

    private float[] yData;

    public SparkView_Adapter(float[] yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}

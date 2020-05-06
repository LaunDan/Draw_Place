package drawplace.p;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MyCanvas extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    public static int DEFAULT_WIDTH = 4;
    public static final int DEFAULT_COLOR = Color.WHITE;
    private int currentColor;
    private int currentWidth;


    public MyCanvas(Context context) {
        this(context, null);
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeMiter(.5f);
        paint.setStrokeWidth(5);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        path = new Path();
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(xPos, yPos);
                break;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                path.lineTo(xPos, yPos);
                break;

        }
        invalidate();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save();

        for (FingerPath fp : paths){
            paint.setColor(fp.color);
            paint.setStrokeWidth(fp.width);
        }
    }

    // set color of line
    public void setColors(int color) {
        if (color == 0) {
            paint.setColor(Color.WHITE);
        } else if (color == 1) {
            paint.setColor(Color.YELLOW);
        } else if (color == 2) {
            paint.setColor(Color.GREEN);
        } else if (color == 3) {
            paint.setColor(Color.BLUE);
        } else if (color == 4) {
            paint.setColor(Color.RED);
        }
    }

    public void init(DisplayMetrics metrics){
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        currentWidth = DEFAULT_WIDTH;
    }

    public void clearAll(){
        paths.clear();
        invalidate();
    }

}

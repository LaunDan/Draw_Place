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
    public Bitmap mBitmap;
    private Canvas mCanvas;
    public static int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_COLOR = Color.WHITE;
    private int currentColor;
    private int currentWidth;
    private int currentBackground = Color.BLACK;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);




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
        this.setDrawingCacheEnabled(true);


    }

    private void touchStart(float x, float y){
        path = new Path();
        FingerPath fp = new FingerPath(currentColor, path, currentWidth);
        paths.add(fp);

        path.reset();
        path.moveTo(x, y);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(xPos, yPos);
                invalidate();
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
        mCanvas.drawColor(currentBackground);

        for (FingerPath fp : paths){
            paint.setColor(fp.color);
            paint.setStrokeWidth(fp.width);

            mCanvas.drawPath(fp.path, paint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        
        canvas.restore();

    }

    public void setColors(int color) {
        if (color == 0) {
            currentColor = Color.WHITE;
        } else if (color == 1) {
            currentColor = Color.YELLOW;
        } else if (color == 2) {
            currentColor = Color.GREEN;
        } else if (color == 3) {
            currentColor = Color.BLUE;
        } else if (color == 4) {
            currentColor = Color.RED;
        } else if (color == 5) {
            currentColor = Color.BLACK;
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

    public void clearOne(){
        if (paths.size() >= 1) {
            paths.remove(paths.size() - 1);
            invalidate();
        }
    }

    public void changeWidth(int width){
        currentWidth = width;
        paint.setStrokeWidth(width);
    }

    public void changeBackground(){
        if (currentBackground == Color.BLACK){
            currentBackground = Color.WHITE;

        } else if (currentBackground == Color.WHITE){
            currentBackground = Color.BLACK;
        }
         invalidate();
    }

    public Bitmap getBitmap(){
        return this.getDrawingCache();
    }
}

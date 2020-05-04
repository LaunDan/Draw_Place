package drawplace.p;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class MyCanvas extends View {
    private Paint paint = new Paint();
    private Path path = new Path();

    public MyCanvas(Context context) {
        super(context);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeMiter(.5f);
        paint.setStrokeWidth(5);

    }
    //TODO change application to change path one by one
    
    //TODO step back button

    // TODO delete all button

    // TODO change width of line

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
        canvas.drawPath(path, paint);
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


}

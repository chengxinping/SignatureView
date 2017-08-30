package cn.xpcheng.signatureview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author ChengXinPing
 * @time 2017/8/30 14:24
 */

    public class SignatureView extends View {
    private Paint linePaint;// 画笔

    private ArrayList<Path> lines;// 写字的笔迹，支持多笔画
    private int lineCount;// 记录笔画数目

    private final int DEFAULT_LINE_WIDTH = 5;// 默认笔画宽度

    private int lineColor = Color.BLACK;// 默认字迹颜色（黑色）
    private float lineWidth = DEFAULT_LINE_WIDTH;// 笔画宽度

    public SignatureView(Context context) {
        super(context);
        init();
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        lines = new ArrayList<Path>();
        linePaint = new Paint();
        linePaint.setColor(lineColor);// 画笔颜色
        linePaint.setStrokeWidth(lineWidth);// 画笔宽度
        linePaint.setStrokeCap(Paint.Cap.ROUND);// 设置笔迹的起始、结束为圆形
        linePaint.setPathEffect(new CornerPathEffect(50));// PahtEfect指笔迹的风格，CornerPathEffect在拐角处添加弧度，弧度半径50像素点
        linePaint.setStyle(Paint.Style.STROKE);// 设置画笔风格
    }


    /**
     * 设置画笔颜色的接口
     *
     * @param lineColor
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        linePaint.setColor(lineColor);
    }

    /**
     * 设置画笔宽度的接口
     *
     * @param lineWidth
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        linePaint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lines != null && lines.size() > 0) {
            for (Path path : lines) {
                canvas.drawPath(path, linePaint);
            }
        }
    }

    /**
     * 清楚屏幕上画的图像
     */
    public void clear() {
        lines.removeAll(lines);
        invalidate();
    }

    /**
     * 获取画的bitmap对象
     *
     * @return bitmap
     */
    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // 绘制背景
        Drawable bgDrawable = getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        // 绘制View视图内容
        draw(canvas);
        return bitmap;
    }


    public String saveBitmap(String path) {
        ByteArrayOutputStream baos = null;
        String filename = null;
        try {
            Bitmap bitmap = getBitmap();
            Bitmap bitmapTag = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapTag);
            Paint paint = new Paint();
            canvas.drawColor(Color.parseColor("#ffffff"));
            canvas.drawBitmap(bitmap, null, new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()), paint);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();

            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            String time = String.valueOf(new Date().getTime());
            filename = path + "/pic_" + time + ".jpeg";
            baos = new ByteArrayOutputStream();
            bitmapTag.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] photoBytes = baos.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(filename)).write(photoBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Path path = new Path();
            path.moveTo(event.getX(), event.getY());
            lines.add(path);
            lineCount = lines.size();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            lines.get(lineCount - 1).lineTo(event.getX(), event.getY());
            invalidate();
        } else {

        }
        return true;
    }
}

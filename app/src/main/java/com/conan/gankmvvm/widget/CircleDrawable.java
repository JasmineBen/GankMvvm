package com.conan.gankmvvm.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class CircleDrawable extends Drawable{
    protected float radius;
    protected final RectF mRect = new RectF();
    protected final RectF mBitmapRect;
    protected final BitmapShader bitmapShader;
    protected final Paint paint;

    public CircleDrawable(Bitmap bitmap) {
        this.radius = (float) (Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2);
        this.bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.mBitmapRect = new RectF(0.0F, 0.0F, (float) bitmap.getWidth(), (float) bitmap.getHeight());
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setShader(this.bitmapShader);
        this.paint.setFilterBitmap(true);
        this.paint.setDither(true);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mRect.set(0.0F, 0.0F, (float) bounds.width(), (float) bounds.height());
        this.radius = (float) (Math.min(bounds.width(), bounds.height()) / 2);
        Matrix shaderMatrix = new Matrix();
        shaderMatrix.setRectToRect(this.mBitmapRect, this.mRect, Matrix.ScaleToFit.FILL);
        this.bitmapShader.setLocalMatrix(shaderMatrix);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(this.radius, this.radius, this.radius, this.paint);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        this.paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        this.paint.setColorFilter(cf);
    }
}

package com.tfg.vitalfit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class ImcCharView extends View {
    private Paint greenPaint, orangePaint, redPaint, markerPaint;
    private TextPaint textPaint;

    private float imcValue = 0f;

    public ImcCharView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setColor(Color.GREEN);

        orangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        orangePaint.setColor(Color.rgb(255, 165, 0)); // Naranja

        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setColor(Color.RED);

        markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        markerPaint.setColor(Color.BLUE);
        markerPaint.setStrokeWidth(6f);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(32f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setIMC(float imc) {
        this.imcValue = imc;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        float paddingHorizontal = 40f; // espacio a izquierda y derecha

        float barHeight = height * 0.3f;
        float barTop = height * 0.3f;
        float barBottom = barTop + barHeight;

        float totalIMC = 40f;

        // Rangos
        float anorexiaEnd = 18.5f;
        float saludableEnd = 24.9f;
        float sobrepesoEnd = 29.9f;

        float usableWidth = width - 2 * paddingHorizontal;

        float anorexiaW = (anorexiaEnd / totalIMC) * usableWidth;
        float saludableW = ((saludableEnd - anorexiaEnd) / totalIMC) * usableWidth;
        float sobrepesoW = ((sobrepesoEnd - saludableEnd) / totalIMC) * usableWidth;
        float obesidadW = usableWidth - anorexiaW - saludableW - sobrepesoW;

        // Dibujar barras de colores
        float x = paddingHorizontal;
        canvas.drawRect(x, barTop, x + anorexiaW, barBottom, orangePaint);
        x += anorexiaW;
        canvas.drawRect(x, barTop, x + saludableW, barBottom, greenPaint);
        x += saludableW;
        canvas.drawRect(x, barTop, x + sobrepesoW, barBottom, orangePaint);
        x += sobrepesoW;
        canvas.drawRect(x, barTop, x + obesidadW, barBottom, redPaint);

        // Cortes de IMC
        float[] cortes = {0f, anorexiaEnd, saludableEnd, sobrepesoEnd, totalIMC};
        for (float corte : cortes) {
            float corteX = paddingHorizontal + (corte / totalIMC) * usableWidth;
            canvas.drawLine(corteX, barTop - 10, corteX, barBottom + 10, textPaint);
            canvas.drawText(String.valueOf(corte), corteX, barTop - 20, textPaint);
        }

        // Línea marcador del IMC del paciente
        float imcX = paddingHorizontal + (imcValue / totalIMC) * usableWidth;
        canvas.drawLine(imcX, barTop - 20, imcX, barBottom + 20, markerPaint);

        // Valor del paciente
        canvas.drawText(String.format("%.1f", imcValue), imcX, barBottom + 50, textPaint);
    }

}

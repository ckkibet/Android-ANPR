package com.google.android.gms.anpr.vision.ocrreader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.android.gms.anpr.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.text.TextBlock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class OcrGraphic extends GraphicOverlay.Graphic {

    private static final int TEXT_COLOR = Color.WHITE;

    private static Paint sRectPaint;
    private static Paint sTextPaint;
    private final TextBlock mText;
    public String text;
    Context context;
    Intent myIntent;

    OcrGraphic(GraphicOverlay overlay, TextBlock text) {
        super(overlay);

        mText = text;

        if (sRectPaint == null) {
            sRectPaint = new Paint();
            sRectPaint.setColor(TEXT_COLOR);
            sRectPaint.setStyle(Paint.Style.STROKE);
            sRectPaint.setStrokeWidth(4.0f);
        }

        if (sTextPaint == null) {
            sTextPaint = new Paint();
            sTextPaint.setColor(TEXT_COLOR);
            sTextPaint.setTextSize(30.0f);
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate();
    }

    public void saveContext(Context con) {
        context = con;
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        if (mText == null) {
            return;
        }

        // Draws the bounding box around the TextBlock.
        RectF rect = new RectF(mText.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, sRectPaint);

        //validation setting
        String REGEX = "^[a-zA-z-Q-q]{1,3}\\s*[-]*[0-9]{0,2}\\s*[-]*[0-9]{3,4}[a-zA-z]{0,4}$"; //regular expression
        Pattern number; //a pattern of compiled regex
        Matcher matcher; //helps in matching the regex
        text = mText.getValue();

        //fixing
        Matcher m = Pattern.compile("[-][0-9]{2}[-]|[-]|[\n]").matcher(text);
        text = m.replaceAll(" ");

        //final touch
        text = Pattern.compile("\\s[0-9]{2}\\s").matcher(text).replaceAll(" ");
        text = text.replaceAll("( +)", " ").trim().toUpperCase();

        //number detection
        number = Pattern.compile(REGEX);
        matcher = number.matcher(text);
        if (matcher.matches()) { //print if valid
            canvas.drawText(text, rect.centerX(), rect.bottom, sTextPaint); //draw on screen
            Displaytext();
            /**
             * This displayText function is implemented in the function below that triggers the popup to be
             * displayed and number plate
             */

//            myIntent.putExtra("result", text); //Optional parameters
//            context.startActivity(myIntent);
            //setting up the intent and passing data from this Ocr activity to Result Activity
            /*
            NOTE:   After a long time searching the web about the problem, I think the problem is with intent because this file
                    is NOT an Activity file, the activity file is OcrCaptureActivity. There's a possibility that this might be a
                    problem. Also, you may need to check the .xml files, I had a hard time with those.
             */

            Intent myIntent = new Intent(context,Result.class);
            myIntent.putExtra("results",text);
            context.startActivity(myIntent);

        }
    }

    public void Displaytext() {
        Intent intent = new Intent(context, Result.class);
        context.startActivity(intent);
    }
}

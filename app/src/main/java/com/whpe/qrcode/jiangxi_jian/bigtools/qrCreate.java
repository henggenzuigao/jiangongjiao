package com.whpe.qrcode.jiangxi_jian.bigtools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * Created by sheep on 2016/11/16.
 *
 * create qr code user zxing lib
 */

public class qrCreate {

	/**
	 * create qr code
	 * @param content qr code content
	 * @param width   qr width
	 * @param height  qr height
	 * @return
	 */
	public static Bitmap createQR(@NonNull String content, int width, int height){
		Bitmap bitmap = null;
		try {
//			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			Hashtable hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			int w = bitMatrix.getWidth();
			int h = bitMatrix.getHeight();
			int[] pixels = new int[width * height];

			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * w + x] = 0xff000000;
					}else{
						pixels[y * w + x] = 0xffffffff;
					}
				}
			}

			bitmap = Bitmap.createBitmap(width, height,
												Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		} catch (WriterException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * create qr code bitmap with yclogo
	 * @param ctx
	 * @param content  qr content
	 * @param width    qr width
	 * @param height   qr height
	 * @param logo     qr yclogo
	 * @param logoscale  qr yclogo scale
	 * @return
	 */
	public static Bitmap createQRwithlogo(Context ctx, String content, int width, int height, Bitmap logo, int logoscale){
		int scale = 5;
		Bitmap QRbitmap = createQR(content, width, height);
		if((logoscale <= 0 ) || (logoscale >= 10)){
			scale = 5;
		}else {
			scale = logoscale;
		}
		Resources res = ctx.getResources();
		int QRw = QRbitmap.getWidth();
		int QRh = QRbitmap.getHeight();
		int logow = logo.getWidth();
		int logoh = logo.getHeight();
		int logowidth = QRw/scale;
		int logoheight = QRh/scale;
//		float density = res.getDisplayMetrics().density;
		Bitmap logoc;
		if((logow >= QRw/5) || (logoh >=  QRh/5)){
			logoc = scaleBitmap(logo, logowidth, logoheight);
		}else{
			logoc = logo;
		}

		Bitmap withlogo = Bitmap.createBitmap(QRbitmap, 0, 0, QRw, QRh);
//		Bitmap withlogo = Bitmap.createBitmap(QRw, QRh, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(withlogo);
//		canvas.drawBitmap(QRbitmap,0,0 ,null);
		canvas.drawBitmap(logoc, QRw/2 - logoc.getWidth()/2, QRh/2 - logoc.getHeight()/2 ,  null);
		canvas.save();
		canvas.restore();
		return withlogo;
	}

	/**
	 * create qr code with yclogo, yclogo from the project resource
	 * @param ctx
	 * @param content  qr content
	 * @param width    qr width
	 * @param height   qr height
	 * @param resid	yclogo resource id
	 * @return
	 */
	public static Bitmap createQRwithlogo(Context ctx, String content, int width, int height,@DrawableRes int resid){
		Bitmap logo = BitmapFactory.decodeResource(ctx.getResources(), resid);

		return createQRwithlogo(ctx, content, width, height, logo, 5);
	}

	/**
	 * scale bitmap with give width and height
	 * @param src
	 * @param w width scale  = w/src.getWidth()
	 * @param h height scale = h/src.getHeight()
	 * @return
	 */
	public static Bitmap scaleBitmap(Bitmap src, float w, float h){
		Matrix matrix = new Matrix();
		int srcw = src.getWidth();
		int srch = src.getHeight();
		matrix.postScale(w/srcw,h/srch);
		return Bitmap.createBitmap(src, 0,0, srcw, srch, matrix, true);
	}
}

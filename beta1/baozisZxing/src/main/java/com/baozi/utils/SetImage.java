package com.baozi.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by User on 2015/7/3.
 */
public class SetImage {
    public static void setImage(String path, ImageView view) {
        /**
         * ��ȡͼƬ����ת�Ƕȣ���Щϵͳ�����յ�ͼƬ��ת�ˣ��е�û����ת 
         */
        int degree = readPictureDegree(path);

        BitmapFactory.Options opts = new BitmapFactory.Options();//��ȡ����ͼ��ʾ����Ļ��
        opts.inSampleSize = 2;
        Bitmap cbitmap = BitmapFactory.decodeFile(path, opts);

        /**
         * ��ͼƬ��תΪ��ķ��� 
         */
        Bitmap newbitmap = rotaingImageView(degree, cbitmap);
        view.setImageBitmap(newbitmap);
    }

    /**
     * ��ȡͼƬ���ԣ���ת�ĽǶ�
     *
     * @param path ͼƬ���·��
     * @return degree��ת�ĽǶ�
     */
    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * ��תͼƬ
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    private static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //��תͼƬ ����   
        Matrix matrix = new Matrix();

        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // �����µ�ͼƬ   
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}

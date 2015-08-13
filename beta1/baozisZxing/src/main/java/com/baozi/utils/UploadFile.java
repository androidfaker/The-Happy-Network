package com.baozi.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by User on 2015/7/13.
 * ���ļ�ת����16����String(��ͨ��post�����ύ��������)
 */
public class UploadFile {

    /**
     * ��ͼƬת����ʮ������ַ�
     *
     * @param
     * @return
     */

    public static String up1(String uploadFile) {

        return "123";
    }


    // ����һ��byte����
    public static String getBytesFromFile(String uploadFile) {
        Log.i("tag--��ʼ", new Date() + "");

        File f = new File(uploadFile);

// ��ȡ�ļ���С
        long length = f.length();
        if (length > Integer.MAX_VALUE) {
            // �ļ�̫���޷���ȡ
            Log.i("File is to large ", f.getName());
        }
// ����һ������������ļ����
        byte[] bytes = new byte[(int) length];
// ��ȡ��ݵ�byte������
        int offset = 0;
        int numRead = 0;
        try {
            InputStream is = new FileInputStream(f);
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
// ȷ��������ݾ��ȡ
        if (offset < bytes.length) {
            Log.i("Could not completely", f.getName());
        }
// Close the input stream and return bytes

        Log.i("tag--����", new Date() + "");
        return byte2hex(bytes);
    }


    public static String UpImage1(String uploadFile) {
        File f = new File(uploadFile);
        InputStream in;
        String s = null;
        StringBuilder sb = new StringBuilder();
        try {
            in = new FileInputStream(f);
            BufferedInputStream buffer = new BufferedInputStream(in);
            int n;
            while ((n = buffer.read()) != -1) {
                sb.append((char) n / 16 + (char) n % 16);
            }
            s = sb.toString();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    // charתbyte

    private static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);

        return bb.array();

    }

    public static String UpImage(String uploadFile) {
        File f = new File(uploadFile);
        FileInputStream fis = null;
        //��ΪFileû�ж�д��������������Ҫ�и�InputStream
        try {
            fis = new FileInputStream(f);
            //����һ���ֽ�����,�൱�ڻ���
            byte[] bytes = new byte[2048];
            int n;      //�õ�ʵ�ʶ�ȡ�����ֽ���
            //ѭ����ȡ
//            String s = "";
            Log.i("tag--��ʼ1", new Date() + "");
            StringBuilder sb = new StringBuilder();
            while ((n = fis.read(bytes)) != -1) {
                //���ֽ�ת��String
//                s = new String(bytes, 0, n);
//                s += bytesToHexString(bytes, n);
//                sb.append(bytesToHexString(bytes, n));
                sb.append(byte2hex(bytes));
            }
            Log.i("tag--����1", new Date() + "");
            Log.i("tag-----s", sb.toString());
            return sb.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //�ر��ļ������������
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }


    private static String bytesToHexString(byte[] src, int n) {
        StringBuilder stringBuilder = new StringBuilder("");
//        Log.i("tag--n", n + "");
//        Log.i("tag--src.length", src.length + "");
        if (src == null || n <= 0) {
            return null;
        }
        for (int i = 0; i < n; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * ��ͼƬת����ʮ������ַ�
     *
     * @param photo
     * @return
     */
    public static String sendPhoto(ImageView photo) {
        Drawable d = photo.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);// (0 - 100)ѹ���ļ�
        byte[] bt = stream.toByteArray();
        String photoStr = byte2hex(bt);
        return photoStr;
    }

//    /**
//     * ������ת�ַ�
//     * @param b
//     * @return
//     */
//    private static final String[] DIGITS_UPPER = { "0", "1", "2", "3", "4", "5",
//    "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
//    public static String byte2hex(byte[] b) {
//        StringBuffer sb = new StringBuffer();
//        String stmp;
//        for (int n = 0; n < b.length; n++) {
//            sb.append(DIGITS_UPPER[(b[n] + 256) % 256 / 16] + DIGITS_UPPER[(b[n] + 256) % 256 % 16]);
////            if (stmp.length() == 1) {
////                sb.append("0" + stmp);
////            } else {
////                sb.append(stmp);
////            }
//        }
//        return sb.toString();
//    }
//    

    /**
     * ������ת�ַ�
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }
}

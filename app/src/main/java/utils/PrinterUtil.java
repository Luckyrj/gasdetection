package utils;

import android.graphics.Bitmap;

import java.io.UnsupportedEncodingException;

import zhiren.gasdetection.AnJian.CheckResultActivity;

/**
 * Author: andy
 * Time:2018/9/10 0010
 * Description:蓝牙打印机打印操作类
 */

public class PrinterUtil {

    /**
     * 打印文本，必须以换行符“\n”结尾
     */
    public static void printText(String textStr) {
        byte[] buffer = getText(textStr);
        CheckResultActivity.pl.write(buffer);
    }

    public static byte[] getText(String textStr) {
        // TODO Auto-generated method stubbyte[] send;
        byte[] send = null;
        try {
            send = textStr.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            send = textStr.getBytes();
        }
        return send;
    }

    /**
     * 打印二维码
     */
    public static void printQR(String str) {
        byte[] btdata = null;
        try {
            btdata = str.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        short datalen = (short) (btdata.length + 3);
        byte pL = (byte) (datalen & 0xff);
        byte pH = (byte) (datalen >> 8);
        /*
         * QR Code 设置单元大小 【格式】 ASCII GS ( k pL pH 1 C n 十六进制 1D 28 6B 03 00 31
		 * 43 n 十进制 29 40 107 03 0 49 67 n 功能：设置QR CODE 单元大小。 说明：·n 对应QR版本号，
		 * 决定QR CODE的高度与宽度。 · 1≤n ≤16。(十六进制为0x01≤n ≤0x0f)
		 */
        write(new byte[]{0x1d, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x43, 0x06});

        write(new byte[]{0x1d, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x45, 49});

        byte[] qrHead = new byte[]{0x1d, 0x28, 0x6b, pL, pH, 0x31, 0x50, 0x30};

        byte[] qrData = new byte[qrHead.length + datalen];
        System.arraycopy(qrHead, 0, qrData, 0, qrHead.length);
        System.arraycopy(btdata, 0, qrData, qrHead.length, btdata.length);
        // //使二维码居中
        write(new byte[]{0x1b, 0x61, 0x01});

        write(qrData);

        write(new byte[]{0x1d, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x51, 0x30});

        write(new byte[]{0x1b, 0x61, 0x00});

    }

    public static boolean write(byte[] bt) {
        CheckResultActivity.pl.write(bt);
        return true;
    }


    /**
     * 指令打印一维码
     */
    public static void printBAR(String str) {

        byte[] btdata = null;
        try {
            btdata = str.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Enable the barcode
        write(new byte[]{0x1d, 0x45, 0x43, 0x01});

        // Set the barcode height is 100
        write(new byte[]{0x1d, 0x68, (byte) 0x64});

        // Set HRI character print location on bottom
        write(new byte[]{0x1d, 0x48, 0x02});

        write(new byte[]{0x1d, 0x77, 0x02});

        // Print the barcode use code128
        byte[] qrHead = new byte[]{0x1d, 0x6b, 0x49, (byte) btdata.length};
        // byte[] qrHead=new byte[]{0x1d,0x6b,0x44,(byte) btdata.length};

        byte[] barCodeData = new byte[qrHead.length + btdata.length];
        System.arraycopy(qrHead, 0, barCodeData, 0, qrHead.length);
        System.arraycopy(btdata, 0, barCodeData, qrHead.length, btdata.length);

        //条码内容不显示
        write(new byte[]{0x1d, 0x48, 0x00});
        // //使条码居中
        write(new byte[]{0x1b, 0x61, 0x01});
        write(barCodeData);
        printText("\r\n");
        //恢复居左
        write(new byte[]{0x1b, 0x61, 0x01});
    }

    /*************************************************************************
     * 假设一个240*240的图片，分辨率设为24, 共分10行打印 每一行,是一个 240*24 的点阵, 每一列有24个点,存储在3个byte里面。
     * 每个byte存储8个像素点信息。因为只有黑白两色，所以对应为1的位是黑色，对应为0的位是白色
     **************************************************************************/
    /**
     * 把一张Bitmap图片转化为打印机可以打印的字节流
     *
     * @param bmp
     * @return
     */
    public static byte[] draw2PxPoint(Bitmap bmp) {
        // 用来存储转换后的 bitmap 数据。为什么要再加1000，这是为了应对当图片高度无法
        // 整除24时的情况。比如bitmap 分辨率为 240 * 250，占用 7500 byte，
        // 但是实际上要存储11行数据，每一行需要 24 * 240 / 8 =720byte 的空间。再加上一些指令存储的开销，
        // 所以多申请 1000byte 的空间是稳妥的，不然运行时会抛出数组访问越界的异常。
        int size = bmp.getWidth() * bmp.getHeight() / 8 + 1000;
        byte[] data = new byte[size];
        int k = 0;
        // 设置行距为0的指令
        data[k++] = 0x1B;
        data[k++] = 0x33;
        data[k++] = 0x00;
        // 逐行打印
        for (int j = 0; j < bmp.getHeight() / 24f; j++) {
            // 打印图片的指令
            data[k++] = 0x1B;
            data[k++] = 0x2A;
            data[k++] = 33;
            data[k++] = (byte) (bmp.getWidth() % 256); // nL
            data[k++] = (byte) (bmp.getWidth() / 256); // nH
            // 对于每一行，逐列打印
            for (int i = 0; i < bmp.getWidth(); i++) {
                // 每一列24个像素点，分为3个字节存储
                for (int m = 0; m < 3; m++) {
                    // 每个字节表示8个像素点，0表示白色，1表示黑色
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(i, j * 24 + m * 8 + n, bmp);
                        data[k] += data[k] + b;
                    }
                    k++;
                }
            }
            data[k++] = 10;// 换行
        }
        return data;
    }

    /**
     * 灰度图片黑白化，黑色是1，白色是0
     *
     * @param x
     *            横坐标
     * @param y
     *            纵坐标
     * @param bit
     *            位图
     * @return
     */
    public static byte px2Byte(int x, int y, Bitmap bit) {
        if (x < bit.getWidth() && y < bit.getHeight()) {
            byte b;
            int pixel = bit.getPixel(x, y);
            int red = (pixel & 0x00ff0000) >> 16; // 取高两位
            int green = (pixel & 0x0000ff00) >> 8; // 取中两位
            int blue = pixel & 0x000000ff; // 取低两位
            int gray = RGB2Gray(red, green, blue);
            if (gray < 128) {
                b = 1;
            } else {
                b = 0;
            }
            return b;
        }
        return 0;
    }

    /**
     * 图片灰度的转化
     */
    private static int RGB2Gray(int r, int g, int b) {
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b); // 灰度转化公式
        return gray;
    }
}

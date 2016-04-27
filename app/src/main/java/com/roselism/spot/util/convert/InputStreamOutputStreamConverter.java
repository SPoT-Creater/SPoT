package com.roselism.spot.util.convert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 输入流转输出流
 * Created by simon on 2016/4/27.
 */
public class InputStreamOutputStreamConverter implements Converter<InputStream, OutputStream> {

    File out; // 输出文件

    /**
     * @param out 设置输出的文件
     */
    public InputStreamOutputStreamConverter(File out) {
        this.out = out;
    }

    @Override
    public OutputStream convert(InputStream in) {

//        inputStream = helper.getConnection().getInputStream();
        OutputStream output = null;
        try {
            output = new FileOutputStream(out);
            int len;
            byte[] buffer = new byte[1024 * 2];
            while ((len = in.read(buffer)) != -1)
                output.write(buffer, 0, len);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // 找不到相应文件
        } catch (IOException e) {
            // IO操作异常
            e.printStackTrace();
        }
        return output;
    }
}

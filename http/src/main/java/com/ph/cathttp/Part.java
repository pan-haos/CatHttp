package com.ph.cathttp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.ph.cathttp.MultipartBody.END_LINE;


/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-11-8  下午9:56
 */
public abstract class Part {

    private Part() {
    }

    public abstract String contentType();

    public abstract String heads();

    public abstract void write(OutputStream ous) throws IOException;


    /**
     * 创建构建form的part
     *
     * @param key
     * @param value
     * @return
     */
    public static Part create(final String key, final String value) {

        return new Part() {
            @Override
            public String contentType() {
                return null;
            }

            @Override
            public String heads() {
                return Util.trans2FormHead(key);
            }

            @Override
            public void write(OutputStream ous) throws IOException {
                ous.write(heads().getBytes("UTF-8"));
                ous.write(END_LINE);
                ous.write(value.getBytes("UTF-8"));
                ous.write(END_LINE);
            }
        };
    }


    public static Part create(final String type, final String key, final File file) {
        if (file == null) throw new NullPointerException("file 为空");
        if (!file.exists()) throw new IllegalStateException("file 不存在");

        return new Part() {
            @Override
            public String contentType() {
                return type;
            }

            @Override
            public String heads() {
                return Util.trans2FileHead(key, file.getName());
            }

            @Override
            public void write(OutputStream ous) throws IOException {
                ous.write(heads().getBytes());
                ous.write("Content-Type: ".getBytes());
                ous.write(Util.getUTF8Bytes(contentType()));
                ous.write(END_LINE);
                ous.write(END_LINE);
                writeFile(ous, file);
                ous.write(END_LINE);
                ous.flush();
            }

            /**
             * 写出文件
             * @param ous　输出流
             * @param file　文件
             */
            private void writeFile(OutputStream ous, File file) throws IOException {
                FileInputStream ins = null;
                try {
                    ins = new FileInputStream(file);
                    int len;
                    byte[] bytes = new byte[2048];
                    while ((len = ins.read(bytes)) != -1) {
                        ous.write(bytes, 0, len);
                    }
                } finally {
                    if (ins != null) {
                        ins.close();
                    }
                }
            }
        };

    }
}

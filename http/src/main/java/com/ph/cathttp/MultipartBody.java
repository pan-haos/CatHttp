package com.ph.cathttp;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-11-4  下午10:51
 */
public class MultipartBody extends RequestBody {

    public static final String disposition = "content-disposition: form-data; ";
    public static final byte[] END_LINE = {'\r', '\n'};
    public static final byte[] PREFIX = {'-', '-'};

    final List<Part> parts;
    final String boundary;

    public MultipartBody(Builder builder) {
        this.parts = builder.parts;
        this.boundary = builder.boundary;
    }

    @Override
    public String contentType() {
        return "multipart/from-data; boundary=" + boundary;
    }

    @Override
    public void writeTo(OutputStream ous) throws IOException {
        try {
            for (Part part : parts) {
                ous.write(PREFIX);
                ous.write(boundary.getBytes("UTF-8"));
                ous.write(END_LINE);
                part.write(ous);
            }
            ous.write(PREFIX);
            ous.write(boundary.getBytes("UTF-8"));
            ous.write(PREFIX);
            ous.write(END_LINE);
            ous.flush();
        } finally {
            if (ous != null) {
                ous.close();
            }
        }
    }

    public static class Builder {

        private String boundary;
        private List<Part> parts;

        public Builder() {
            this(UUID.randomUUID().toString());
        }

        private Builder(String boundary) {
            this.parts = new ArrayList<>();
            this.boundary = boundary;
        }

        public Builder addPart(String type, String key, File file) {
            if (key == null) throw new NullPointerException("part name == null");
            parts.add(Part.create(type, key, file));
            return this;
        }

        public Builder addForm(String key, String value) {
            if (key == null) throw new NullPointerException("part name == null");
            parts.add(Part.create(key, value));
            return this;
        }

        public MultipartBody build() {
            if (parts.isEmpty()) throw new NullPointerException("part list == null");
            return new MultipartBody(this);
        }
    }

}

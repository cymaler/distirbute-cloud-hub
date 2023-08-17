package com.cymal.model;

public class DchResProtocol {

    private byte [] magic;

    private byte version;

    private byte code;

    private byte stype;

    private byte ctype;

    private int headLen;

    private int len;

    private long resId;

    private long timestamp;

    private byte [] body;

    public byte[] getMagic() {
        return magic;
    }

    public void setMagic(byte[] magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public byte getStype() {
        return stype;
    }

    public void setStype(byte stype) {
        this.stype = stype;
    }

    public byte getCtype() {
        return ctype;
    }

    public void setCtype(byte ctype) {
        this.ctype = ctype;
    }

    public int getHeadLen() {
        return headLen;
    }

    public void setHeadLen(int headLen) {
        this.headLen = headLen;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}

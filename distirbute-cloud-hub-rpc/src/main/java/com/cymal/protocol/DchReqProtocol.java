package com.cymal.protocol;

public class DchReqProtocol {

    private byte [] magic;

    private byte version;

    private byte method;

    private byte stype;

    private byte ctype;

    private int headLen;

    private int len;

    private long reqId;

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

    public byte getMethod() {
        return method;
    }

    public void setMethod(byte method) {
        this.method = method;
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

    public long getReqId() {
        return reqId;
    }

    public void setReqId(long reqId) {
        this.reqId = reqId;
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

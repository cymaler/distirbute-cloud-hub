package com.cymal.model;

public class DchContext {

    private DchReqProtocol reqProtocol;

    private DchBody dchBody;

    private Object result;

    private DchResProtocol resProtocol;

    public DchReqProtocol getReqProtocol() {
        return reqProtocol;
    }

    public void setReqProtocol(DchReqProtocol reqProtocol) {
        this.reqProtocol = reqProtocol;
    }

    public DchBody getDchBody() {
        return dchBody;
    }

    public void setDchBody(DchBody dchBody) {
        this.dchBody = dchBody;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public DchResProtocol getResProtocol() {
        return resProtocol;
    }

    public void setResProtocol(DchResProtocol resProtocol) {
        this.resProtocol = resProtocol;
    }
}

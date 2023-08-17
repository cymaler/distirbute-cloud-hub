package com.cymal.client;

public interface DchCallback<Res>{

    void received(Res response, Exception ex);

}

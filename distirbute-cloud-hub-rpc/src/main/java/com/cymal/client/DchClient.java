package com.cymal.client;

import com.cymal.model.DchReqProtocol;
import com.cymal.model.DchResProtocol;

public interface DchClient {

    void ping();

    void isservice();

    void destroy();

    DchResProtocol invoke(DchReqProtocol request, int timeoutMillis);

    void invoke(DchReqProtocol request, DchCallback<DchResProtocol> callback, int timeoutMillis);

}

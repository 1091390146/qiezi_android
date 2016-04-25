package com.example.qiezi.view;

import android.content.Intent;

/**
 * Created by 潘 on 2016/2/25.
 */
public interface ReceiverInterface {
    /**
     * @author zhuchongkun
     * @param actions
     * @exception Registratioin
     *                of radio
     *
     */
    void regiserRadio(String[] actions);

    /**
     * @author zhuchongkun
     * @exception Cancellatioin
     *                of radio
     */
    void destroyRadio();

    /**
     * @author zhuchongkun
     * @param intent
     * @exception To
     *                deal with radio
     */
    void dealWithRadio(Intent intent);

}

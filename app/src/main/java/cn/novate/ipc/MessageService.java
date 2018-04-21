package cn.novate.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/4/21 16:06
 * Version 1.0
 * Params:
 * Description:    服务器端的代码
*/

public class MessageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final UserAidl.Stub mBinder = new UserAidl.Stub()
    {

        @Override
        public String getUserName() throws RemoteException {
            return "Novate@163.com";
        }

        @Override
        public String getUserPassword() throws RemoteException {
            return "123456";
        }
    };
}

package com.compassionapp.langChance;

import android.content.res.Configuration;  
import android.os.RemoteException;  
  
/** 
 * @author Sodino E-mail:sodinoopen@hotmail.com 
 * @version Time�?011-7-10 上午11:37:46 
 */  
public abstract interface IActivityManager {  
    public abstract Configuration getConfiguration() throws RemoteException;  
  
    public abstract void updateConfiguration(Configuration paramConfiguration)  
            throws RemoteException;  
}  

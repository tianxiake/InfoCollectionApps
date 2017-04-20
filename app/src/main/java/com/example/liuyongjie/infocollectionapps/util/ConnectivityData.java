package com.example.liuyongjie.infocollectionapps.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.ProxyInfo;
import android.os.Build;
import android.util.Log;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyongjie on 2017/4/14.
 * 这个类主要是ConnectivityManager中的信息
 */

public class ConnectivityData {

    private static final ILogger log = LoggerFactory.getLogger("ConnectivityData");
    private ConnectivityManager connectivityManager;

    public ConnectivityData(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取AllNetWorkInfo Android5.0以上API生效
     *
     * @return 5.0以下系统或者是取不到会返回null
     */
    public List<NetworkInfo> getAllNetWorkInfo() {
        ArrayList<NetworkInfo> list = new ArrayList<>();
        Network[] networks = getAllNetworks();
        if (networks != null) {
            for (int i = 0; i < networks.length; i++) {
                NetworkInfo networkInfo = getNetworkInfo(networks[i]);
                list.add(networkInfo);
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * 获取AllNetWorkLinkProperties Android5.0以上API生效
     *
     * @return 5.0以下系统或者是取不到会返回null
     */
    public List<LinkProperties> getAllNetWorkLinkProperties() {
        ArrayList<LinkProperties> list = new ArrayList<>();
        Network[] networks = getAllNetworks();
        if (networks != null) {
            for (int i = 0; i < networks.length; i++) {
//                NetworkInfo networkInfo = getNetworkInfo(networks[i]);
                LinkProperties linkProperties = getLinkProperties(networks[i]);
                list.add(linkProperties);
            }
            return list;
        } else {
            return null;
        }
    }


    /**
     * 获取AllNetworkCapabilities Android5.0以上API生效
     *
     * @return 5.0以下系统或者是取不到会返回null
     */
    public List<NetworkCapabilities> getAllNetworkCapabilities() {
        ArrayList<NetworkCapabilities> list = new ArrayList<>();
        Network[] networks = getAllNetworks();
        if (networks != null) {
            for (int i = 0; i < networks.length; i++) {
                NetworkCapabilities networkCapabilities = getNetworkCapabilities(networks[i]);
                list.add(networkCapabilities);
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * Android6.0以上的API
     * 获取网络代理的信息
     */
    public ProxyInfo getDefaultProxy() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            ProxyInfo defaultProxy = connectivityManager.getDefaultProxy();
            log.verbose(Author.liuyongjie, Business.dev_test, "defaultProxy={}", defaultProxy);
            return defaultProxy;
        }
        return null;
    }


    /**
     * Android 5.0以上
     * 当前可追踪的的所有的网络
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     */
    private Network[] getAllNetworks() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Network[] allNetworks = connectivityManager.getAllNetworks();
//            Log.d("Networks", allNetworks.toString());
//            log.verbose(Author.liuyongjie, Business.dev_test, "allNetworks={}", allNetworks);
            if (allNetworks != null) {
                Log.d("lyj", "-------------------allNetworks---------------------------");
                for (int i = 0; i < allNetworks.length; i++) {
                    getNetworkInfo(allNetworks[i]);
                    getLinkProperties(allNetworks[i]);
                    getNetworkCapabilities(allNetworks[i]);
                }
                Log.d("lyj", "--------------------allNetworks--------------------------");
            }

            return allNetworks;

        }
        return null;
    }


    /**
     * Android6.0以上API
     * 获取和当前进程有关的Network
     */
    public Network getBoundNetworkForProcess() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getBoundNetworkForProcess();
            return network;
        }
        return null;
    }

    /**
     * Android7.0 以上API
     * RESTRICT_BACKGROUND_STATUS_DISABLED 1
     * RESTRICT_BACKGROUND_STATUS_ENABLED  3
     * RESTRICT_BACKGROUND_STATUS_WHITELISTED 2
     * 取不到返回-1
     */
    public int getRestrictBackgroundStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            int restrictBackgroundStatus = connectivityManager.getRestrictBackgroundStatus();
            log.verbose(Author.liuyongjie, Business.dev_test, "restrictBackgroundStatus={}", restrictBackgroundStatus);
            return restrictBackgroundStatus;
        }
        return -1;
    }


    /**
     * 获取活跃的网络信息
     */
    public NetworkInfo getActiveNetworkInfo() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        log.verbose(Author.liuyongjie, Business.dev_test, "activeNetworkInfo={}", activeNetworkInfo);
        return activeNetworkInfo;
    }


    /**
     * Android5.0 以上 获取NetworkInfo
     *
     * @param network 指定的Network
     */
    public NetworkInfo getNetworkInfo(Network network) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            log.verbose(Author.liuyongjie, Business.dev_test, "networkInfo={}", networkInfo);
            return networkInfo;
        }
        return null;
    }

    /**
     * Android5.0以上API,获取指定Network的连接的属性值
     *
     * @param network 指定的Network
     */
    private LinkProperties getLinkProperties(Network network) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LinkProperties linkProperties = connectivityManager.getLinkProperties(network);
            log.verbose(Author.liuyongjie, Business.dev_test, "linkProperties={}", linkProperties);
            return linkProperties;
        }
        return null;
    }

//    /**
//     * android6.0以上API，获取活跃的网络
//     */
//    public void getActiveNetwork() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            Network activeNetwork = connectivityManager.getActiveNetwork();
//            Log.d("lyj", "--------------------ActiveNetwork--------------------------");
//            getNetworkInfo(activeNetwork);
//            Log.d("lyj", "--------------------ActiveNetwork--------------------------");
//        }
//    }


    /**
     * Android5.0以上API 获取指定网络的兼容性信息
     *
     * @param network 指定的网络
     */
    private NetworkCapabilities getNetworkCapabilities(Network network) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            log.verbose(Author.liuyongjie, Business.dev_test, "networkCapabilities={}", networkCapabilities);
            return networkCapabilities;
        }
        return null;
    }


}

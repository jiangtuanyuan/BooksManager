package cn.books.threadproxy;

/**
 *  ThreadPoolProxy工厂类,封装对ThreadPoolProxy创建
 * @author LiuTao
 */
public class ThreadPoolProxyFactory {
    static ThreadPoolProxy mNormalThreadPoolProxy;
    static ThreadPoolProxy mDownLoadThreadPoolProxy;

    /**
     * 得到普通类型的线程池代理
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 得到下载类型的线程池代理
     */
    public static ThreadPoolProxy getDownLoadThreadPoolProxy() {
        if (mDownLoadThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownLoadThreadPoolProxy == null) {
                    mDownLoadThreadPoolProxy = new ThreadPoolProxy(3, 3);
                }
            }
        }
        return mDownLoadThreadPoolProxy;
    }
}

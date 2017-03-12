package com.thedarshan.sparkSkeleton;

public abstract class AbstractSparkApplication{
    protected String mountPoint;
    protected String dataDirectory;

    public String getMountPoint() {
        return mountPoint;
    }

    public AbstractSparkApplication(String mountPoint, String dataDirectory) {
        this.mountPoint = mountPoint;
        this.dataDirectory=dataDirectory;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }
    
    public abstract void init() throws Exception;
    public abstract void shutdown();    
    
}

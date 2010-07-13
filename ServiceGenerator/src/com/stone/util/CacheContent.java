package com.stone.util;

public interface CacheContent {

	public byte[] fetchCacheContentFromWeb();
	
	public void setWebUrl(String url);
}

package com.along.outboundmanage.utill;


public class EnclosureUtil {
	/**
	 * 根据高德围栏Api
	 * add: curl -i -X POST https://restapi.amap.com/v4/geofence/meta?key=用户key -d 'json'
	 * updata: curl -i -X POST https://restapi.amap.com/v4/geofence/meta?key=key&gid=gid&method=patch -d 'json'
	 * select: curl -i -X GET https://restapi.amap.com/v4/geofence/meta?key=用户key
	 * delete: curl -i -X POST https://restapi.amap.com/v4/geofence/meta?key=key&gid=gid&method=delete -d 'json'
	 * start/stop: curl -i -X POST https://restapi.amap.com/v4/geofence/meta?key=key&gid=gid&method=patch -d 'json'
	 * listen: https://restapi.amap.com/v4/geofence/status
	 */
	private static final String ENCLOSURE_URL = "https://restapi.amap.com/v4/geofence/meta";


}

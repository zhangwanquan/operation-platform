package com.lys.platform.util;

import com.lys.platform.entity.MallInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/30 16:56
 * @version: 1.0
 */
public class LocationGeoUtil {

    @Autowired
    private GeoOperations<String, Object> geoOperations;

    private static final String GEO_KEY = "locations:points";

    public static List<RedisGeoCommands.GeoLocation<MallInfo>> convertToGeoInfo(List<MallInfo> mallInfos) {
        return mallInfos.stream().map(mallInfo ->
                new RedisGeoCommands.GeoLocation<>(mallInfo,
                        new Point(mallInfo.getLongitude(), mallInfo.getLatitude()))).collect(Collectors.toList());

    }

}

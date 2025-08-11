package com.lys.platform.service.impl;

import com.lys.platform.dao.MallClickMapper;
import com.lys.platform.dao.MallInfoMapper;
import com.lys.platform.dao.MallLikeMapper;
import com.lys.platform.dao.MallViewMapper;
import com.lys.platform.dao.RequirementMapper;
import com.lys.platform.dao.StoreInfoMapper;
import com.lys.platform.entity.Customer;
import com.lys.platform.entity.MallClick;
import com.lys.platform.entity.MallInfo;
import com.lys.platform.entity.MallLike;
import com.lys.platform.entity.MallView;
import com.lys.platform.entity.Requirement;
import com.lys.platform.entity.StoreInfo;
import com.lys.platform.enums.DistanceRangeType;
import com.lys.platform.enums.MallSortType;
import com.lys.platform.service.MallService;
import com.lys.platform.util.LocationGeoUtil;
import com.lys.platform.util.RedisUtil;
import com.lys.platform.vo.FloorStoreInfoVo;
import com.lys.platform.vo.MallInfoVo;
import com.lys.platform.vo.MallQueryVo;
import com.lys.platform.vo.RequirementReqVo;
import com.lys.platform.vo.StoreInfoVo;
import com.lys.platform.vo.StoreQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/28 16:29
 * @version: 1.0
 */
@Service
public class MallServiceImpl implements MallService {
    @Autowired
    private MallLikeMapper mallLikeMapper;
    @Autowired
    private MallViewMapper mallViewMapper;
    @Autowired
    private MallClickMapper mallClickMapper;
    @Autowired
    private MallInfoMapper mallInfoMapper;
    @Autowired
    private RequirementMapper requirementMapper;
    @Autowired
    private StoreInfoMapper storeInfoMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void likeMall(String phone, Integer mallId, Boolean like) {
        MallLike query = new MallLike();
        query.setPhone(phone);
        query.setMallId(mallId);
        MallLike mallLike = mallLikeMapper.selectOne(query);
        if (null != mallLike) {
            mallLike.setDisabled(like ? 0 : 1);
            mallLike.setUpdateTime(new Date());
            mallLikeMapper.updateByPrimaryKey(mallLike);
        } else {
            mallLike = new MallLike();
            mallLike.setPhone(phone);
            mallLike.setMallId(mallId);
            mallLike.setDisabled(like ? 0 : 1);
            mallLike.setCreateTime(new Date());
            mallLikeMapper.insertSelective(mallLike);
        }
    }

    @Override
    public void viewMall(String phone, Integer mallId) {
        MallView query = new MallView();
        query.setPhone(phone);
        query.setMallId(mallId);
        MallView mallView = mallViewMapper.selectOne(query);
        Date now = new Date();
        if (null != mallView) {
            mallView.setLastViewTime(now);
            mallView.setCount(mallView.getCount() + 1);
            mallView.setUpdateTime(now);
             mallViewMapper.updateByPrimaryKey(mallView);
        } else {
            mallView = new MallView();
            mallView.setPhone(phone);
            mallView.setMallId(mallId);
            mallView.setLastViewTime(now);
            mallView.setCount(1);
            mallView.setCreateTime(now);
            mallViewMapper.insertSelective(mallView);
        }
    }


    @Override
    public List<MallInfoVo> getLikeMallList(String phone) {
        MallLike query = new MallLike();
        query.setPhone(phone);
        query.setDisabled(0);
        List<MallLike> mallLikeList = mallLikeMapper.select(query);
        if (CollectionUtils.isEmpty(mallLikeList)) {
            return Collections.emptyList();
        }
        Map<Integer, Date> mallLikeMap = mallLikeList.stream()
                .collect(Collectors.toMap(MallLike::getMallId, MallLike::getUpdateTime));
        List<Integer> mallIds = mallLikeList.stream()
                .map(MallLike::getMallId).collect(Collectors.toList());

        return getMallInfoVos(mallIds, mallLikeMap);
    }

    private List<MallInfoVo> getMallInfoVos(List<Integer> mallIds, Map<Integer, Date> mallIdDateMap) {
        return getAllMallListFromCache(mallIds).stream()
                .sorted(Comparator.comparing((MallInfo mallInfo) -> mallIdDateMap.get(mallInfo.getId())).reversed())
                .map(mallInfo -> {
                    MallInfoVo vo = new MallInfoVo();
                    BeanUtils.copyProperties(mallInfo, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MallInfoVo> getViewMallList(String phone) {
        MallView query = new MallView();
        query.setPhone(phone);
        List<MallView> mallViewList = mallViewMapper.select(query);
        if (CollectionUtils.isEmpty(mallViewList)) {
            return Collections.emptyList();
        }
        Map<Integer, Date> mallLastViewMap = mallViewList.stream()
                .collect(Collectors.toMap(MallView::getMallId, MallView::getLastViewTime));
        List<Integer> mallIds = mallViewList.stream()
                .map(MallView::getMallId).collect(Collectors.toList());
        return getMallInfoVos(mallIds, mallLastViewMap);
    }

    @Override
    public MallInfoVo getMallDetail(Integer mallId) {
        // todo 后面增加缓冲,从缓冲中获取商场详情
        // 获取商场详情
        MallInfo mallInfo = mallInfoMapper.selectByPrimaryKey(mallId);
        MallInfoVo mallInfoVo = new MallInfoVo();
        BeanUtils.copyProperties(mallInfo, mallInfoVo);
        return mallInfoVo;
    }

    @Override
    public void clickMall(Integer mallId) {
        MallClick query = new MallClick();
        query.setMallId(mallId);
        MallClick mallClick = mallClickMapper.selectOne(query);
        Date now = new Date();
        if (null != mallClick) {
            mallClick.setCount(mallClick.getCount() + 1);
            mallClick.setUpdateTime(now);
            mallClickMapper.updateByPrimaryKey(mallClick);
        } else {
            mallClick = new MallClick();
            mallClick.setMallId(mallId);
            mallClick.setCount(1);
            mallClick.setCreateTime(now);
            mallClickMapper.insertSelective(mallClick);
        }
    }

    @Override
    public Boolean requirementMall(RequirementReqVo requirementReqVo) {
        boolean success = true;
        Requirement requirement = new Requirement();
        BeanUtils.copyProperties(requirementReqVo, requirement);

        Requirement exists = requirementMapper.selectOne(requirement);
        if (null != exists) {
            return false;
        }
        requirementMapper.insertSelective(requirement);
        return success;
    }

    @Override
    public  Map<String, FloorStoreInfoVo> getStoreList(StoreQueryVo storeQueryVo) {
        StoreInfo query = new StoreInfo();
        query.setMallId(storeQueryVo.getMallId());
        // 处理传入的查询条件,名称迷糊查询在java中过滤
        if (null != storeQueryVo.getRentalStatus()){
            query.setRentalStatus(storeQueryVo.getRentalStatus());
        }
        if (null != storeQueryVo.getBusinessType()){
            query.setBusinessType(storeQueryVo.getBusinessType());
        }
        List<StoreInfo> storeInfoList = storeInfoMapper.select(query);
        if (CollectionUtils.isEmpty(storeInfoList)) {
            return Collections.emptyMap();
        }
        List<StoreInfoVo> all = storeInfoList.stream()
                // 处理名称模糊查询
                .filter(e -> {
                    if (StringUtils.isNotEmpty(storeQueryVo.getName())) {
                        return e.getName().contains(storeQueryVo.getName());
                    }
                    return true;
                }).sorted(doSorted(storeQueryVo.getSortType()))
                .map(this::convertToVo)  // 转换为VO
                .collect(Collectors.toList());

        // 按楼层聚合数据,排序规则,未租排在最上面,已租的排在后面,按照录入顺序,先填的排在前面
        Map<String, FloorStoreInfoVo> floorStoreInfoVoMap = storeInfoList.stream()
                .collect(Collectors.groupingBy(
                        StoreInfo::getFloor,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    // 转换为 StoreInfoVo 并排序
                                    List<StoreInfoVo> voList = list.stream()
                                            // 处理排序
                                            .sorted(doSorted(storeQueryVo.getSortType()))
                                            .map(this::convertToVo)  // 转换为VO
                                            .collect(Collectors.toList());

                                    return new FloorStoreInfoVo(voList, list.size());
                                }
                        )
                ));
        floorStoreInfoVoMap.put("ALL", new FloorStoreInfoVo(all, all.size()));
        // 按照楼层字母排序
        TreeMap<String, FloorStoreInfoVo> sortedMap = new TreeMap<>(floorStoreInfoVoMap);
        return sortedMap;
    }

    /**
     * 默认排序,未租排在最上面,已租的排在后面,按照录入顺序,先填的排在前面
     * 排序规则,0默认排序、1按单价从高到低、2按单价从低到高3、按面积从高到低4、按面积从低到高
     * @param sortType
     * @return
     */
    private static Comparator<StoreInfo> doSorted(Integer sortType) {
        if (sortType.equals(1)) {
            return Comparator.comparing(StoreInfo::getUnitPrice).reversed();
        } else if (sortType.equals(2)) {
            return Comparator.comparing(StoreInfo::getUnitPrice);
        } else if (sortType.equals(3)) {
            return Comparator.comparing(StoreInfo::getStoreArea).reversed();
        } else if (sortType.equals(4)) {
            return Comparator.comparing(StoreInfo::getStoreArea);
        }
        return Comparator.comparing((StoreInfo s)
                        -> s.getRentalStatus() != 0)
                .thenComparing(StoreInfo::getCreateTime);
    }

    @Override
    public List<MallInfoVo> getMallList(MallQueryVo mallQueryVo) {
        // 获取商场列表
        List<MallInfo> mallInfos = getAllMallListFromCache();
        List<RedisGeoCommands.GeoLocation<MallInfo>> geoLocations = LocationGeoUtil.convertToGeoInfo(mallInfos);
        redisUtil.addLocations(RedisUtil.GEO_KEY, geoLocations);

        // 先判断是否有距离搜索,如果有通过redis去搜索,过滤得到id列表
        Integer distanceRange = mallQueryVo.getDistanceRange();
        if (null != distanceRange) {
            DistanceRangeType distanceRangeType = DistanceRangeType.of(distanceRange);
            List<GeoResult<RedisGeoCommands.GeoLocation<MallInfo>>> nearby =
                    redisUtil.findNearby(RedisUtil.GEO_KEY, mallQueryVo.getLongitude(), mallQueryVo.getLatitude(),
                            Optional.ofNullable(distanceRangeType).map(DistanceRangeType::range).orElse(0.5),
                            Metrics.KILOMETERS);

            mallInfos = nearby.stream()
                    .map(geoResult -> geoResult.getContent().getName())
                    .collect(Collectors.toList());
        }
        // 按搜索条件过滤和排序
        return filterMallByQuery(mallInfos, mallQueryVo);
    }

    private List<MallInfoVo> filterMallByQuery(List<MallInfo> mallInfos, MallQueryVo mallQueryVo) {
        if (CollectionUtils.isEmpty(mallInfos)) {
            return Collections.emptyList();
        }
        List<MallInfoVo> filteredMallInfoList = mallInfos.stream()
                .filter(mallInfo -> matchCityCode(mallQueryVo, mallInfo)
                        && matchNameAndBusinessName(mallQueryVo, mallInfo)
                        && matchType(mallQueryVo, mallInfo)
                        && matchMallArea(mallQueryVo, mallInfo)
                        && matchStatus(mallQueryVo, mallInfo)
                        && matchBusinessDuration(mallQueryVo, mallInfo)
                        && matchLevel(mallQueryVo, mallInfo)
                        && matchInvestmentArea(mallQueryVo, mallInfo)
                        && matchShopScale(mallQueryVo, mallInfo)
                        && matchBusinessType(mallQueryVo, mallInfo)
                        && matchParkingSpaceNumber(mallQueryVo, mallInfo))
                // 直接使用默认排序,如果发现搜索条件有排序规则,则重新进行排序
                .sorted(Comparator.comparing(MallInfo::getScore).reversed()
                        .thenComparing(MallInfo::getName))
                .map(mallInfo -> {
                    MallInfoVo vo = new MallInfoVo();
                    BeanUtils.copyProperties(mallInfo, vo);
                    return vo;
                })
                .collect(Collectors.toList());


        List<Integer> matchedIdList = filteredMallInfoList.stream().map(MallInfoVo::getId).collect(Collectors.toList());

        // 如果设置了按照热度排名,那么需要查询出访问次数表,根据点击次数进行再次排序
        if (Optional.ofNullable(mallQueryVo.getSortType()).map( i ->MallSortType.CLICK_COUNT.code() == i).orElse(false)) {
            Example example = new Example(Customer.class);
            example.createCriteria().andIn("id", matchedIdList);
            Map<Integer, Integer> clickCoutMap = mallClickMapper.selectByExample(example)
                    .stream().collect(Collectors.toMap(MallClick::getMallId, MallClick::getCount));
            return filteredMallInfoList.stream()
                    .sorted(Comparator.comparing(
                            (MallInfoVo e) -> clickCoutMap.getOrDefault(e.getId(), 0))
                                    .reversed()
                            .thenComparing(MallInfoVo::getName))
                    .collect(Collectors.toList());
        }

        return filteredMallInfoList;
    }

    private boolean matchParkingSpaceNumber(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getParkingSpaceNumber()) {
            return true;
        }
        return mallQueryVo.getParkingSpaceNumber().equals(mallInfo.getParkingSpaceNumber());
    }

    private boolean matchBusinessType(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getBusinessType()) {
            return true;
        }
        return mallQueryVo.getBusinessType().equals(mallInfo.getBusinessType());
    }

    private boolean matchShopScale(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getShopScale()) {
            return true;
        }
        return mallQueryVo.getShopScale().equals(mallInfo.getShopScale());
    }

    private boolean matchInvestmentArea(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getInvestmentArea()) {
            return true;
        }
        return mallQueryVo.getInvestmentArea().equals(mallInfo.getInvestmentArea());
    }

    private boolean matchLevel(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getLevel()) {
            return true;
        }
        return mallQueryVo.getLevel().equals(mallInfo.getLevel());
    }

    private boolean matchBusinessDuration(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getBusinessDuration()) {
            return true;
        }
        return mallQueryVo.getBusinessDuration().equals(mallInfo.getBusinessDuration());
    }

    private boolean matchStatus(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getStatus()) {
            return true;
        }
        return mallQueryVo.getStatus().equals(mallInfo.getStatus());
    }

    private boolean matchMallArea(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getMallArea()) {
            return true;
        }
        return mallQueryVo.getMallArea().equals(mallInfo.getMallArea());
    }

    private boolean matchType(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (null == mallQueryVo.getType()) {
            return true;
        }
        return mallQueryVo.getType().equals(mallInfo.getType());
    }

    private static boolean matchCityCode(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        if (StringUtils.isEmpty(mallQueryVo.getCityCode())) {
            return true;
        }
        return StringUtils.equals(mallInfo.getCityCode(), mallQueryVo.getCityCode());
    }

    private static boolean matchNameAndBusinessName(MallQueryVo mallQueryVo, MallInfo mallInfo) {
        // 商场名称和业态名称做模糊匹配
        if (StringUtils.isEmpty(mallQueryVo.getName())) {
            return true;
        }
        return mallInfo.getName().contains(mallQueryVo.getName())
                || Optional.ofNullable(mallInfo.getBusinessTypeName())
                .filter(name -> mallInfo.getName() != null) // 确保 mallQueryVo.getName() 不为空
                .map(name -> name.contains(mallQueryVo.getName()))
                .orElse(false);
    }

    public List<MallInfo> getAllMallListFromCache() {
        List<MallInfo> mallInfos = (List<MallInfo>) redisUtil.get(RedisUtil.MALL_INFO_KEY);
        if (CollectionUtils.isEmpty(mallInfos)) {
            // 从数据库查询,并存入缓存中
            List<MallInfo> mallInfosFromDb = mallInfoMapper.selectAll();
            redisUtil.setList(RedisUtil.MALL_INFO_KEY, mallInfosFromDb);
            mallInfos = mallInfosFromDb;
        }

        return mallInfos;
    }

    public List<MallInfo> getAllMallListFromCache(List<Integer> mallIds) {
        if (CollectionUtils.isEmpty(mallIds)) {
            return new ArrayList<>();
        }
        List<MallInfo> allMallListFromCache = getAllMallListFromCache();
        return allMallListFromCache.stream()
                .filter(mallInfo -> mallIds.contains(mallInfo.getId())).collect(Collectors.toList());
    }

    private StoreInfoVo convertToVo(StoreInfo storeInfo) {
        StoreInfoVo vo = new StoreInfoVo();
        BeanUtils.copyProperties(storeInfo, vo);
        return vo;
    }
}

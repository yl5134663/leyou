package com.service;

import com.client.GoodsClient;
import com.interceptor.LoginInterceptor;
import com.pojo.Cart;
import com.pojo.Sku;
import com.pojo.UserInfo;
import com.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private GoodsClient goodsClient;
    static final String KEY_PREFIX = "leyou:cart:uid:";
    static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addCart(Cart cart) {
        //获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        //获取redis中的key
        String key = KEY_PREFIX + user.getId();
        //获取hash操作对象
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
        //查询是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean aBoolean = ops.hasKey(skuId.toString());
        if (aBoolean) {
            // 存在，获取购物车数据
            String json = ops.get(skuId.toString()).toString();
            cart = JsonUtils.parse(json, Cart.class);
            // 修改购物车数量
            cart.setNum(cart.getNum() + num);
        }
        else{
            // 不存在，新增购物车数据
            cart.setUserId(user.getId());
            // 其它商品信息，需要查询商品服务
            Sku sku = this.goodsClient.querySkuById(skuId);
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }
        //将购物车数据写入redis
        ops.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    /**
     * 查询购物车
     * @return
     */
    public List<Cart> queryCartList() {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();

        // 判断是否存在购物车
        String key = KEY_PREFIX + user.getId();
        if(!this.redisTemplate.hasKey(key)){
            // 不存在，直接返回
            return null;
        }
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        List<Object> carts = hashOps.values();
        // 判断是否有数据
        if(CollectionUtils.isEmpty(carts)){
            return null;
        }
        // 查询购物车数据
        return carts.stream().map(o -> JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());
    }

    /**
     *修改购物车内容
     * @param cart
     */
    public void updateCarts(Cart cart) {
        // 获取登陆信息
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + userInfo.getId();
        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(key);
        // 获取购物车信息
        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();
        Cart cart1 = JsonUtils.parse(cartJson, Cart.class);
        // 更新数量
        cart1.setNum(cart.getNum());
        // 写入购物车
        hashOperations.put(cart.getSkuId().toString(), JsonUtils.serialize(cart1));
    }

    /**
     * 删除购物车
     * @param skuId
     */
    public void deleteCart(String skuId) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        hashOps.delete(skuId);
    }
}

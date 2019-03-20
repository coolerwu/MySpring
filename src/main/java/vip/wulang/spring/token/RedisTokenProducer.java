package vip.wulang.spring.token;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import vip.wulang.spring.exception.EmptyStringException;
import vip.wulang.spring.token.structure.UserInfo;
import vip.wulang.spring.token.utils.TokenUtil;
import vip.wulang.spring.util.IntegerUtils;
import vip.wulang.spring.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * The class represents redis token producer.
 *
 * @author CoolerWu on 2019/3/16.
 * @version 1.0
 */
@SuppressWarnings("all")
public class RedisTokenProducer implements ITokenProducer {
    private static final int WEEK_SECOND = 3600 * 24 * 7;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private JedisPool jedisPool;

    public RedisTokenProducer() {
        init(new JedisPoolConfig(), "localhost", 6379);
    }

    public RedisTokenProducer(String host, int port) {
        if (StringUtils.isEmpty(host)) {
            throw new EmptyStringException("host is empty.");
        }

        if (IntegerUtils.lessThanOrEqualZero(port)) {
            throw new IllegalArgumentException("port is illegal.");
        }

        init(new JedisPoolConfig(), host, port);
    }

    public RedisTokenProducer(String host, int port, int maxTotal, int maxIdle, int minIdle,
                              boolean blockWhenExhausted, int maxWaitMillis) {
        if (StringUtils.isEmpty(host)) {
            throw new EmptyStringException("localhost is empty.");
        }

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setBlockWhenExhausted(blockWhenExhausted);
        config.setMaxWaitMillis(maxWaitMillis);
        init(config, host, port);
    }

    private void init(JedisPoolConfig config, String host, int port) {
        if (config.getMaxTotal() <= 0) {
            config.setMaxTotal(8);
        }

        if (config.getMaxIdle() <= 0) {
            config.setMaxIdle(8);
        }

        if (config.getMinIdle() < 0) {
            config.setMinIdle(0);
        }

        if (config.getBlockWhenExhausted() && config.getMaxWaitMillis() < -1L) {
            config.setMaxWaitMillis(-1L);
        }

        jedisPool = new JedisPool(config, host, port);
    }

    @Override
    public String productToken(String username, String password) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            if (username == null || password == null) {
                throw new NullPointerException("username or password is null.");
            }

            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                throw new EmptyStringException("username or password is empty.");
            }

            UserInfo user = new UserInfo(username, password);
            String token = TokenUtil.createToken(user);

            if (StringUtils.isEmpty(token)) {
                throw new EmptyStringException("localhost is empty.");
            }

            if (jedis.exists(username)) {
                String token_origin = jedis.get(username);
                jedis.del(username);
                jedis.del(token_origin);

                while (jedis.exists(token)) {
                    user.updateSecretKey();
                    // add secret
                    token = TokenUtil.createToken(user);
                }
            }

            // set seconds
            jedis.expire(token, WEEK_SECOND);
            jedis.expire(username, WEEK_SECOND);
            Map<String, String> map = new HashMap<>();
            map.put(USERNAME, username);
            map.put(PASSWORD, password);
            jedis.hmset(token, map);
            jedis.set(username, token);
            return token;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public boolean whetherHaveTokenStorageData(String tokenHeader) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.exists(tokenHeader);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void deleteTokenStorageData(String tokenHeader) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            if (jedis.exists(tokenHeader)) {
                Map<String, String> map = jedis.hgetAll(tokenHeader);
                String username = jedis.get(map.get(USERNAME));
                jedis.del(tokenHeader);
                jedis.del(username);
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public UserInfo getTokenStorageData(String tokenHeader) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            if (jedis.exists(tokenHeader)) {
                Map<String, String> map = jedis.hgetAll(tokenHeader);
                return new UserInfo(map.get(USERNAME), map.get(PASSWORD));
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    @Override
    public void updateTokenStorageData(String tokenHeader) {
        throw new RuntimeException();
    }
}

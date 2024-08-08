package yeomeong.common.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@Repository
public class JwtRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public JwtRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value, long expirationMinutes){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, value, Duration.ofSeconds(expirationMinutes));
        log.info("key: {}", key);
        log.info("value: {}", value);
        log.info("만료 시간, 분: {}", Duration.ofSeconds(expirationMinutes));
    }

    public String findByKey(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
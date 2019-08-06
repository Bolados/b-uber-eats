package tech.omeganumeric.api.ubereats.services.otp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Description(value = "Service for generating and validating OTP.")
@Service
public class OTPGenerator {

    private final String NUMBERS = "0123456789";
    @Value("${security.otp.expire:5}")
    private Integer expire = 5;
    @Value("${security.otp.length:5}")
    private Integer length = 4;

    private LoadingCache<String, Integer> otpCache;

    /**
     * Constructor configuration.
     */
    public OTPGenerator() {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expire, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    /**
     * Method for generating OTP and put it in cache.
     *
     * @param key - cache key
     * @return cache value (generated OTP number)
     */
    public Integer generateOTP(String key) {
        Random random = new Random();
        int OTP = gen(length) + random.nextInt(9 * gen(length));
        otpCache.put(key, OTP);
        return OTP;
    }

    private int gen(int length) {
        String value = "1";
        for (int i = 0; i < length; i++) {
            value += "0";
        }
        return Integer.parseInt(value);
    }

    /**
     * Method for getting OTP value by key.
     *
     * @param key - target key
     * @return OTP value
     */
    public Integer getOPTByKey(String key) {
        try {
            return otpCache.get(key);
        } catch (ExecutionException e) {
            return -1;
        }
    }

    /**
     * Method for removing key from cache.
     *
     * @param key - target key
     */
    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }
}

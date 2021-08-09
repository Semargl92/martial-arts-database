package by.semargl.util;


import by.semargl.domain.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserGenerator {

    public List<User> generate(int count) {
        List<User> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(generate());
        }
        return result;
    }

    public User generate() {
        User user = new User();
        user.setName(RandomStringUtils.randomAlphanumeric(10));
        user.setSurname(RandomStringUtils.randomAlphanumeric(10));
        user.setWeight(RandomUtils.nextFloat(40F, 100F));
        return user;
    }
}

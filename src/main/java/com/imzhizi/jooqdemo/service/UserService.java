package com.imzhizi.jooqdemo.service;

import com.imzhizi.jooqdemo.domain.User;
import com.imzhizi.jooqdemo.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.jooq.types.ULong;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.imzhizi.jooqdemo.Tables.USER;

@Service
public class UserService {
    private final DSLContext dsl;
    private final ModelMapper mapper;

    @Autowired
    public UserService(DSLContext dsl, ModelMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    public List<User> allUsers() {
        return dsl
                .selectFrom(USER)
                .fetch()
                .stream()
                .map(e -> mapper.map(e, User.class))
                .collect(Collectors.toList());
    }

    public Optional<User> getUserById(long id) {
        return dsl
                .selectFrom(USER)
                .where(USER.ID.eq(ULong.valueOf(id)))
                .fetch()
                .stream()
                .map(e -> mapper.map(e, User.class))
                .findFirst();
    }

    public UserRecord getUserRecordById(long id) {
        return dsl
                .selectFrom(USER)
                .where(USER.ID.eq(ULong.valueOf(id)))
                .fetchOne();
    }

    public Optional<User> getUserByUsername(String username) {
        return dsl
                .selectFrom(USER)
                .where(USER.USERNAME.eq(username))
                .fetch()
                .stream()
                .map(e -> mapper.map(e, User.class))
                .findFirst();
    }

    public int setNameForUser(Long id, String name) {
        return dsl
                .update(USER)
                .set(USER.NAME, name)
                .where(USER.ID.eq(ULong.valueOf(id)))
                .execute();
    }

    public int createUser(User user) {
        UserRecord userRecord = dsl.newRecord(USER);
        userRecord.setUsername(user.getUsername());
        userRecord.setPwd(user.getPwd());
        return userRecord.insert();
    }
}

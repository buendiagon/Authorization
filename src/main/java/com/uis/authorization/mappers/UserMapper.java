package com.uis.authorization.mappers;

import com.uis.authorization.dto.UserDTO;
import com.uis.authorization.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Daniel Adrian Gonzalez Buendia
 */

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapping User to UserDTO
    UserDTO toUserDTO(User user);
}

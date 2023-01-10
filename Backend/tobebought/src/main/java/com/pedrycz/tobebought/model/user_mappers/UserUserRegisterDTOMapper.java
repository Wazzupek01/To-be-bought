package com.pedrycz.tobebought.model.user_mappers;

import com.pedrycz.tobebought.entities.User;
import com.pedrycz.tobebought.model.user.UserRegisterDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserUserRegisterDTOMapper {
    User userRegisterDTOToUser(UserRegisterDTO userRegisterDTO);
}

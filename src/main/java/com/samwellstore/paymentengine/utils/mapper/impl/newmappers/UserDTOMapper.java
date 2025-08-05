package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.User.UserDTO;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;

public class UserDTOMapper implements Mapper<User, UserDTO> {


    final ModelMapper modelMapper;

    public UserDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO mapTo(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}

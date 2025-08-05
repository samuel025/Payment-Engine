package com.samwellstore.paymentengine.utils.mapper.impl.newmappers;

import com.samwellstore.paymentengine.dto.AdminDTOs.AdminDTO;
import com.samwellstore.paymentengine.dto.SignUpDTOs.AddAdminDTO;
import com.samwellstore.paymentengine.entities.User;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AddAdminMapper implements Mapper<User, AddAdminDTO> {
    final ModelMapper modelMapper;

    public AddAdminMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AddAdminDTO mapTo(User user) {
        return modelMapper.map(user, AddAdminDTO.class);
    }

    @Override
    public User mapFrom(AddAdminDTO addAdminDTO) {
        return modelMapper.map(addAdminDTO, User.class);
    }
}


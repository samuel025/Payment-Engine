package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.AdminDTOs.AddAdminDTO;
import com.samwellstore.paymentengine.entities.Admin;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AddAdminMapperImpl implements Mapper<Admin, AddAdminDTO> {

    final ModelMapper modelMapper;

    public AddAdminMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AddAdminDTO mapTo(Admin admin) {
        return modelMapper.map(admin, AddAdminDTO.class);
    }

    @Override
    public Admin mapFrom(AddAdminDTO addAdminDTO) {
        return modelMapper.map(addAdminDTO, Admin.class);
    }
}

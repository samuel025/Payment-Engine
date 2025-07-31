package com.samwellstore.paymentengine.utils.mapper.impl;

import com.samwellstore.paymentengine.dto.AdminDTOs.AddAdminDTO;
import com.samwellstore.paymentengine.dto.AdminDTOs.AdminDTO;
import com.samwellstore.paymentengine.entities.Admin;
import com.samwellstore.paymentengine.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AdminMapperImpl implements Mapper<Admin, AdminDTO> {

    final ModelMapper modelMapper;

    public AdminMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AdminDTO mapTo(Admin admin) {
        return modelMapper.map(admin, AdminDTO.class);
    }

    @Override
    public Admin mapFrom(AdminDTO adminDTO) {
        return modelMapper.map(adminDTO, Admin.class);
    }
}

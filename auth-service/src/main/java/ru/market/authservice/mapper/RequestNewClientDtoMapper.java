package ru.market.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.market.authservice.dto.RequestNewClientDto;
import ru.market.authservice.entity.UserProfile;

@Mapper
public interface RequestNewClientDtoMapper {

    @Mapping(source = "requestNewClientDto.login", target = "email")
    UserProfile requestNewClientDtoToUser(RequestNewClientDto requestNewClientDto);

}

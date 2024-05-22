package com.example.weather.data.mapper;

import com.example.weather.data.dto.WeatherDto;
import com.example.weather.data.entity.WeatherEntity;
import com.example.weather.utils.mapper.CycleAvoidingMappingContext;
import com.example.weather.utils.mapper.EntityToDomainMappingCycleAvoiding;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public interface WeatherMapper {
  @EntityToDomainMappingCycleAvoiding
  @Named("toEntity")
  default WeatherEntity toEntity(WeatherDto weatherDto) {
    return toEntity(weatherDto, new CycleAvoidingMappingContext());
  }

  @EntityToDomainMappingCycleAvoiding
  @Named("toDto")
  default WeatherDto toDto(WeatherEntity weatherEntity) {
    return toDto(weatherEntity, new CycleAvoidingMappingContext());
  }

  @Mapping(source = "id", target = "id",
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  WeatherEntity toEntity(WeatherDto weatherDto, @Context CycleAvoidingMappingContext context);

  WeatherDto toDto(WeatherEntity weatherEntity, @Context CycleAvoidingMappingContext context);

  @IterableMapping(qualifiedByName = "toEntity")
  List<WeatherEntity> toEntityList (List<WeatherDto> dtoList);

  @IterableMapping(qualifiedByName = "toDto")
  List<WeatherDto> toDtoList (List<WeatherEntity> entityList);

}

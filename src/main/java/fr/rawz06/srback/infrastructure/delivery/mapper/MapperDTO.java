package fr.rawz06.srback.infrastructure.delivery.mapper;

public interface MapperDTO<DTO, POJO> {
    DTO toDTO(POJO entity);
    POJO toPOJO(DTO dto);
}

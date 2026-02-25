package fr.rawz06.srback.infrastructure.persistence.mapper;

public interface MapperEntity<POJO, Entity> {
    POJO mapEntityToPojo(Entity entity);
    Entity mapPojoToEntity(POJO pojo);
}

package com.mongodb.opendata.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.opendata.domain.DatasetMapping;

@Repository
public interface DatasetMappingRepository extends CrudRepository<DatasetMapping, String> {

}

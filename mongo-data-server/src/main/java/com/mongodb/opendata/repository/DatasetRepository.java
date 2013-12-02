package com.mongodb.opendata.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.google.dspl.model.Dataset;

@Repository
public interface DatasetRepository extends CrudRepository<Dataset, String> {

}

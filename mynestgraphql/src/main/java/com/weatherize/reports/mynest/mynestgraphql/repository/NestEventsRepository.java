package com.weatherize.reports.mynest.mynestgraphql.repository;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.weatherize.reports.mynest.mynestgraphql.model.HvacData;

public interface NestEventsRepository extends CassandraRepository<HvacData, Date> {
	Slice<HvacData> findAllByOrderByTimeofeventDesc(Pageable pageable);
	HvacData findByEventid(UUID id);
}

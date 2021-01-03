package com.weatherize.mynest.live.feedstore.repository;

import java.util.Date;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.weatherize.mynest.live.feedstore.model.HvacData;

public interface MyNestThermostatEventRepository extends CassandraRepository<HvacData, Date> {
	Slice<HvacData> findByEventidOrderByTimeofeventDesc(int eventid,Pageable pageable);
}

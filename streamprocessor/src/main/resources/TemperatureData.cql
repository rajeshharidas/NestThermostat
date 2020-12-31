create table if not exists mynestthermostat.temperaturedata (
datasetid INT,
year INT,
month INT,
timeofcapture TIMESTAMP,
temperature FLOAT,
humidity FLOAT,
timetotarget FLOAT,
mode TEXT,
hvaccycleon BOOLEAN,
primary key((datasetid),timeofcapture)
) WITH CLUSTERING ORDER BY (timeofcapture DESC);
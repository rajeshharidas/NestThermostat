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
primary key((datasetid),year,month,timeofcapture)
) WITH CLUSTERING ORDER BY (year DESC, month DESC, timeofcapture DESC);
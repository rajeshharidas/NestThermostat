create table if not exists mynestthermostat.nesteventdata (
eventid UUID,
timeofevent TIMESTAMP,
traitkey TEXT,
traitvalue TEXT,
primary key((eventid),timeofevent)
) WITH CLUSTERING ORDER BY (timeofevent DESC);
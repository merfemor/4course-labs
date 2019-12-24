create table rss_feed(
    id integer primary key autoincrement not null,
    title varchar(256) not null,
    url varchar(1024) not null
);

create table rss_feed_item(
    id integer primary key autoincrement not null,
    feed_id integer not null,
    title varchar(256) not null,
    description varchar(2048) not null,
    time varchar (256) not null,
    link varchar (1024) not null,
    foreign key(feed_id) references rss_feed(id) on delete cascade on update cascade
);

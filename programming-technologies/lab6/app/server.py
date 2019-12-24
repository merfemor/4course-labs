#!/usr/local/bin/python3

import math
import os

import feedparser
import flask
from flask import Flask
from dateutil import parser
from flask_sqlalchemy import SQLAlchemy

PAGE_SIZE = 10

app = Flask(__name__)
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'app.db')
db = SQLAlchemy(app)


class Feed(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(256), nullable=False)
    url = db.Column(db.String(1024), nullable=False, unique=True)

    def __repr__(self):
        return '<Feed ' + self.title + ': ' + self.url + '>'


class FeedItem(db.Model):
    id = db.Column(db.String(512), primary_key=True)
    title = db.Column(db.String(256), nullable=False)
    description = db.Column(db.String(2048), nullable=False)
    url = db.Column(db.String(1024), nullable=False, unique=True)
    time = db.Column(db.String(256), nullable=False)
    parsed_time = db.Column(db.DateTime(), nullable=True)

    feed_id = db.Column(db.Integer, db.ForeignKey('feed.id'), nullable=False)
    feed = db.relationship('Feed', backref=db.backref('items', lazy=True, order_by='FeedItem.parsed_time.desc()'))

    def __repr__(self):
        return '<FeedItem in feed ' + self.feed.title + ' with name' + self.title + ': ' + self.url + '>'


def get_feed_html_page_data(feed_id, page):
    feed = Feed.query.get(feed_id)
    # if feed_id < 0 or feed_id >= len(added_rss):
    #     print("feed_id out of range")
    #     return None

    page_count = math.ceil(len(feed.items) / PAGE_SIZE)
    if page > page_count:
        print("page out of range")
        return None, None, None

    info = {
        "page_count": page_count,
        "active_page": page
    }

    start_index = (page - 1) * PAGE_SIZE
    end_index = min(page * PAGE_SIZE, len(feed.items))

    return feed, info, feed.items[start_index:end_index]


def parse_feed_from_url(rss_feed_link):
    d = feedparser.parse(rss_feed_link)

    def rss_entry_to_feed_item(entry):
        published = entry.published
        published_parsed = parser.parse(published)

        return FeedItem(id=entry.id,
                        title=entry.title,
                        url=entry.link,
                        time=published,
                        parsed_time=published_parsed,
                        description=entry.description,
                        feed=feed)

    feed = Feed(title=d.feed.title, url=rss_feed_link)
    feed.items = [rss_entry_to_feed_item(entry) for entry in d.entries]
    return feed


def reload_feed(feed_id):
    feed_url = Feed.query.get(feed_id).url
    new_feed = parse_feed_from_url(feed_url)
    db.session.add(new_feed.items)
    db.session.commit()


def add_rss_feed_link(rss_feed_link):
    feed = parse_feed_from_url(rss_feed_link)
    db.session.add(feed)
    db.session.commit()


def get_feeds():
    return Feed.query.all()


@app.route('/', methods=["GET"])
def root():
    return flask.redirect('/feed', code=302)


@app.route('/feed', methods=["GET"])
@app.route('/feed/<int:feed_id>', methods=["GET"])
@app.route('/feed/<int:feed_id>/<int:page>', methods=["GET"])
def feed(feed_id=None, page=1):
    if feed_id is None:
        data = {"no_feeds": True, "items": []}
        info = None
        items = []
    else:
        data, info, items = get_feed_html_page_data(feed_id, page)
        if data is None:
            return flask.abort(400)
        info["no_feeds"] = False
    feeds = get_feeds()
    return flask.render_template('index.html', data=data, items=items, feeds=feeds, info=info)


@app.route('/feed', methods=["POST"])
def add_rss():
    rss_feed_link = flask.request.form.get('rss-feed-link')
    add_rss_feed_link(rss_feed_link)
    return flask.redirect("/", code=302)


@app.route('/feedupdate/<int:feed_id>', methods=["GET"])
def feed_update(feed_id):
    reload_feed(feed_id)
    return flask.redirect("/feed/" + feed_id, code=302)

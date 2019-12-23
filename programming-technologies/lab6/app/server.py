#!/usr/bin/python3

import copy
import math

import feedparser
import flask
from flask import Flask

VARIANTS_IN_QUESTION = 3
PAGE_SIZE = 10

app = Flask(__name__)

added_rss = []


def get_feed_html_page_data(feed_id, page):
    if feed_id < 0 or feed_id >= len(added_rss):
        print("feed_id out of range")
        return None

    feed = copy.deepcopy(added_rss[feed_id])
    feed["page_count"] = math.ceil(len(feed["items"]) / PAGE_SIZE)
    if page > feed["page_count"]:
        print("page out of range")
        return None

    feed["active_page"] = page
    start_index = (page - 1) * PAGE_SIZE
    end_index = min(page * PAGE_SIZE, len(feed["items"]))
    feed["items"] = feed["items"][start_index:end_index]
    return feed


def add_rss_feed_link(rss_feed_link):
    d = feedparser.parse(rss_feed_link)

    new_id = len(added_rss)
    added_rss.append({
        "name": d.feed.title,
        "url": rss_feed_link,
        "id": new_id,
        "items": [{
            "title": entry.title,
            "link": entry.link,
            "time": entry.published,
            "description": entry.description
        } for entry in d.entries]
    })


def get_feeds():
    return [{
        "id": i,
        "url": f["url"],
        "name": f["name"]
    } for (i, f) in enumerate(added_rss)]


@app.route('/', methods=["GET"])
def root():
    return flask.redirect('/feed', code=302)


@app.route('/feed', methods=["GET"])
@app.route('/feed/<int:feed_id>', methods=["GET"])
@app.route('/feed/<int:feed_id>/<int:page>', methods=["GET"])
def feed(feed_id=None, page=1):
    if feed_id is None:
        data = {"no_feeds": True, "items": []}
    else:
        data = get_feed_html_page_data(feed_id, page)
        if data is None:
            return flask.abort(400)
        data["no_feeds"] = False
    feeds = get_feeds()
    return flask.render_template('index.html', data=data, items=data["items"], feeds=feeds)


@app.route('/feed', methods=["POST"])
def add_rss():
    rss_feed_link = flask.request.form.get('rss-feed-link')
    add_rss_feed_link(rss_feed_link)
    return flask.redirect("/", code=302)

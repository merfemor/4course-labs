#!/usr/bin/python3

import flask
from flask import Flask

VARIANTS_IN_QUESTION = 3
PAGE_SIZE = 10
FEED_STUB = {
    "id": 1,
    "name": "Habrahabr",
    "items": [
        {
            "title": "Уникальная диета: на мягких французких булках и чае",
            "description": "ололо дескрипшн",
            "link": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            "time": "Updated at deech"
        }
    ],
    "page_count": 5,
    "active_page": 2
}

app = Flask(__name__)


def get_feed_html_page_data(feed_id, page):
    # TODO: implement
    return FEED_STUB


def add_rss_feed_link(rss_feed_link):
    print("adding rss feed: ", rss_feed_link)


@app.route('/', methods=["GET"])
def root():
    return flask.redirect('/feed', code=302)


@app.route('/feed', methods=["GET"])
@app.route('/feed/<int:feed_id>', methods=["GET"])
@app.route('/feed/<int:feed_id>/<int:page>', methods=["GET"])
def feed(feed_id=None, page=0):
    data = get_feed_html_page_data(feed_id, page)
    return flask.render_template('index.html', data=data, items=data["items"])


@app.route('/feed', methods=["POST"])
def add_rss():
    rss_feed_link = flask.request.form.get('rss-feed-link')
    add_rss_feed_link(rss_feed_link)
    return flask.redirect("/", code=302)

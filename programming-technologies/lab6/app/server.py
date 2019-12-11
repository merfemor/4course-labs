#!/usr/bin/python3

import flask
from flask import Flask

VARIANTS_IN_QUESTION = 3

app = Flask(__name__)


def get_news_grouped_list():
    response = [
        {
            "feed_name": "Habrahabr",
            "items": [
                {
                    "title": "Уникальная диета: на мягких французких булках и чае",
                    "description": "ололо дескрипшн",
                    "url": "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
                }
            ]
        }
    ]
    return response


@app.route('/news')
def news():
    resp = get_news_grouped_list()
    return flask.jsonify(resp)


def add_rss_feed_link(rss_feed_link):
    print("adding rss feed: ", rss_feed_link)


@app.route('/rss', methods=["POST"])
def add_rss():
    rss_feed_link = flask.request.form.get('rss-feed-link')
    add_rss_feed_link(rss_feed_link)
    return flask.redirect("/", code=302)

@app.route('/', )
def main():
    return flask.render_template('index.html')

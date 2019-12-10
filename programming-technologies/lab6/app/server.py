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


@app.route('/', )
def main():
    return flask.render_template('index.html')

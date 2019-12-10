#!/usr/bin/python3

import flask
from flask import Flask

names = []
with open("guess.txt", "r") as names_file:
    names = names_file.readlines()

app = Flask(__name__)


@app.route('/')
def main():
    return flask.render_template('main.html')


def get_params_for_difficulty(difficulty):
    # TODO: implement
    return ("https://znaj.ua/images/2018/10/20/0VxXp45e6hJUdpJt8tWxEFUxRLUjDc9EfkETdqlB.jpeg",
            ["foo", "bar", "baz"],
            0)

@app.route('/game')
def game():
    difficulty = int(flask.request.args.get("diff"))
    (image_url, variants, right_var) = get_params_for_difficulty(difficulty)
    return flask.render_template("game.html", image_url=image_url, variants=variants, right_var=right_var)

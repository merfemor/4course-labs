#!/usr/bin/python3

import flask
import random
import requests
import re
from flask import Flask
from bs4 import BeautifulSoup

VARIANTS_IN_QUESTION = 3

with open("guess.txt", "r") as names_file:
    names = [n.strip() for n in names_file.readlines()]

app = Flask(__name__)


def get_url_for_name(name):
    query = '%2B'.join(name.split())
    url = "https://www.google.ru/search?q=" + query + "&source=lnms&tbm=isch"
    print("get_url_for_name: searching image in google, url = ", url)
    resp = requests.get(url)
    print("get_url_for_name: code = ", resp.status_code, "result is empty: ", len(resp.text) == 0)
    soup = BeautifulSoup(resp.text, 'html.parser')
    images = [a['src'] for a in soup.find_all("img", {"src": re.compile('gstatic.com')})]
    if len(images) == 0:
        raise ValueError("images not found in response")
    return images[random.randint(0, max(len(images), 20) - 1)]


def get_params_for_difficulty(difficulty):
    if difficulty == 0:
        end = 10
    elif difficulty == 1:
        end = int(len(names) / 2)
    else:
        end = len(names)

    print("randomize in range [0; ", end, ") name of ", len(names), " names")

    name_variants = random.sample(names[:end - 1], VARIANTS_IN_QUESTION)
    right_variant = name_variants[0]
    random.shuffle(name_variants)
    right_variant_index = name_variants.index(right_variant)

    return (get_url_for_name(right_variant),
            name_variants,
            right_variant_index)


@app.route('/game')
def game():
    difficulty = int(flask.request.args.get("diff"))
    image_url, variants, right_var = get_params_for_difficulty(difficulty)
    return flask.render_template("game.html", image_url=image_url, variants=variants, right_var=right_var)


@app.route('/')
def main():
    return flask.render_template('main.html')

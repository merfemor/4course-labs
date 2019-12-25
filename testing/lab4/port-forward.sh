#!/bin/bash

HELIOS_USER=s225111
ssh -L 127.0.0.1:8080:aqua:8080 $HELIOS_USER@se.ifmo.ru -p 2222
